package com.example.course.unit;

import com.example.course.validation.QuotaValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuotaValidationTest {

    QuotaValidation validation = new QuotaValidation();

    @Test
    void quotaAvailable_passes() {
        assertDoesNotThrow(() -> validation.validate(5, 10));
    }

    @Test
    void quotaFull_throws() {
        assertThrows(IllegalStateException.class,
                () -> validation.validate(10, 10));
    }
}




