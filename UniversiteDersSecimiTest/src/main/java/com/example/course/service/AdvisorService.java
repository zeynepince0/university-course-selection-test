package com.example.course.service;

import com.example.course.dto.AdvisorDecisionRequest;
import com.example.course.dto.EnrollmentResponse;

public interface AdvisorService {

    EnrollmentResponse decide(AdvisorDecisionRequest request);
}
