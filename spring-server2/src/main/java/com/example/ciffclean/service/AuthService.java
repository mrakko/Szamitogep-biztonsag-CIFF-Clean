package com.example.ciffclean.service;

import java.security.MessageDigest;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.models.UserTokenDTO;
import com.example.ciffclean.repositories.UserRepository;

@Component
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    public UserTokenDTO register(CreateUserDTO createUserDTO) throws Exception{
        if(!userRepository.findByEmail(createUserDTO.getEmail()).isEmpty()){
            throw new IllegalArgumentException();
        }
        AppUser appUser = new AppUser();
        appUser.setAddress(createUserDTO.getAddress());
        appUser.setEmail(createUserDTO.getEmail());        
        appUser.setPassword(getHash(createUserDTO.getPassword()));
        appUser.setFullName(createUserDTO.getFullName());
        userRepository.save(appUser);
        String token = jwtTokenUtil.generateToken(appUser.getId(), appUser.getFullName());
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setToken(token); 
        return userTokenDTO;
    }

    public String login(LoginUserDTO loginUserDTO) throws Exception{
        var userList = userRepository.findByEmail(loginUserDTO.getEmail());
        if(userList.isEmpty()){ 
            throw new NoSuchElementException(MediaService.USER_NOT_FOUND);
        }
        if(userList.size() != 1){ 
            throw new IllegalArgumentException();
        }
        var user = userList.get(0);
        if(!user.getPassword().equals(getHash(loginUserDTO.getPassword()))){
            throw new IllegalArgumentException();
        }
        return jwtTokenUtil.generateToken(user.getId(), user.getFullName());
   }

   public void changePassword(Long userId, String oldPass, String newPass) throws Exception{
        var appUser = userRepository.findById(userId);
        if(appUser.isEmpty()){
            throw new NoSuchElementException(MediaService.USER_NOT_FOUND);
        }
        var user = appUser.get();

        if(!user.getPassword().equals(getHash(oldPass)) || !isValidPassword(newPass)){
            throw new IllegalArgumentException();
        }

        user.setPassword(getHash(newPass));
        userRepository.save(user);
   }

   public String getHash(String password) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] data = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < data.length; i++){
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
   }

   public boolean isValidPassword(String password)
    {
            boolean isValid = true;
            if (password.length() < 12){isValid = false;}
            String upperCaseChars = "(.*[A-Z].*)";
            if (!password.matches(upperCaseChars )){isValid = false;}
            String lowerCaseChars = "(.*[a-z].*)";
            if (!password.matches(lowerCaseChars )){isValid = false;}
            String numbers = "(.*[0-9].*)";
            if (!password.matches(numbers )){isValid = false;}
            return isValid;
    }
}
