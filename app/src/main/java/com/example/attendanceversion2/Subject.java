package com.example.attendanceversion2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject {

    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("id")
    private Integer id;
    @SerializedName("subjectName")
    private String subjectName;
    @SerializedName("lecturer")
    private Integer lecturer;


    public Subject(String subjectName) {
        this.subjectName = subjectName;
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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Integer lecturer) {
        this.lecturer = lecturer;
    }
}
