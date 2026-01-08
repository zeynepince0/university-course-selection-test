package com.example.course.unit;

import com.example.course.entity.Course;
import com.example.course.entity.CoursePrerequisite;
import com.example.course.entity.Enrollment;
import com.example.course.entity.EnrollmentStatus;
import com.example.course.validation.PrerequisiteValidation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrequisiteValidationTest {

    PrerequisiteValidation validation = new PrerequisiteValidation();

    @Test
    void prerequisiteCompleted_passes() {
        Course prereq = new Course();
        prereq.setId(1L);

        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setPrerequisiteCourse(prereq);

        Enrollment e = new Enrollment();
        e.setCourse(prereq);
        e.setStatus(EnrollmentStatus.APPROVED);

        assertDoesNotThrow(() ->
                validation.validate(new Course(), List.of(cp), List.of(e)));
    }

    @Test
    void prerequisiteNotCompleted_throws() {
        Course prereq = new Course();
        prereq.setId(1L);

        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setPrerequisiteCourse(prereq);

        assertThrows(IllegalStateException.class,
                () -> validation.validate(new Course(), List.of(cp), List.of()));
    }
    @Test
    void differentCourseCompleted_notEnough_throws() {
        Course prereq = new Course();
        prereq.setId(1L);

        Course other = new Course();
        other.setId(2L);

        CoursePrerequisite cp = new CoursePrerequisite();
        cp.setPrerequisiteCourse(prereq);

        Enrollment e = new Enrollment();
        e.setCourse(other);
        e.setStatus(EnrollmentStatus.APPROVED);

        assertThrows(IllegalStateException.class,
                () -> validation.validate(new Course(), List.of(cp), List.of(e)));
    }

}
