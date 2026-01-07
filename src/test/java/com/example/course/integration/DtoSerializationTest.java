package com.example.course.integration;

import com.example.course.dto.*;
import com.example.course.dto.EnrollmentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DtoSerializationTest {

    @Autowired ObjectMapper objectMapper;

    @Test
    void departmentDto_serializes() throws Exception {
        DepartmentDto dto = new DepartmentDto(1L, "D1", "Department 1");
        String json = objectMapper.writeValueAsString(dto);
        assertTrue(json.contains("D1"));
        DepartmentDto read = objectMapper.readValue(json, DepartmentDto.class);
        assertEquals(dto.getCode(), read.getCode());
    }

    @Test
    void advisorDecisionRequest_serializes() throws Exception {
        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(10L);
        req.setDecision("APPROVE");
        String json = objectMapper.writeValueAsString(req);
        AdvisorDecisionRequest r2 = objectMapper.readValue(json, AdvisorDecisionRequest.class);
        assertEquals(req.getDecision(), r2.getDecision());
    }

    @Test
    void enrollmentResponse_serializes() throws Exception {
        EnrollmentResponse r = new EnrollmentResponse(5L, "C1", "Course 1", "PENDING");
        String json = objectMapper.writeValueAsString(r);
        EnrollmentResponse r2 = objectMapper.readValue(json, EnrollmentResponse.class);
        assertEquals(r.getCourseCode(), r2.getCourseCode());
    }

    @Test
    void courseSelectionRequest_serializes() throws Exception {
        CourseSelectionRequest req = new CourseSelectionRequest();
        req.setStudentNumber("S1");
        req.setCourseCodes(java.util.List.of("C1","C2"));
        String json = objectMapper.writeValueAsString(req);
        CourseSelectionRequest r2 = objectMapper.readValue(json, CourseSelectionRequest.class);
        assertEquals(req.getStudentNumber(), r2.getStudentNumber());
    }

    @Test
    void semesterDto_serializes() throws Exception {
        SemesterDto dto = new SemesterDto(2L, "2027-1");
        String json = objectMapper.writeValueAsString(dto);
        SemesterDto dto1 = objectMapper.readValue(json, SemesterDto.class);
        assertEquals(dto.getName(), dto1.getName());
    }
}