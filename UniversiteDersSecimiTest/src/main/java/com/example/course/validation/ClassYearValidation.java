package com.example.course.validation;

public class ClassYearValidation {

    public void validate(int studentClassYear, int courseClassYear) {
        if (studentClassYear < courseClassYear) {
            throw new IllegalStateException("Student class year is not sufficient");
        }
    }
}
