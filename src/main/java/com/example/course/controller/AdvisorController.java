package com.example.course.controller;

import com.example.course.dto.AdvisorDecisionRequest;
import com.example.course.dto.EnrollmentResponse;
import com.example.course.service.AdvisorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;

    public AdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @PostMapping("/decision")
    public EnrollmentResponse decide(@RequestBody AdvisorDecisionRequest request) {
        return advisorService.decide(request);
    }
}
