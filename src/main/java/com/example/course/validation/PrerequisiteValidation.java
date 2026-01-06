package com.example.course.validation;

import com.example.course.entity.Course;
import com.example.course.entity.CoursePrerequisite;
import com.example.course.entity.Enrollment;
import com.example.course.entity.EnrollmentStatus;

import java.util.List;

public class PrerequisiteValidation {

    public void validate(Course course,
                         List<CoursePrerequisite> prerequisites,
                         List<Enrollment> studentEnrollments) {

        for (CoursePrerequisite prerequisite : prerequisites) {
            boolean completed = studentEnrollments.stream()
                    .anyMatch(e ->
                            e.getCourse().getId().equals(prerequisite.getPrerequisiteCourse().getId()) &&
                                    e.getStatus() == EnrollmentStatus.APPROVED
                    );

            if (!completed) {
                throw new IllegalStateException("Prerequisite course not completed");
            }
        }
    }
}

