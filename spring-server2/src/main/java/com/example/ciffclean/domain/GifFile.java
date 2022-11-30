package com.example.ciffclean.domain;

import java.util.ArrayList;

import jakarta.persistence.*;

public class GifFile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "GIF_ID")
    private ArrayList<Comment> comments = new ArrayList<>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;


    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public ArrayList<Comment> getComments() { return this.comments; }
    public byte[] getContent() { return content; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }
}
