package com.example.ciffclean.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.ChangeUserPasswordDTO;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.models.UserTokenDTO;
import com.example.ciffclean.repositories.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("register")
    public ResponseEntity<UserTokenDTO> registerUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            //TODO: Input validation
            AppUser appUser = new AppUser();
            appUser.setAddress(createUserDTO.getAddress());
            appUser.setEmail(createUserDTO.getEmail());
            appUser.setPassword(createUserDTO.getPassword());   //TODO: Plaintext helyett cizelláltabban...
            appUser.setFullName(createUserDTO.getFullName());
            AppUser savedUser = userRepository.save(appUser);
            return ResponseEntity.ok(generateUserTokenDTO(savedUser));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<UserTokenDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        try{
            //TODO: Input validation
            var userList = userRepository.findByEmail(loginUserDTO.getEmail());
            if(userList.isEmpty()){ return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); }
            if(userList.size() != 1){ return ResponseEntity.status(HttpStatus.CONFLICT).build(); }
            return ResponseEntity.ok(generateUserTokenDTO(userList.get(0)));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO){
        try{
            //TODO: Input validation
            //TODO: API leírást kiegészíteni. Kell a DTO-ba egy mező, hogy melyik User kéri a változtatást
            Long userId = Long.valueOf(0);
            Optional<AppUser> appUser = userRepository.findById(userId);
            if(!appUser.isPresent()){   return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}
            if(appUser.get().getEmail().equals(changeUserPasswordDTO.getOldPassword())){ 
                AppUser changedUser = appUser.get();
                changedUser.setPassword(changeUserPasswordDTO.getNewPassword());
                userRepository.save(changedUser);
                return ResponseEntity.ok(true);
            } 
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserTokenDTO generateUserTokenDTO(AppUser appUser){
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setUserId(appUser.getId());
        userTokenDTO.setValue(appUser.getFullName()); //TODO: Mi legyen benne?
        userTokenDTO.setExpirationDate(LocalDateTime.now()
            .plus(Duration.of(10, ChronoUnit.MINUTES)));  //TODO: Meddig legyen érvényes?
        return userTokenDTO;
    }
    
}
