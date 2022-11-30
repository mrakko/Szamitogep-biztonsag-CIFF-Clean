package com.example.ciffclean.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ciffclean.models.EditUserDTO;
import com.example.ciffclean.models.UserDTO;
import com.example.ciffclean.repositories.GifFileRepository;
import com.example.ciffclean.repositories.UserRepository;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GifFileRepository gifFileRepository;
    
    public UserDTO getUser(Long currentUserId) throws Exception {
        var user = userRepository.findById(currentUserId);
        if(user == null){
            throw new NoSuchElementException("No logged in user");
        }
        var res = new UserDTO(user.get());

        var image = gifFileRepository.findById(user.get().getImageId());
        if(image != null){ res.setImage(image.get().getContent());}

        return res;
    }

    public UserDTO editUser(EditUserDTO user, Long currentUserId) throws Exception{
        if(currentUserId != user.getId()){
            throw new IllegalArgumentException("Users can modify only their own personal data.");
        }
        var userToEdit = userRepository.findById(currentUserId);
        if(userToEdit == null){
            throw new NoSuchElementException("No logged in user");
        }

        // TODO profilkép változása ?
        // TODO edituserdto missing id in yaml
        var editedUser = userToEdit.get();
        editedUser.setFullName(user.getFullName());
        editedUser.setEmail(user.getEmail());
        editedUser.setAddress(user.getAddress());

        var res = new UserDTO(editedUser);
    
        userRepository.save(editedUser);

        var image = gifFileRepository.findById(editedUser.getImageId());
        if(image != null){ res.setImage(image.get().getContent());}

        return res;
    }

    
}
