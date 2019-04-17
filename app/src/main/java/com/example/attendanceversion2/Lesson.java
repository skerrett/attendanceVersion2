package com.example.attendanceversion2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Lesson {

    @SerializedName("createdAt")

    private String createdAt;
    @SerializedName("updatedAt")

    private String updatedAt;
    @SerializedName("id")

    private Integer id;
    @SerializedName("date")

    private String date;
    @SerializedName("subject")
    private Subject subject;

    public Lesson(String createdAt, String updatedAt, Integer id, String date, Subject subject) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.date = date;
        this.subject = subject;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

}

