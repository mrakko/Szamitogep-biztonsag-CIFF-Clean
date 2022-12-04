package com.example.ciffclean.domain;

import java.util.ArrayList;
import java.util.Date;
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

    @ManyToOne(cascade = CascadeType.MERGE)
    private AppUser user;

    @Column
    private Date uploadDate;

    public Date getUploadDate() {
        return (Date) uploadDate.clone();
    }
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = (Date) uploadDate.clone();
    }
    public Long getId() { return this.id; }
    public String getName() { return this.name; }
    public List<Comment> getComments() { return new ArrayList<Comment>(this.comments); }
    public byte[] getContent() { return content.clone(); }
    public AppUser getUser() { return user; }
    public byte[] getCaff() { return caff.clone(); }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setComments(List<Comment> comments) { this.comments = new ArrayList<Comment>(comments); }
    public void setContent(byte[] content) { this.content = content.clone(); }
    public void setUser(AppUser user) { this.user = user; }
    public void setCaff(byte[] caff) { this.caff = caff.clone(); }
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
        dto.setUploadDate(uploadDate);
        dto.setUploader(user.toPublicUserDTO());
        return dto;
    }
}
