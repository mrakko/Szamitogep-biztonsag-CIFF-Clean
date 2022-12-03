package com.example.ciffclean.models;

public class PublicUserDTO   {
  private Integer id = null;

  private UserRole role = null;

  private String fullName = null;

  private byte[] profileImage = null;

  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public byte[] getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(byte[] profileImage) {
    this.profileImage = profileImage;
  }
}
