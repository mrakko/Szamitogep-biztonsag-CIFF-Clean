package com.example.ciffclean.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ciffclean.domain.Log;
import com.example.ciffclean.repositories.LogRepository;

@Component
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public Long logActivity(Long userId, String activity, Long gifId){
        return log(userId, activity, gifId, true, "");
    }


    public Long logError(Long userId, String cause, String function){
        if(cause.length() > 200){cause = cause.substring(0, 200);}
        return log(userId, function, null, false, cause);
    }

    private Long log(Long userId, String activity, Long gifId, boolean success, String details){
        Log log = new Log();
        log.setUserId(userId);
        log.setActivity(activity);
        log.setRelatedFileId(gifId);
        log.setSucceeded(success);
        log.setDetails(details);
        log.setDate(new Date());
        logRepository.save(log);
        return log.getId();
    }
}
