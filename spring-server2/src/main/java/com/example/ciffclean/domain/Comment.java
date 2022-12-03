package com.example.ciffclean.domain;

import com.example.ciffclean.models.CommentDTO;

import jakarta.persistence.*;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    private Long gifId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @Column
    private String text;

    public Long getId() { return id; }
    public String getText() { return this.text; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }

    public Long getGifId() {
        return gifId;
    }
    public void setGifId(Long gifId) {
        this.gifId = gifId;
    }
    public AppUser getUser() {
        return user;
    }
    public void setUser(AppUser user) {
        this.user = user;
    }

    public CommentDTO toCommentDTO(){
        CommentDTO dto = new CommentDTO();
        dto.setId(id.intValue());
        dto.setText(text);
        dto.setCommenter(user.toPublicUserDTO());
        return dto;
    }
}
