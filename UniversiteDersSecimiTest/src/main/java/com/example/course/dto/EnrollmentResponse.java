package com.example.course.dto;
public class EnrollmentResponse {

    private Long enrollmentId;
    private String courseCode;
    private String courseName;
    private String status;

    public EnrollmentResponse(Long enrollmentId, String courseCode, String courseName, String status) {
        this.enrollmentId = enrollmentId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.status = status;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStatus() {
        return status;
    }
}
