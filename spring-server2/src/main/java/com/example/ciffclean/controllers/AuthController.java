package com.example.ciffclean.controllers;

import java.util.NoSuchElementException;

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

import com.example.ciffclean.models.ChangeUserPasswordDTO;
import com.example.ciffclean.models.CreateUserDTO;
import com.example.ciffclean.models.LoginUserDTO;
import com.example.ciffclean.models.UserTokenDTO;
import com.example.ciffclean.service.AuthService;
import com.example.ciffclean.service.JwtTokenUtil;
import com.example.ciffclean.service.LogService;

import io.jsonwebtoken.SignatureException;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthService authService;

    @Autowired
    private LogService logService;

    @PostMapping("register")
    public ResponseEntity<UserTokenDTO> registerUser(@RequestBody CreateUserDTO createUserDTO){
        try{
            logService.logActivity(null, "REGISTER REQUESTED: " + createUserDTO.getEmail(), null);
            if(!EmailValidator.getInstance().isValid(createUserDTO.getEmail()) || !authService.isValidPassword(createUserDTO.getPassword())){
                logService.logError(null, "INVALID EMAIL OR PASSWORD", "REGISTER");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            var res = authService.register(createUserDTO);
            logService.logActivity(null, "REGISTER SUCCESSFUL: " + createUserDTO.getEmail(), null);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e){
            logService.logError(null, "EMAIL ALREADY EXISTS", "REGISTER");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }  catch(DataAccessException e){
            logService.logError(null, e.getMessage(), "REGISTER");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(null, e.getMessage(), "REGISTER");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("login")
    public ResponseEntity<UserTokenDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO){
        Long currentUserId = -1L;
        try{
            var res = new UserTokenDTO();
            logService.logActivity(currentUserId, "LOGIN REQUESTED: " + loginUserDTO.getEmail(), null);
            res.setToken(authService.login(loginUserDTO));
            currentUserId = jwtTokenUtil.getUserIdFromToken(res.getToken());
            logService.logActivity(currentUserId, "SUCCESSFUL LOGIN " + loginUserDTO.getEmail(), null);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e){
            logService.logError(currentUserId, "USER NOT FOUND", "LOGIN");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e){
            logService.logError(currentUserId, "INVALID EMAIL OR PASSWORD", "LOGIN");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch(DataAccessException dae){
            logService.logError(currentUserId, "INVALID EMAIL OR PASSWORD", "LOGIN");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "LOGIN");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<Boolean> changePassword(@RequestBody ChangeUserPasswordDTO changeUserPasswordDTO,
                            @RequestHeader(value = "Authorization", required = false) String authorization){
        Long currentUserId = -1L;
        try{
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            logService.logActivity(currentUserId, "CHANGE_PASSWORD REQUESTED", null);
            authService.changePassword(currentUserId, changeUserPasswordDTO.getOldPassword(), changeUserPasswordDTO.getNewPassword());
            logService.logActivity(currentUserId, "CHANGE_PASSWORD SUCCESSFUL", null);
            return ResponseEntity.ok(true);
        } catch (NoSuchElementException | SignatureException e){
            logService.logError(currentUserId, "UNAUTHORIZED", "CHANGE_PASSWORD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch(DataAccessException e){
            logService.logError(currentUserId, "INVALID PASSWORD", "CHANGE_PASSWORD");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch(IllegalArgumentException e){
            logService.logError(currentUserId, "INVALID PASSWORD", "CHANGE_PASSWORD");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "CHANGE_PASSWORD");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
