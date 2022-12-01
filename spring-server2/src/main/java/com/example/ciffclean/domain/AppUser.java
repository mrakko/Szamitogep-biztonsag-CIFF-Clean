package com.example.ciffclean.domain;

import jakarta.persistence.*;

@Entity
public class AppUser {
    
    @Id
    @GeneratedValue
    private Long id;

    public Long getId(){ return id;}

    @Column
    private String fullName;

    public String getFullName(){ return fullName;}

    @Column
    private UserRole role;

    public UserRole getRole(){ return role;}
    
    @Column
    private String address;

    public String getAddress(){ return address;}

    @Column
    private String email;

    public String getEmail(){ return email;}

    // TODO ez string vagy ciff hivatkozás?
    @JoinColumn
    private Long imageId;

    public Long getImageId(){ return imageId;}

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
