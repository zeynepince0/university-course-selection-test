package com.example.course.integration;

import com.example.course.dto.AdvisorDecisionRequest;
import com.example.course.dto.EnrollmentResponse;
import com.example.course.entity.*;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.repository.*;
import com.example.course.service.AdvisorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AdvisorServiceIT {

    @Autowired AdvisorService advisorService;
    @Autowired EnrollmentRepository enrollmentRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired SemesterRepository semesterRepository;

    @Test
    void approveAndRejectFlow() {
        Department d = new Department(); d.setName("Math"); departmentRepository.save(d);
        Course c = new Course(); c.setCode("MATH1"); c.setName("Math 1"); c.setCredit(4); c.setDepartment(d); courseRepository.save(c);
        Student s = new Student(); s.setStudentNumber("STU1"); studentRepository.save(s);
        Semester sem = new Semester(); sem.setName("2025-1"); semesterRepository.save(sem);

        Enrollment e = new Enrollment();
        e.setCourse(c); e.setStudent(s); e.setSemester(sem);
        e.setStatus(EnrollmentStatus.PENDING); e.setCreatedAt(LocalDateTime.now());
        enrollmentRepository.save(e);

        AdvisorDecisionRequest approveReq = new AdvisorDecisionRequest();
        approveReq.setEnrollmentId(e.getId());
        approveReq.setDecision("APPROVE");
        EnrollmentResponse res = advisorService.decide(approveReq);
        assertEquals("APPROVED", res.getStatus());

        AdvisorDecisionRequest rejectReq = new AdvisorDecisionRequest();
        rejectReq.setEnrollmentId(e.getId());
        rejectReq.setDecision("REJECT");
        EnrollmentResponse res2 = advisorService.decide(rejectReq);
        assertEquals("REJECTED", res2.getStatus());
    }
}













