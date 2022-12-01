package com.example.ciffclean.domain;

import java.util.ArrayList;
import java.util.List;

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
    
    @Column
    private String address;

    @Column
    private String email;

    @JoinColumn
    private Long imageId;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy="userId", orphanRemoval = true)
    private List<GifFile> gifFiles = new ArrayList<GifFile>();

    public Long getId(){ return id;}

    public String getFullName(){ return fullName;}

    public Long getImageId(){ return imageId;}

    public UserRole getRole(){ return role;}

    public String getAddress(){ return address;}

    public String getEmail(){ return email;}


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
