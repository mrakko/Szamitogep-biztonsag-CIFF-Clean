package com.example.ciffclean.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Component;

import com.example.ciffclean.domain.AppUser;
import com.example.ciffclean.domain.Comment;
import com.example.ciffclean.domain.GifFile;
import com.example.ciffclean.models.CreateCommentDTO;
import com.example.ciffclean.repositories.CommentRepository;
import com.example.ciffclean.repositories.GifFileRepository;
import com.example.ciffclean.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class MediaService {

    private static final String TMP_CAFF_FOLDER_PATH = "tmp/caff";
    private static final String TMP_GIF_FOLDER_PATH = "tmp/gif";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String USER_NOT_FOUND = "User not found";
    
    private final GifFileRepository gifFileRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public MediaService(
        GifFileRepository gifFileRepository, 
        CommentRepository commentRepository, 
        UserRepository userRepository) {
        this.gifFileRepository = gifFileRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }
    
    public Long addCaff(byte[] content, String name, Long userId) throws SerialException, SQLException, InvalidPathException, NullPointerException{
        String uuid = UUID.randomUUID().toString();
        Path caff_folder_path = Paths.get(TMP_CAFF_FOLDER_PATH);
        Path gif_folder_path = Paths.get(TMP_GIF_FOLDER_PATH);
        Path caff_path = Paths.get(caff_folder_path.toString(), uuid + ".caff");
        Path gif_path = Paths.get(gif_folder_path.toString(), uuid + ".gif");
        
        
        try {
            Files.createDirectories(caff_folder_path);
            Files.createFile(caff_path);
            Files.createDirectories(gif_folder_path);
            Files.createFile(gif_path);
        }catch (IOException e) {
            System.out.println("An error occurred while creating tmp folders.");
            e.printStackTrace();
        }

        try {
            Files.write(caff_path, content);
        } catch (IOException e) {
            System.out.println("An error occurred while creating tmp caff file.");
            e.printStackTrace();
            return -1L;
        }

        Runtime rt = Runtime.getRuntime();
        try {
            var p = rt.exec(new String[]{"parser.exe", caff_path.toString(), gif_path.toString()});
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("An error occurred while converting caff file.");
            e.printStackTrace();
            return -1L;
        }

        byte[] gifContent = null;
        try {
            gifContent = Files.readAllBytes(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while reading tmp gif file.");
            e.printStackTrace();
            return -1L;
        }
        
        try {
            Files.delete(caff_path);
            Files.delete(gif_path);
        } catch (IOException e) {
            System.out.println("An error occurred while deleting tmp files.");
            e.printStackTrace();
        }

        GifFile gifFile = new GifFile();
        gifFile.setName(name);
        gifFile.setContent(gifContent);
        gifFile.setCaff(content);
        if(userId != null){
            var user = userRepository.findById(userId).get();
            gifFile.setUser(user);
        }
        gifFile.setUploadDate(new Date());
        gifFileRepository.save(gifFile);
        return gifFile.getId();
    }

    @Transactional
    public Long commentFile(CreateCommentDTO body, Long currentUserId) {
        Optional<AppUser> result = userRepository.findById(currentUserId);
        if(result.isEmpty()){
            throw new IllegalArgumentException();
        }
        AppUser currentUser = result.get();
        var file = gifFileRepository.findById(body.getFileId());
        if(file.isEmpty()){
            throw new NoSuchElementException(FILE_NOT_FOUND);
        }
        Comment newComment = new Comment();
        newComment.setUser(currentUser);
        newComment.setGifId(body.getFileId());
        newComment.setText(body.getText());
        var saved = commentRepository.save(newComment);

        file.get().addComment(saved);
        return saved.getId();
    }

    public GifFile getGif(Long id){
        var res = gifFileRepository.findById(id);
        if(res.isEmpty()){
            throw new NoSuchElementException(FILE_NOT_FOUND);
        }
        return res.get();
    }
    
    @Transactional
    public void deleteFile(Long gifId) {
        var gif = gifFileRepository.findById(gifId);
        if(gif.isEmpty()){
            throw new NoSuchElementException(FILE_NOT_FOUND);
        }
        gifFileRepository.delete(gif.get());
    }

    public List<GifFile> findFiles(String name){
        return gifFileRepository.findByNameContains(name);
    }

    @Transactional
    public GifFile editFileName(Long id, String name){
        Optional<GifFile> result = gifFileRepository.findById(id);
        if (result.isEmpty()){
            throw new NoSuchElementException(FILE_NOT_FOUND);
        }
        GifFile gifFile = result.get();
        gifFile.setName(name);
        gifFileRepository.save(gifFile);
        return gifFile;
    }
    
    public byte[] downloadFile(Long id) {
        var file = gifFileRepository.findById(id);
        if(file.isEmpty()){
            throw new NoSuchElementException(FILE_NOT_FOUND);
        }
        return file.get().getContent();
    }

}

