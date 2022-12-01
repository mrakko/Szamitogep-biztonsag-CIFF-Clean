package com.example.ciffclean.domain;

import jakarta.persistence.*;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    private Long gifId;

    @JoinColumn
    private Long userId;

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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
