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

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Test
    public void registerTest() throws Exception{
        var dto = createUser();
        authService.register(dto);
        assertEquals(2, userRepository.count());
    }

    @Test
    public void loginTest() throws Exception{
        var dto = createUser();
        authService.register(dto);
        
        var dtoLogin = createLoginUser();
        var token = authService.login(dtoLogin);
        assertNotNull(token);
        assertNotEquals("", token);
    }

    @Test
    public void changePasswordTest() throws Exception{
        var dto = createUser();
        var token = authService.register(dto).getToken();
        var userId = jwtTokenUtil.getUserIdFromToken(token);

        authService.changePassword(userId, dto.getPassword(), "MyNewPass123");
        var user = userRepository.findById(userId).get();
        assertEquals(user.getPassword(), authService.getHash("MyNewPass123"));
    }

    private CreateUserDTO createUser(){
        var dto = new CreateUserDTO();
        dto.setEmail("myemail@email.com");
        dto.setPassword("MyOldPassword1234");
        return dto;
    }

    private LoginUserDTO createLoginUser(){
        var dto = new LoginUserDTO();
        dto.setEmail("myemail@email.com");
        dto.setPassword("MyOldPassword1234");
        return dto;
    }
}
