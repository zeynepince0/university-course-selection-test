package com.example.course.integration;

import com.example.course.entity.*;
import com.example.course.repository.*;
import com.example.course.entity.EnrollmentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RepositoryIntegrationTest {

    @Autowired DepartmentRepository departmentRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired SemesterRepository semesterRepository;
    @Autowired EnrollmentRepository enrollmentRepository;

    @Test
    void enrollmentRepository_basicOperations() {
        Department d = new Department(); d.setName("Computer Science"); departmentRepository.save(d);
        Course c = new Course(); c.setCode("CS101"); c.setName("Intro to CS"); c.setCredit(3); c.setDepartment(d); courseRepository.save(c);
        Student s = new Student(); s.setStudentNumber("S100"); studentRepository.save(s);
        Semester sem = new Semester(); sem.setName("2025-1"); semesterRepository.save(sem);

        Enrollment e = new Enrollment();
        e.setCourse(c); e.setStudent(s); e.setSemester(sem);
        e.setStatus(EnrollmentStatus.APPROVED); e.setCreatedAt(LocalDateTime.now());
        enrollmentRepository.save(e);

        assertEquals(1, enrollmentRepository.countByCourse(c));
        int sum = enrollmentRepository.sumApprovedCreditsByStudent(s);
        assertEquals(3, sum);
    }
}