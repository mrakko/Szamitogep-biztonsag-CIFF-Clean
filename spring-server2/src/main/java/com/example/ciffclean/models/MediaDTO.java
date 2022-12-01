package com.example.ciffclean.models;
import java.util.List;

public class MediaDTO {
    private Integer fileId = null;

    private String fileName = null;

    private List<CommentDTO> comments = null;

    
    public Integer getFileId() {
      return fileId;
    }

    public void setFileId(Integer fileId) {
      this.fileId = fileId;
    }
    
    public String getFileName() {
      return fileName;
    }

    public void setFileName(String fileName) {
      this.fileName = fileName;
    }

    public List<CommentDTO> getComments() {
      return comments;
    }

    public void setComments(List<CommentDTO> comments) {
      this.comments = comments;
    }


}
