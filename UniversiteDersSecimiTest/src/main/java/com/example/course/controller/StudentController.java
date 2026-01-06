package com.example.course.controller;

import com.example.course.dto.CourseSelectionRequest;
import com.example.course.dto.EnrollmentResponse;
import com.example.course.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/courses")
    public List<EnrollmentResponse> selectCourses(@RequestBody CourseSelectionRequest request) {
        return studentService.selectCourses(
                request.getStudentNumber(),
                request.getCourseCodes()
        );
    }

    @GetMapping("/{studentNumber}/enrollments")
    public List<EnrollmentResponse> getEnrollments(@PathVariable String studentNumber) {
        return studentService.getEnrollments(studentNumber);
    }
}
