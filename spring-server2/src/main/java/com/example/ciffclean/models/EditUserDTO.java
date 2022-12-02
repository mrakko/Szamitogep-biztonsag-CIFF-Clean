package com.example.ciffclean.models;

public class EditUserDTO   {

  private String fullName;

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  private String email;

  public void setEmail(String email) {
    this.email = email;
  }
  private String address;

  public void setAddress(String address) {
    this.address = address;
  }
  public String getFullName(){
    return fullName;
  }
  public String getEmail(){
    return email;
  }
  public String getAddress(){
    return address;
  }
}
