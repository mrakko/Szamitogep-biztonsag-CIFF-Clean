package com.example.ciffclean.service;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LogService {
    public void logActivity(Long userId, String activity, Long gifId){
        var date =  new Date();
        // TODO log
    }


    public void logError(String cause, String function){
        // TODO log
    }
}
