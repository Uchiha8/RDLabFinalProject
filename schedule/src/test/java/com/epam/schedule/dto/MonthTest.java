package com.epam.schedule.dto;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MonthTest {
    @Test
    void testAllArgsConstructor() {
        // Arrange
        String month = "January";
        Duration summaryDuration = Duration.ofHours(50);

        // Act
        Month monthObject = new Month(month, summaryDuration);

        // Assert
        assertNotNull(monthObject);
        assertEquals(month, monthObject.getMonth());
        assertEquals(summaryDuration, monthObject.getSummaryDuration());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Month monthObject = new Month();

        // Assert
        assertNotNull(monthObject);
    }

    @Test
    void testGetterAndSetter() {
        // Arrange
        Month monthObject = new Month();
        String month = "February";
        Duration summaryDuration = Duration.ofHours(60);

        // Act
        monthObject.setMonth(month);
        monthObject.setSummaryDuration(summaryDuration);

        // Assert
        assertEquals(month, monthObject.getMonth());
        assertEquals(summaryDuration, monthObject.getSummaryDuration());
    }
}
