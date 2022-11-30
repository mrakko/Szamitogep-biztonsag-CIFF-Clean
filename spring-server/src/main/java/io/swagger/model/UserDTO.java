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
 * UserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")


public class UserDTO   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("role")
  private UserRole role = null;

  @JsonProperty("fullName")
  private String fullName = null;

  @JsonProperty("address")
  private String address = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("profileImage")
  private byte[] profileImage = null;

  public UserDTO id(Integer id) {
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

  public UserDTO role(UserRole role) {
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

  public UserDTO fullName(String fullName) {
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

  public UserDTO address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(description = "")
  
    public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public UserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(description = "")
  
    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDTO profileImage(byte[] profileImage) {
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
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(this.id, userDTO.id) &&
        Objects.equals(this.role, userDTO.role) &&
        Objects.equals(this.fullName, userDTO.fullName) &&
        Objects.equals(this.address, userDTO.address) &&
        Objects.equals(this.email, userDTO.email) &&
        Objects.equals(this.profileImage, userDTO.profileImage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, role, fullName, address, email, profileImage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    fullName: ").append(toIndentedString(fullName)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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
