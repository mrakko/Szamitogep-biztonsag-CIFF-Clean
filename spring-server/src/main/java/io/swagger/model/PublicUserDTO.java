package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Publicly available infos of the user (used for identifying commenter)
 */
@Schema(description = "Publicly available infos of the user (used for identifying commenter)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")


public class PublicUserDTO   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("role")
  private UserRole role = null;

  @JsonProperty("fullName")
  private String fullName = null;

  @JsonProperty("profileImage")
  private byte[] profileImage = null;

  public PublicUserDTO id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PublicUserDTO role(UserRole role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/
  @Schema(description = "")
  
    @Valid
    public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public PublicUserDTO fullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  /**
   * Get fullName
   * @return fullName
   **/
  @Schema(description = "")
  
    public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public PublicUserDTO profileImage(byte[] profileImage) {
    this.profileImage = profileImage;
    return this;
  }

  /**
   * Get profileImage
   * @return profileImage
   **/
  @Schema(description = "")
  
    public byte[] getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(byte[] profileImage) {
    this.profileImage = profileImage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PublicUserDTO publicUserDTO = (PublicUserDTO) o;
    return Objects.equals(this.id, publicUserDTO.id) &&
        Objects.equals(this.role, publicUserDTO.role) &&
        Objects.equals(this.fullName, publicUserDTO.fullName) &&
        Objects.equals(this.profileImage, publicUserDTO.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role, fullName, profileImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PublicUserDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    profileImage: ").append(toIndentedString(profileImage)).append("\n");
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
