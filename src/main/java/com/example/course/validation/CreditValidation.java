package com.example.course.validation;

import com.example.course.entity.Course;
import com.example.course.entity.Enrollment;
import com.example.course.entity.Student;

import java.util.List;

public class CreditValidation {

    public void validate(Student student, List<Course> selectedCourses, List<Enrollment> existingEnrollments) {
        int currentCredits = existingEnrollments.stream()
                .mapToInt(e -> e.getCourse().getCredit())
                .sum();

        int newCredits = selectedCourses.stream()
                .mapToInt(Course::getCredit)
                .sum();

        if (currentCredits + newCredits > student.getMaxCredit()) {
            throw new IllegalStateException("Credit limit exceeded");
        }
    }
}
