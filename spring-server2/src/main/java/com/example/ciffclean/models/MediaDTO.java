package com.example.ciffclean.models;
import java.util.Date;
import java.util.List;

public class MediaDTO {
    private Integer fileId = null;

    private String fileName = null;

    private List<CommentDTO> comments = null;

    private Date uploadDate;
    
    private PublicUserDTO uploader;
    
    public PublicUserDTO getUploader() {
      return uploader;
    }

    public void setUploader(PublicUserDTO uploader) {
      this.uploader = uploader;
    }

    public Date getUploadDate() {
      return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
      this.uploadDate = uploadDate;
    }

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
