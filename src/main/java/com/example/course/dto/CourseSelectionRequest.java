package com.example.course.dto;

import java.util.List;

public class CourseSelectionRequest {

    private String studentNumber;
    private List<String> courseCodes;

    public String getStudentNumber() {
        return studentNumber;
    }

    public List<String> getCourseCodes() {
        return courseCodes;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setCourseCodes(List<String> courseCodes) {
        this.courseCodes = courseCodes;
    }
}
