package com.example.ciffclean.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ciffclean.domain.GifFile;
import com.example.ciffclean.models.*;
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

    @GetMapping("search")
    public ResponseEntity<List<MediaDTO>> searchFile(
        @RequestParam String query
    ) {
        List<GifFile> results = mediaService.findGifFiles(query);
        ArrayList<MediaDTO> dtos = new ArrayList<MediaDTO>();
        for (GifFile gifFile : results) {
            dtos.add(gifFile.toMediaDTO());
        }
        return ResponseEntity.ok().body(dtos);
    }

    @PutMapping("{file_id}/modify")
    public ResponseEntity<MediaDTO> modifyFile(
        @RequestHeader(value = "Authorization") String authorization,
        @PathVariable Long file_id,
        @RequestBody EditFileDTO body
    ) {
        try {
            Long currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            // TODO auth
            GifFile gifFile = mediaService.editFileName(file_id, body.getFileName());

            return ResponseEntity.ok().body(gifFile.toMediaDTO());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(
        @RequestHeader(value = "Authorization") String authorization,
        @RequestParam("file") MultipartFile file
    ) {
        try {
            Long currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            // TODO auth
            byte[] content = IOUtils.toByteArray(file.getInputStream());
            Long id = mediaService.addCaff(content, file.getName(), currentUserId);
            if (id == -1L){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            return ResponseEntity.ok().body(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
