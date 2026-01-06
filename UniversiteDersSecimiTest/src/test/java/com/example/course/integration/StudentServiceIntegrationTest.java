package com.example.course.integration;

import com.example.course.dto.CourseSelectionRequest;
import com.example.course.dto.EnrollmentResponse;
import com.example.course.entity.*;
import com.example.course.repository.*;
import com.example.course.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StudentServiceIntegrationTest {

    @Autowired StudentService studentService;
    @Autowired StudentRepository studentRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired DepartmentRepository departmentRepository;

    @Test
    void selectCourses_and_getEnrollments() {
        Department d = new Department(); d.setName("Physics"); departmentRepository.save(d);
        Course c1 = new Course(); c1.setCode("PHY1"); c1.setName("Physics 1"); c1.setCredit(3); c1.setQuota(10); c1.setClassYear(1); c1.setDepartment(d); courseRepository.save(c1);
        Course c2 = new Course(); c2.setCode("PHY2"); c2.setName("Physics 2"); c2.setCredit(3); c2.setQuota(10); c2.setClassYear(1); c2.setDepartment(d); courseRepository.save(c2);
        Student s = new Student(); s.setStudentNumber("STU100"); s.setClassYear(1); s.setMaxCredit(10); studentRepository.save(s);

        CourseSelectionRequest req = new CourseSelectionRequest();
        req.setStudentNumber(s.getStudentNumber());
        req.setCourseCodes(List.of(c1.getCode(), c2.getCode()));

        List<EnrollmentResponse> responses = studentService.selectCourses(req.getStudentNumber(), req.getCourseCodes());

        assertEquals(2, responses.size());
        List<EnrollmentResponse> got = studentService.getEnrollments(s.getStudentNumber());
        assertEquals(2, got.size());
    }
}