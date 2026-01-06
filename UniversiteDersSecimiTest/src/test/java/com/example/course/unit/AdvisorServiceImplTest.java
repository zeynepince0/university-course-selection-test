package com.example.course.unit;

import com.example.course.dto.AdvisorDecisionRequest;
import com.example.course.dto.EnrollmentResponse;
import com.example.course.entity.Enrollment;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.repository.EnrollmentRepository;
import com.example.course.service.impl.AdvisorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdvisorServiceImplTest {

    @Mock
    EnrollmentRepository enrollmentRepository;

    @InjectMocks
    AdvisorServiceImpl advisorService;

    @Test
    void approve_success() {
        Enrollment e = new Enrollment();
        e.setId(1L);
        e.setStatus(EnrollmentStatus.PENDING);

        when(enrollmentRepository.findById(1L))
                .thenReturn(Optional.of(e));
        when(enrollmentRepository.save(any()))
                .thenReturn(e);

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(1L);
        req.setDecision("APPROVE");

        EnrollmentResponse res = advisorService.decide(req);

        assertEquals("APPROVED", res.getStatus());
        assertNotNull(e.getApprovedAt());
    }

    @Test
    void reject_success() {
        Enrollment e = new Enrollment();
        e.setId(2L);

        when(enrollmentRepository.findById(2L))
                .thenReturn(Optional.of(e));
        // ensure save returns the enrollment (Mockito by default returns null for unstubbed methods)
        when(enrollmentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(2L);
        req.setDecision("REJECT");

        EnrollmentResponse res = advisorService.decide(req);

        assertEquals("REJECTED", res.getStatus());
    }

    @Test
    void invalidDecision_throws() {
        Enrollment e = new Enrollment();

        when(enrollmentRepository.findById(3L))
                .thenReturn(Optional.of(e));

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(3L);
        req.setDecision("XXX");

        assertThrows(IllegalArgumentException.class,
                () -> advisorService.decide(req));
    }

    @Test
    void enrollmentNotFound_throws() {
        when(enrollmentRepository.findById(99L))
                .thenReturn(Optional.empty());

        AdvisorDecisionRequest req = new AdvisorDecisionRequest();
        req.setEnrollmentId(99L);
        req.setDecision("APPROVE");

        assertThrows(RuntimeException.class,
                () -> advisorService.decide(req));
    }
}
