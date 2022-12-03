package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.repositories.UserRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthServiceTest {
    @Autowired
	private AuthService authService;

    @Autowired
	private UserRepository userRepository;

    @Test
    public void registerTest(){
        var dto = createUser();
        authService.register(dto);
        assertEquals(1, userRepository.count());
    }

    @Test
    public void loginTest(){
        var dto = createUser();
        authService.register(dto);
        
        var dtoLogin = createLoginUser();
        var token = authService.login(dtoLogin);
        assertNotNull(token);
        assertNotEquals("", token);
    }

    @Test
    public void changePasswordTest(){
        var dto = createUser();
        authService.register(dto);

        authService.changePassword(1L, dto.getPassword(), "MyNewPass123");
        var user = userRepository.findById(1L).get();
        assertEquals(user.getPassword(), "MyNewPass123");
    }

    private CreateUserDTO createUser(){
        var dto = new CreateUserDTO();
        dto.setEmail("myemail@email.com");
        dto.setPassword("MyPass123");
        return dto;
    }

    private LoginUserDTO createLoginUser(){
        var dto = new LoginUserDTO();
        dto.setEmail("myemail@email.com");
        return dto;
    }
}
