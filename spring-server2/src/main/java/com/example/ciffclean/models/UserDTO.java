package com.example.ciffclean.models;

import com.example.ciffclean.domain.User;
import com.example.ciffclean.domain.UserRole;

public class UserDTO   {
  private Long id;

  private UserRole role;

  private String fullName;

  private String address;

  private String email;

  private byte[] profileImage;

  public UserDTO(){}
  public UserDTO(User user){
    this.id = user.getId();
    this.role = user.getRole();
    this.fullName = user.getFullName();
    this.address = user.getAddress();
    this.email = user.getEmail();
  }

  public void setImage(byte[] value){
    this.profileImage = value;
  }
  public void setFullName(String value) {
    this.fullName = value;
  }
  public void setEmail(String value) {
    this.email = value;
  }
  public void setAddress(String value) {
    this.address = value;
  }
}
