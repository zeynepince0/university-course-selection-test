package com.example.course.integration;

import com.example.course.dto.SemesterDto;
import com.example.course.repository.SemesterRepository;
import com.example.course.service.SemesterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class SemesterServiceIntegrationTest {

    @Autowired SemesterService semesterService;
    @Autowired SemesterRepository semesterRepository;

    @Test
    void createAndGetSemester() {
        SemesterDto dto = new SemesterDto(null, "2030-1");
        SemesterDto created = semesterService.create(dto);

        assertNotNull(created.getId());
        assertEquals("2030-1", created.getName());

        SemesterDto fetched = semesterService.getByName("2030-1");
        assertEquals("2030-1", fetched.getName());

        List<SemesterDto> all = semesterService.getAll();
        assertTrue(all.stream().anyMatch(s -> "2030-1".equals(s.getName())));
    }
}