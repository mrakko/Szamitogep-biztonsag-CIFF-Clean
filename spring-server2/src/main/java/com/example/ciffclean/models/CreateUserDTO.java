package com.example.ciffclean.models;

public class CreateUserDTO {

    private String address;

    private String email;

    private String password;

    private String fullName;

    private String profilImage;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }
}
