package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets UserRole
 */
public enum UserRole {
  ADMIN("admin"),
    REGULAR("regular");

  private String value;

  UserRole(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static UserRole fromValue(String text) {
    for (UserRole b : UserRole.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
