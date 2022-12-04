package com.example.ciffclean.controllers;

import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import com.example.ciffclean.service.AuthService;
import com.example.ciffclean.service.JwtTokenUtil;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<UserTokenDTO> registerUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            //TODO: Input validation
            if(!EmailValidator.getInstance().isValid(createUserDTO.getEmail()) || !isValidPassword(createUserDTO.getPassword())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.ok(authService.register(createUserDTO));

        }catch(DataAccessException dae){
            dae.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
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
        }catch(DataAccessException dae){
            dae.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO,
                            @RequestHeader(value = "Authorization", required = false) String authorization){
        try{
            Optional<String> otoken = jwtTokenUtil.getTokenFromHeader(authorization);
            if (otoken.isPresent()){
                String token = otoken.get();
                Long userId = jwtTokenUtil.getUserId(token);
                if (jwtTokenUtil.isValidToken(token, userId)) {
                    Optional<AppUser> appUser = userRepository.findById(userId);
                    if(!appUser.isPresent()){   return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}
                    if(appUser.get().getPassword().equals(changeUserPasswordDTO.getOldPassword())){
                        if(!isValidPassword(changeUserPasswordDTO.getNewPassword())){
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
                        }
                        AppUser changedUser = appUser.get();
                        changedUser.setPassword(changeUserPasswordDTO.getNewPassword());
                        userRepository.save(changedUser);
                        return ResponseEntity.ok(true);
                    }
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }                
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch(DataAccessException dae){
            dae.printStackTrace();
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
