package com.example.ciffclean.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.EditUserDTO;
import com.example.ciffclean.models.UserDTO;
import com.example.ciffclean.models.UserRole;
import com.example.ciffclean.repositories.UserRepository;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {
    @Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

    private AppUser testUser;

    @BeforeEach
    public void init(){
        testUser = new AppUser();
        testUser.setAddress("My address");
        testUser.setFullName("Fullname");
        testUser.setEmail("test@test.com");
        testUser.setRole(UserRole.Regular);
    }

    @Test
    public void getUserTest() throws Exception{
        userRepository.save(testUser);
        var testUserId = testUser.getId();
        assertNotEquals(testUserId, -1L);

        var actual = userService.getUser(testUserId);
        var user = new UserDTO(testUser);
        assertEquals(user.getId(), actual.getId());
    }

    @Test
    public void editUserTest() throws Exception{
        userRepository.save(testUser);
        var testUserId = testUser.getId();
        assertNotEquals(testUserId, -1L);

        var editUser = getEditUserDTO();

        var actual = userService.editUser(editUser, testUserId);
        var expected = userRepository.findById(testUserId);
        var user = new UserDTO(expected.get());
        assertEquals(user.getId(), actual.getId());
    }

    private EditUserDTO getEditUserDTO(){
        EditUserDTO editUser = new EditUserDTO();
        editUser.setAddress("New address");
        editUser.setEmail("newemail@test.com");
        editUser.setFullName("My New Name");
        return editUser;
    }
}
