package com.example.ciffclean.domain;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Log {
    @Id
    @GeneratedValue
    private Long id;

    @JoinColumn
    private Long userId;

    @Column
    private String activity;

    @JoinColumn
    private Long relatedFileId;

    @Column
    private boolean succeeded;

    @Column
    private String details;

    @Column
    private Date date;

    public Date getDate() {
        return (Date) date.clone();
    }
    public void setDate(Date date) {
        this.date = (Date) date.clone();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public Long getRelatedFileId() {
        return relatedFileId;
    }
    public void setRelatedFileId(Long relatedFileId) {
        this.relatedFileId = relatedFileId;
    }
    public boolean isSucceeded() {
        return succeeded;
    }
    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
