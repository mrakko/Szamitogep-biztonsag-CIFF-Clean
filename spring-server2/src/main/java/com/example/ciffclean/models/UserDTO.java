package com.example.ciffclean.models;

import com.example.ciffclean.domain.AppUser;

public class UserDTO   {
  private Long id;

  private UserRole role;

  private String fullName;

  private String address;

  private String email;

  private byte[] profileImage;

  public byte[] getProfileImage() {
    return profileImage;
  }
  
  public UserDTO(){}
  public UserDTO(AppUser user){
    this.id = user.getId();
    this.role = user.getRole();
    this.fullName = user.getFullName();
    this.address = user.getAddress();
    this.email = user.getEmail();
  }

  public Long getId() {
    return id;
  }

  public UserRole getRole() {
    return role;
  }

  public String getFullName() {
    return fullName;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
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
