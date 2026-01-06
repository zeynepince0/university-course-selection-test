package com.example.course.unit;

import com.example.course.entity.Course;
import com.example.course.entity.Student;
import com.example.course.validation.CreditValidation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreditValidationTest {

    CreditValidation validation = new CreditValidation();

    @Test
    void creditWithinLimit_passes() {
        Student s = new Student();
        s.setMaxCredit(10);

        Course c = new Course();
        c.setCredit(5);

        assertDoesNotThrow(() ->
                validation.validate(s, List.of(c), List.of()));
    }

    @Test
    void creditExceeded_throws() {
        Student s = new Student();
        s.setMaxCredit(5);

        Course c = new Course();
        c.setCredit(6);

        assertThrows(IllegalStateException.class,
                () -> validation.validate(s, List.of(c), List.of()));
    }
}
