package com.example.ciffclean.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.ciffclean.models.CommentDTO;
import com.example.ciffclean.models.MediaDTO;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] caff;

    @JoinColumn
    private Long userId;

    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Comment> getComments() { return this.comments; }
    public byte[] getContent() { return content; }
    public Long getUserId() { return userId; }
    public byte[] getCaff() { return caff; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setComments(ArrayList<Comment> comments) { this.comments = comments; }
    public void setContent(byte[] content) { this.content = content; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setCaff(byte[] caff) { this.caff = caff; }
    public void addComment(Comment c){this.comments.add(c);}

    public MediaDTO toMediaDTO(){
        MediaDTO dto = new MediaDTO();
        dto.setFileId(id.intValue());
        ArrayList<CommentDTO> dtos = new ArrayList<CommentDTO>();
        for (Comment comment : comments) {
            dtos.add(comment.toCommentDTO());
        }
        dto.setComments(dtos);
        dto.setFileName(name);
        return dto;
    }
}
