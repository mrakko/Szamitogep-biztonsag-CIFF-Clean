package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CreateCommentDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")


public class CreateCommentDTO   {
  @JsonProperty("text")
  private String text = null;

  @JsonProperty("fileId")
  private Integer fileId = null;

  public CreateCommentDTO text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   **/
  @Schema(description = "")
  
    public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public CreateCommentDTO fileId(Integer fileId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateCommentDTO createCommentDTO = (CreateCommentDTO) o;
    return Objects.equals(this.text, createCommentDTO.text) &&
        Objects.equals(this.fileId, createCommentDTO.fileId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, fileId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateCommentDTO {\n");
    
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    fileId: ").append(toIndentedString(fileId)).append("\n");
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
