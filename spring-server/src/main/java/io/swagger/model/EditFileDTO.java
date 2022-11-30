package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EditFileDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")


public class EditFileDTO   {
  @JsonProperty("fileName")
  private String fileName = null;

  public EditFileDTO fileName(String fileName) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EditFileDTO editFileDTO = (EditFileDTO) o;
    return Objects.equals(this.fileName, editFileDTO.fileName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fileName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EditFileDTO {\n");
    
    sb.append("    fileName: ").append(toIndentedString(fileName)).append("\n");
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
