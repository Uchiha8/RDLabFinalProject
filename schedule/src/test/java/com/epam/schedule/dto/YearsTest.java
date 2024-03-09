package com.epam.schedule.dto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
