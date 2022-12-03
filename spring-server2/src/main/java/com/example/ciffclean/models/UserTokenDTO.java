package com.example.ciffclean.models;

import java.time.LocalDateTime;

public class UserTokenDTO{
    
    private Long userId;

    private String token;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }
}  