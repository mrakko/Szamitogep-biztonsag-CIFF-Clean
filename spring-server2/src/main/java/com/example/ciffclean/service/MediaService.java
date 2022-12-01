package com.example.ciffclean.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.ciffclean.domain.Comment;
import com.example.ciffclean.domain.GifFile;
import com.example.ciffclean.models.CreateCommentDTO;
import com.example.ciffclean.repositories.CommentRepository;
import com.example.ciffclean.repositories.GifFileRepository;

import jakarta.transaction.Transactional;

@Component
public class MediaService {

    private static final String TMP_CAFF_FOLDER_PATH = "tmp/caff";
    private static final String TMP_GIF_FOLDER_PATH = "tmp/gif";
    
    private final GifFileRepository gifFileRepository;
    private final CommentRepository commentRepository;

    public MediaService(GifFileRepository gifFileRepository, CommentRepository commentRepository) {
        this.gifFileRepository = gifFileRepository;
        this.commentRepository = commentRepository;
    }
    
    public Long addCaff(byte[] content, String name, Long userId){
        String uuid = UUID.randomUUID().toString();
        Path caff_path = Paths.get(TMP_CAFF_FOLDER_PATH + "/" + uuid + ".caff");
        Path gif_path = Paths.get(TMP_GIF_FOLDER_PATH + "/" + uuid + ".gif");
        
        try {
            Files.createDirectories(caff_path.getParent());
            Files.createFile(caff_path);
            Files.createDirectories(gif_path.getParent());
            Files.createFile(gif_path);
        } catch (IOException e) {
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
            rt.exec(new String[]{"parser.exe", caff_path.toString(), gif_path.toString()});
        } catch (IOException e) {
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
        gifFile.setUserId(userId);
        gifFileRepository.save(gifFile);
        return gifFile.getId();
    }

    @Transactional
    public void commentFile(CreateCommentDTO body, Long currentUserId) {
        var file = gifFileRepository.findById(body.getFileId());
        if(file.equals(Optional.empty())){
            throw new IllegalArgumentException();
        }
        Comment newComment = new Comment();
        newComment.setUserId(currentUserId);
        newComment.setGifId(body.getFileId());
        newComment.setText(body.getText());
        var saved = commentRepository.save(newComment);

        var newComments = new ArrayList<Comment>(file.get().getComments());
        newComments.add(saved);
        file.get().setComments(newComments);
        
    }

    public void deleteFile(Long gifId) {
        var gif = gifFileRepository.findById(gifId);
        if(gif.equals(Optional.empty())){
            throw new IllegalArgumentException();
        }
        gifFileRepository.deleteById(gifId);
    }

}
