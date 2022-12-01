package com.example.ciffclean.models;

import org.springframework.core.io.Resource;


public class MediaUploadBody   {
  private Resource file = null;

  public Resource getFile() {
    return file;
  }

  public void setFile(Resource file) {
    this.file = file;
  }
}
