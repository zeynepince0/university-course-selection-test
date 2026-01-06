package com.example.course.unit;

import com.example.course.validation.ClassYearValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassYearValidationTest {

    ClassYearValidation validation = new ClassYearValidation();

    @Test
    void valid_passes() {
        assertDoesNotThrow(() -> validation.validate(3, 1));
    }

    @Test
    void invalid_throws() {
        assertThrows(IllegalStateException.class,
                () -> validation.validate(1, 3));
    }
}
