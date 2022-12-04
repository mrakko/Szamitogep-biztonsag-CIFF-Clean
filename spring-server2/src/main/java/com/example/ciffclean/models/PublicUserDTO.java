package com.example.ciffclean.models;

public class PublicUserDTO   {
  private Integer id = null;

  private UserRole role = null;

  private String fullName = null;

  private byte[] profileImage = null;

  public PublicUserDTO() {}

  public PublicUserDTO(PublicUserDTO other) {
    this.id = other.id;
    this.role = other.role;
    if(other.profileImage != null)
      this.profileImage = other.profileImage.clone();
    this.fullName = other.fullName;
  }

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
    if(profileImage != null)
      return profileImage.clone();
    return null;
  }

  public void setProfileImage(byte[] profileImage) {
    if(profileImage != null)
      this.profileImage = profileImage.clone();
  }

}
