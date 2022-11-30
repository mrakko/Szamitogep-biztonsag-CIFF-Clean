package io.swagger.api;

import io.swagger.model.ChangeUserPasswordDTO;
import io.swagger.model.CreateUserDTO;
import io.swagger.model.LoginUserDTO;
import io.swagger.model.UserTokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-11-30T07:05:42.446Z[GMT]")
@RestController
public class AuthApiController implements AuthApi {

    private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AuthApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> changePassword(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody ChangeUserPasswordDTO body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserTokenDTO> loginUser(@Parameter(in = ParameterIn.DEFAULT, description = "Returns JWT token details", required=true, schema=@Schema()) @Valid @RequestBody LoginUserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserTokenDTO>(objectMapper.readValue("{\n  \"value\" : \"value\",\n  \"userId\" : 0,\n  \"expirationDate\" : \"2000-01-23T04:56:07.000+00:00\"\n}", UserTokenDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserTokenDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserTokenDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserTokenDTO> registerUser(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateUserDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserTokenDTO>(objectMapper.readValue("{\n  \"value\" : \"value\",\n  \"userId\" : 0,\n  \"expirationDate\" : \"2000-01-23T04:56:07.000+00:00\"\n}", UserTokenDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserTokenDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<UserTokenDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
