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
import com.example.ciffclean.service.LogService;
import com.example.ciffclean.service.MediaService;
import com.example.ciffclean.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private LogService logService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("comment")
    public ResponseEntity<MediaDTO> commentFile(@RequestHeader(value = "Authorization") String authorization,
        @RequestBody CreateCommentDTO body) {
            Long currentUserId = -1L;
            try {
                currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
                logService.logActivity(currentUserId, "COMMENT", body.getFileId());
                mediaService.commentFile(body, currentUserId);
                var res = mediaService.getGif(body.getFileId());
                return ResponseEntity.ok(res.toMediaDTO());
            } catch (NoSuchElementException e) {
                if(e.getMessage().equals(MediaService.FILE_NOT_FOUND)){
                    logService.logActivity(currentUserId, "COMMENT", null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                logService.logError(currentUserId, "UNAUTHORIZED", "COMMENT");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }  catch (Exception e) {
                e.printStackTrace();
                logService.logError(currentUserId, e.getMessage(), "COMMENT");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization") String authorization) {
       Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            logService.logActivity(currentUserId, "DELETE", id);
            checkUserRole(currentUserId, UserRole.Admin);
            mediaService.deleteFile(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            if(e.getMessage().equals(MediaService.FILE_NOT_FOUND)){
                logService.logActivity(currentUserId, "COMMENT", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logService.logError(currentUserId, "UNAUTHORIZED", "DELETE");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException e) {
            logService.logError(currentUserId, currentUserId.toString() + " IS NOT ADMIN", "DELETE");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "DELETE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") Long id,
            @RequestHeader(value = "Authorization") String authorization) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            logService.logActivity(currentUserId, "DOWNLOAD", id);
            var res = mediaService.downloadFile(id);
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            if(e.getMessage().equals(MediaService.FILE_NOT_FOUND)){
                logService.logActivity(currentUserId, "DOWNLOAD", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logService.logError(currentUserId, "UNAUTHORIZED", "DOWNLOAD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "DOWNLOAD");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MediaDTO>> getFiles(@RequestHeader(value = "Authorization") String authorization) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            logService.logActivity(currentUserId, "GETALLFILES", null);
            List<GifFile> results = mediaService.findFiles("");
            ArrayList<MediaDTO> dtos = new ArrayList<MediaDTO>();
            for (GifFile gifFile : results) {
                dtos.add(gifFile.toMediaDTO());
            }
            return ResponseEntity.ok().body(dtos);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "GETALLFILES");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "GETALLFILES");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<MediaDTO> getFileById(
        @PathVariable("id") Long id,
        @RequestHeader(value = "Authorization") String authorization) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            logService.logActivity(currentUserId, "GETFILEBYID", id);
            var res = mediaService.getGif(id).toMediaDTO();
            return ResponseEntity.ok(res);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "GETFILEBYID");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "GETFILEBYID");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<MediaDTO>> searchFile(
        @RequestParam String query,
        @RequestHeader(value = "Authorization") String authorization
    ) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            logService.logActivity(currentUserId, "SEARCH " + query, null);
            List<GifFile> results = mediaService.findFiles(query);
            ArrayList<MediaDTO> dtos = new ArrayList<MediaDTO>();
            for (GifFile gifFile : results) {
                dtos.add(gifFile.toMediaDTO());
            }
            return ResponseEntity.ok().body(dtos);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "SEARCH");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "SEARCH");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{file_id}/modify")
    public ResponseEntity<MediaDTO> modifyFile(
        @RequestHeader(value = "Authorization") String authorization,
        @PathVariable Long file_id,
        @RequestBody EditFileDTO body
    ) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            logService.logActivity(currentUserId, "MODIFYFILE START", file_id);
            GifFile gifFile = mediaService.editFileName(file_id, body.getFileName());
            logService.logActivity(currentUserId, "MODIFYFILE OK", file_id);
            return ResponseEntity.ok().body(gifFile.toMediaDTO());
        } catch (NoSuchElementException e) {
            if(e.getMessage().equals(MediaService.FILE_NOT_FOUND)){
                logService.logActivity(currentUserId, "DOWNLOAD", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            logService.logError(currentUserId, "UNAUTHORIZED", "MODIFYFILE");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "MODIFYFILE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadFile(
        @RequestHeader(value = "Authorization") String authorization,
        @RequestParam("file") MultipartFile file
    ) {
        Long currentUserId = -1L;
        try {
            currentUserId = jwtTokenUtil.getCurrentUserId(authorization);
            logService.logActivity(currentUserId, "UPLOAD START", null);
            jwtTokenUtil.checkIfUserIsAuthenticated(authorization);
            byte[] content = IOUtils.toByteArray(file.getInputStream());
            Long id = mediaService.addCaff(content, file.getName(), currentUserId);
            if (id == -1L){
                logService.logError(currentUserId, "CANNOT UPLOAD", "UPLOAD");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            logService.logActivity(currentUserId, "UPLOAD DONE", id);
            return ResponseEntity.ok().body(id);
        } catch (NoSuchElementException e) {
            logService.logError(currentUserId, "UNAUTHORIZED", "UPLOAD");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            e.printStackTrace();
            logService.logError(currentUserId, e.getMessage(), "UPLOAD");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void checkUserRole(Long currentUserId, UserRole role) throws Exception {
        var user = userService.getUser(currentUserId);
        if (!user.getRole().equals(role)) {
            throw new IllegalArgumentException("Role mismatch");
        }
    }
}
