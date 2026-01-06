package com.example.course.service;

import com.example.course.dto.EnrollmentResponse;

import java.util.List;

public interface StudentService {

    List<EnrollmentResponse> selectCourses(String studentNumber, List<String> courseCodes);

    List<EnrollmentResponse> getEnrollments(String studentNumber);
}
