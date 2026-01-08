package com.example.course.integration;

import com.example.course.dto.AdvisorDecisionRequest;
import com.example.course.dto.CourseSelectionRequest;
import com.example.course.dto.DepartmentDto;
import com.example.course.entity.*;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ControllerIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired EnrollmentRepository enrollmentRepository;
    @Autowired SemesterRepository semesterRepository;

    @Test
    void advisorDecisionEndpoint() throws Exception {
        Department d = new Department(); d.setName("CS"); departmentRepository.save(d);
        Course c = new Course(); c.setCode("CS200"); c.setName("Algo"); c.setCredit(3); c.setDepartment(d); courseRepository.save(c);
        Student s = new Student(); s.setStudentNumber("STU9"); studentRepository.save(s);
        Semester sem = new Semester(); sem.setName("2025-1"); semesterRepository.save(sem);
        Enrollment e = new Enrollment(); e.setStudent(s); e.setCourse(c); e.setSemester(sem); e.setStatus(EnrollmentStatus.PENDING); e.setCreatedAt(LocalDateTime.now()); enrollmentRepository.save(e);

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(e.getId());
        req.setDecision("APPROVE");

        mockMvc.perform(post("/advisors/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("APPROVED")));
    }

    @Test
    void studentEndpoints_courseSelectionAndGetEnrollments() throws Exception {
        Department d = new Department(); d.setName("ECO"); departmentRepository.save(d);
        Course c1 = new Course(); c1.setCode("ECO1"); c1.setName("Econ1"); c1.setCredit(3); c1.setQuota(10); c1.setClassYear(1); c1.setDepartment(d); courseRepository.save(c1);
        Course c2 = new Course(); c2.setCode("ECO2"); c2.setName("Econ2"); c2.setCredit(3); c2.setQuota(10); c2.setClassYear(1); c2.setDepartment(d); courseRepository.save(c2);
        Student s = new Student(); s.setStudentNumber("STU10"); s.setClassYear(1); s.setMaxCredit(10); studentRepository.save(s);

        CourseSelectionRequest req = new CourseSelectionRequest();
        req.setStudentNumber(s.getStudentNumber());
        req.setCourseCodes(List.of(c1.getCode(), c2.getCode()));

        mockMvc.perform(post("/students/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/students/" + s.getStudentNumber() + "/enrollments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void departmentEndpoints_createAndGet() throws Exception {
        DepartmentDto dto = new DepartmentDto(null, "D100", "Dept100");

        mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("D100")));

        mockMvc.perform(get("/departments/D100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dept100")));
    }

    @Test
    void semesterEndpoints_createAndGet() throws Exception {
        com.example.course.dto.SemesterDto dto = new com.example.course.dto.SemesterDto(null, "2026-1");

        mockMvc.perform(post("/semesters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("2026-1")));
    }
    @Test
    void advisorDecision_invalidDecision_400() throws Exception {
        Department d = new Department(); d.setName("CS"); departmentRepository.save(d);

        Course c = new Course();
        c.setCode("CS101"); c.setName("Algo"); c.setCredit(3); c.setDepartment(d);
        courseRepository.save(c);

        Student s = new Student();
        s.setStudentNumber("STU1");
        studentRepository.save(s);

        Semester sem = new Semester();
        sem.setName("2025-1");
        semesterRepository.save(sem);

        Enrollment e = new Enrollment();
        e.setCourse(c);
        e.setStudent(s);
        e.setSemester(sem);
        e.setStatus(EnrollmentStatus.PENDING);
        e.setCreatedAt(LocalDateTime.now());
        enrollmentRepository.save(e);

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(e.getId());
        req.setDecision("XXX");

        mockMvc.perform(post("/advisors/decision")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void selectCourse_studentNotFound_404() throws Exception {
        CourseSelectionRequest req = new CourseSelectionRequest();
        req.setStudentNumber("XXX");
        req.setCourseCodes(List.of("C1"));

        mockMvc.perform(post("/students/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }


}