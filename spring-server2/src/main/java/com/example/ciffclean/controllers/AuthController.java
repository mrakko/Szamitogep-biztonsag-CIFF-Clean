package com.example.ciffclean.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.UserTokenDTO;
import com.example.ciffclean.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserTokenDTO> registerUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            //TODO: Input validation
            AppUser appUser = new AppUser();
            appUser.setAddress(createUserDTO.getAddress());
            appUser.setEmail(createUserDTO.getEmail());
            appUser.setFullName(createUserDTO.getFullName());
            AppUser savedUser = userRepository.save(appUser);
            UserTokenDTO userTokenDTO = new UserTokenDTO();
            userTokenDTO.setUserId(savedUser.getId());
            userTokenDTO.setValue(savedUser.getFullName()); //TODO: Mi legyen benne?
            userTokenDTO.setExpirationDate(LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES)));  //TODO: Meddig legyen érvényes?
            return ResponseEntity.ok(userTokenDTO);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
