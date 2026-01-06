package com.example.course.dto;

public class AdvisorDecisionRequest {

    private Long enrollmentId;
    private String decision;

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getDecision() {
        return decision;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
