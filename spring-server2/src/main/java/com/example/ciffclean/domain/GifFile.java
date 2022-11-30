package com.example.ciffclean.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class GifFile {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy="gifId", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<Comment>();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;


    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Comment> getComments() { return this.comments; }
    public byte[] getContent() { return content; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }
    public void setContent(byte[] content) { this.content = content; }
}
