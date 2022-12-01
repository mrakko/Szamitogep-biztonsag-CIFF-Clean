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
import com.example.ciffclean.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public ResponseEntity<UserDTO> getUser(@RequestHeader(value = "Authorization") String authorization){
        try {
            Long currentUserId = getCurrentUserId(authorization);
            var res = userService.getUser(currentUserId);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("")
    public ResponseEntity<UserDTO> editUser(@RequestBody EditUserDTO editUserDTO,
        @RequestHeader(value = "Authorization") String authorization){
        try {
            Long currentUserId = getCurrentUserId(authorization);
            var res = userService.editUser(editUserDTO, currentUserId);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Long getCurrentUserId(String authorization){
        var id = jwtTokenUtil.getUserIdFromToken(authorization);
        if(id == null){
            throw new NoSuchElementException();
        }
        return id.get();
    }
}
