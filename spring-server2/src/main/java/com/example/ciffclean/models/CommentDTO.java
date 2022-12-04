package com.example.ciffclean.models;

public class CommentDTO   {
  private Integer id = null;

  private String text = null;

  private PublicUserDTO commenter = null;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public PublicUserDTO getCommenter() {
    return new PublicUserDTO(commenter);
  }

  public void setCommenter(PublicUserDTO commenter) {
    this.commenter = new PublicUserDTO(commenter);
  }

}
