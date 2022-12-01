package com.example.ciffclean.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ciffclean.models.CreateCommentDTO;
import com.example.ciffclean.models.UserRole;
import com.example.ciffclean.service.JwtTokenUtil;
import com.example.ciffclean.service.MediaService;
import com.example.ciffclean.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/media")
public class MediaController {
    
    // TODO log

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("comment")
    public ResponseEntity<Void> commentFile(@RequestHeader(value = "Authorization") String authorization,
        @RequestBody CreateCommentDTO body) {
            try {
                Long currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
                mediaService.commentFile(body, currentUserId);
                return ResponseEntity.ok().build();
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteFile(
        @PathVariable Long id,    
        @RequestHeader(value = "Authorization") String authorization) {
            try {
                Long currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
                checkUserRole(currentUserId, UserRole.Admin);
                mediaService.deleteFile(id);
                return ResponseEntity.noContent().build();
            } catch (NoSuchElementException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    private void checkUserRole(Long currentUserId, UserRole admin) throws Exception {
        var user = userService.getUser(currentUserId);
        if(!user.getRole().equals(admin)){
            throw new IllegalArgumentException("Only users are allowed to delete files");
        }
    }
}
