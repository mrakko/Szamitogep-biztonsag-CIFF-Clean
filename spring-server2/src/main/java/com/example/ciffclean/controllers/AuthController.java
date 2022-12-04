package com.example.ciffclean.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.ChangeUserPasswordDTO;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.models.UserTokenDTO;
import com.example.ciffclean.repositories.UserRepository;
import com.example.ciffclean.service.JwtTokenUtil;

@CrossOrigin(origins ={"http://localhost:8080", "http://localhost:4200"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("register")
    public ResponseEntity<UserTokenDTO> registerUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            //TODO: Input validation
            AppUser appUser = new AppUser();
            appUser.setAddress(createUserDTO.getAddress());
            appUser.setEmail(createUserDTO.getEmail());
            appUser.setPassword(createUserDTO.getPassword());   //TODO: Plaintext helyett cizelláltabban...
            appUser.setFullName(createUserDTO.getFullName());
            userRepository.save(appUser);
            return ResponseEntity.ok(generateUserTokenDTO(appUser));

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
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO,
                            @RequestHeader(value = "Authorization", required = false) String authorization){
        try{
            //TODO: Input validation
            
            Optional<String> otoken = jwtTokenUtil.getTokenFromHeader(authorization);
            if (otoken.isPresent()){
                String token = otoken.get();
                Long userId = jwtTokenUtil.getUserId(token);
                if (jwtTokenUtil.isValidToken(token, userId)) {
                    Optional<AppUser> appUser = userRepository.findById(userId);
                    if(!appUser.isPresent()){   return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}
                    if(appUser.get().getPassword().equals(changeUserPasswordDTO.getOldPassword())){
                        AppUser changedUser = appUser.get();
                        changedUser.setPassword(changeUserPasswordDTO.getNewPassword());
                        userRepository.save(changedUser);
                        return ResponseEntity.ok(true);
                    }
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }                
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserTokenDTO generateUserTokenDTO(AppUser appUser){
        String token = jwtTokenUtil.generateToken(appUser.getId(), appUser.getFullName());
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setUserId(appUser.getId());
        userTokenDTO.setToken(token); 
        return userTokenDTO;
    }
    
}
