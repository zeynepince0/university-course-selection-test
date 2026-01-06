package com.example.course.validation;

public class QuotaValidation {

    public void validate(int currentEnrollmentCount, int quota) {
        if (currentEnrollmentCount >= quota) {
            throw new IllegalStateException("Course quota is full");
        }
    }
}
