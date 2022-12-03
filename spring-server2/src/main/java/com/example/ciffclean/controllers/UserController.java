package com.example.ciffclean.controllers;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ciffclean.models.EditUserDTO;
import com.example.ciffclean.models.UserDTO;
import com.example.ciffclean.service.JwtTokenUtil;
import com.example.ciffclean.service.LogService;
import com.example.ciffclean.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestHeader(value = "Authorization") String authorization){
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            logService.logActivity(currentUserId, "GETUSER", null);
            var res = userService.getUser(currentUserId);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "GETUSER");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "GETUSER");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> editUser(@RequestBody EditUserDTO editUserDTO,
        @RequestHeader(value = "Authorization") String authorization){

        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            logService.logActivity(currentUserId, "EDITUSER START", null);
            var res = userService.editUser(editUserDTO, currentUserId);
            logService.logActivity(currentUserId, "EDITUSER OK", null);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "EDITUSER");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "EDITUSER");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
