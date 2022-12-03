package com.example.ciffclean.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.repositories.UserRepository;

@Component
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public String register(CreateUserDTO createUserDTO){
         AppUser appUser = new AppUser();
         appUser.setAddress(createUserDTO.getAddress());
         appUser.setEmail(createUserDTO.getEmail());
         appUser.setPassword(createUserDTO.getPassword());   //TODO: Plaintext helyett cizelláltabban...
         appUser.setFullName(createUserDTO.getFullName());
         userRepository.save(appUser);
         return jwtTokenUtil.generateToken(appUser.getId(), appUser.getFullName());
    }

    public String login(LoginUserDTO loginUserDTO){
        var userList = userRepository.findByEmail(loginUserDTO.getEmail());
        if(userList.isEmpty()){ 
            throw new NoSuchElementException(MediaService.USER_NOT_FOUND);
        }
        if(userList.size() != 1){ 
            throw new IllegalArgumentException();
        }
        return jwtTokenUtil.generateToken(userList.get(0).getId(), userList.get(0).getFullName());
   }

   public void changePassword(Long userId, String oldPass, String newPass){
        var appUser = userRepository.findById(userId);
        if(appUser.isEmpty()){
            throw new NoSuchElementException(MediaService.USER_NOT_FOUND);
        }
        var user = appUser.get();
        if(user.getPassword().equals(oldPass)){
            user.setPassword(newPass);
            userRepository.save(user);
        }
   }
}
