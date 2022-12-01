package com.example.ciffclean.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ciffclean.models.EditUserDTO;
import com.example.ciffclean.models.UserDTO;
import com.example.ciffclean.repositories.GifFileRepository;
import com.example.ciffclean.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GifFileRepository gifFileRepository;
    
    public UserDTO getUser(Long currentUserId) throws Exception {
        var user = userRepository.findById(currentUserId);
        if(user.isEmpty()){
            throw new NoSuchElementException("Unauthorized.");
        }
        var res = new UserDTO(user.get());

        var image = gifFileRepository.findById(user.get().getImageId());
        if(!image.isEmpty()){ res.setImage(image.get().getContent());}

        return res;
    }

    @Transactional
    public UserDTO editUser(EditUserDTO user, Long currentUserId) throws Exception{
        var userToEdit = userRepository.findById(currentUserId);
        if(userToEdit.isEmpty()){
            throw new NoSuchElementException("Unauthorized.");
        }

        // TODO profilkép változása?
        var editedUser = userToEdit.get();
        editedUser.setFullName(user.getFullName());
        editedUser.setEmail(user.getEmail());
        editedUser.setAddress(user.getAddress());

        var res = new UserDTO(editedUser);
    
        userRepository.save(editedUser);

        var image = gifFileRepository.findById(editedUser.getImageId());
        if(!image.isEmpty()){ res.setImage(image.get().getContent());}

        return res;
    }

    
}
