package com.epam.schedule.dto;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class YearsTest {
    @Test
    void testAllArgsConstructor() {
        // Arrange
        int year = 2022;
        List<Month> months = new ArrayList<>();

        // Act
        Years yearsObject = new Years(year, months);

        // Assert
        assertNotNull(yearsObject);
        assertEquals(year, yearsObject.getYear());
        assertEquals(months, yearsObject.getMonths());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Years yearsObject = new Years();

        // Assert
        assertNotNull(yearsObject);
    }

    @Test
    public void testBuilder() {
        // Given
        int expectedYear = 2022;
        List<Month> expectedMonths = Arrays.asList(
                new Month("January", Duration.ofDays(31)),
                new Month("February", Duration.ofDays(28))
                // Add more months as needed
        );

        // When
        Years years = Years.builder()
                .year(expectedYear)
                .months(expectedMonths)
                .build();

        // Then
        assertNotNull(years);
        assertEquals(expectedYear, years.getYear());
        assertEquals(expectedMonths, years.getMonths());
    }

    @Test
    void testGetterAndSetter() {
        // Arrange
        Years yearsObject = new Years();
        int year = 2023;
        List<Month> months = new ArrayList<>();

        // Act
        yearsObject.setYear(year);
        yearsObject.setMonths(months);

        // Assert
        assertEquals(year, yearsObject.getYear());
        assertEquals(months, yearsObject.getMonths());
    }
}
