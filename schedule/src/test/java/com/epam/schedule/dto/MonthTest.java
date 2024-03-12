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
    public void testBuilder() {
        // Given
        String expectedMonth = "January";
        Duration expectedSummaryDuration = Duration.ofDays(30);

        // When
        Month month = Month.builder()
                .month(expectedMonth)
                .summaryDuration(expectedSummaryDuration)
                .build();

        // Then
        assertNotNull(month);
        assertEquals(expectedMonth, month.getMonth());
        assertEquals(expectedSummaryDuration, month.getSummaryDuration());
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
