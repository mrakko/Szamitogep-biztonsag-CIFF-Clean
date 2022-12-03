package com.example.ciffclean.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.ciffclean.models.PublicUserDTO;
import com.example.ciffclean.models.UserRole;

import jakarta.persistence.*;

@Entity
public class AppUser {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String fullName;

  @Column
  private UserRole role;

    private String password;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

  @Column
  private String address;

  @Column
  private String email;

  @JoinColumn
  private Long imageId;

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", orphanRemoval = true)
  private List<GifFile> gifFiles = new ArrayList<GifFile>();

  public Long getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public Long getImageId() {
    return imageId;
  }

  public UserRole getRole() {
    return role;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
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

  public void setRole(UserRole role) {
    this.role = role;
  }

  public PublicUserDTO toPublicUserDTO() {
    PublicUserDTO dto = new PublicUserDTO();
    dto.setFullName(fullName);
    dto.setId(id.intValue());
    return dto;
  }
}
