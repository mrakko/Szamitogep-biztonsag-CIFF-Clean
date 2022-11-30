package io.swagger.api;

import io.swagger.model.CreateCommentDTO;
import io.swagger.model.EditFileDTO;
import io.swagger.model.MediaDTO;
import org.springframework.core.io.Resource;
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
public class MediaApiController implements MediaApi {

    private static final Logger log = LoggerFactory.getLogger(MediaApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public MediaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> commentFile(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody CreateCommentDTO body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteFile(@Parameter(in = ParameterIn.PATH, description = "ID of the media file", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Resource> downloadFile(@Parameter(in = ParameterIn.PATH, description = "ID of the file", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Resource>(objectMapper.readValue("\"\"", Resource.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Resource>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<MediaDTO>> getFiles() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<MediaDTO>>(objectMapper.readValue("[ {\n  \"fileName\" : \"fileName\",\n  \"comments\" : [ {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  }, {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  } ],\n  \"fileId\" : 0\n}, {\n  \"fileName\" : \"fileName\",\n  \"comments\" : [ {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  }, {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  } ],\n  \"fileId\" : 0\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<MediaDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<MediaDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<MediaDTO> modifyFile(@Parameter(in = ParameterIn.PATH, description = "ID of the media file", required=true, schema=@Schema()) @PathVariable("id") Integer id,@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody EditFileDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<MediaDTO>(objectMapper.readValue("{\n  \"fileName\" : \"fileName\",\n  \"comments\" : [ {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  }, {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  } ],\n  \"fileId\" : 0\n}", MediaDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<MediaDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<MediaDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<MediaDTO>> searchFile(@Parameter(in = ParameterIn.QUERY, description = "Name of the file" ,schema=@Schema()) @Valid @RequestParam(value = "query", required = false) String query) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<MediaDTO>>(objectMapper.readValue("[ {\n  \"fileName\" : \"fileName\",\n  \"comments\" : [ {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  }, {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  } ],\n  \"fileId\" : 0\n}, {\n  \"fileName\" : \"fileName\",\n  \"comments\" : [ {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  }, {\n    \"id\" : 6,\n    \"text\" : \"text\",\n    \"commenter\" : {\n      \"role\" : \"admin\",\n      \"fullName\" : \"fullName\",\n      \"id\" : 1,\n      \"profileImage\" : \"\"\n    }\n  } ],\n  \"fileId\" : 0\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<MediaDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<MediaDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> uploadFile(@Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<String>(objectMapper.readValue("\"\"", String.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

}
