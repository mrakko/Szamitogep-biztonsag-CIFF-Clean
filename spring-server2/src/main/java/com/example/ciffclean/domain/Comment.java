package com.example.ciffclean.domain;

import jakarta.persistence.*;

@Entity
public class Comment {
    
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    private Long gifId;

    // TODO user

    @Column
    private String text;

    public Long getId() { return id; }
    public String getText() { return this.text; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
}
