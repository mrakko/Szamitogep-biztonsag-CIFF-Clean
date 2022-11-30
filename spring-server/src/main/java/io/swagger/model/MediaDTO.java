package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.CommentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MediaDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")


public class MediaDTO   {
  @JsonProperty("fileId")
  private Integer fileId = null;

  @JsonProperty("fileName")
  private String fileName = null;

  @JsonProperty("comments")
  @Valid
  private List<CommentDTO> comments = null;

  public MediaDTO fileId(Integer fileId) {
    this.fileId = fileId;
    return this;
  }

  /**
   * Get fileId
   * @return fileId
   **/
  @Schema(description = "")
  
    public Integer getFileId() {
    return fileId;
  }

  public void setFileId(Integer fileId) {
    this.fileId = fileId;
  }

  public MediaDTO fileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  /**
   * Get fileName
   * @return fileName
   **/
  @Schema(description = "")
  
    public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public MediaDTO comments(List<CommentDTO> comments) {
    this.comments = comments;
    return this;
  }

  public MediaDTO addCommentsItem(CommentDTO commentsItem) {
    if (this.comments == null) {
      this.comments = new ArrayList<CommentDTO>();
    }
    this.comments.add(commentsItem);
    return this;
  }

  /**
   * Get comments
   * @return comments
   **/
  @Schema(description = "")
      @Valid
    public List<CommentDTO> getComments() {
    return comments;
  }

  public void setComments(List<CommentDTO> comments) {
    this.comments = comments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaDTO mediaDTO = (MediaDTO) o;
    return Objects.equals(this.fileId, mediaDTO.fileId) &&
        Objects.equals(this.fileName, mediaDTO.fileName) &&
        Objects.equals(this.comments, mediaDTO.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileId, fileName, comments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MediaDTO {\n");
    
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
    sb.append("    fileName: ").append(toIndentedString(fileName)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
