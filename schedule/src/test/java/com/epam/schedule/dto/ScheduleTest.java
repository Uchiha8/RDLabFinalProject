package com.epam.schedule.dto;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ScheduleTest {

    @Test
    public void testNoArgsConstructor() {
        // When
        Schedule schedule = new Schedule();

        // Then
        assertNotNull(schedule);
        assertNull(schedule.getId());
        assertNull(schedule.getUsername());
        assertNull(schedule.getFirstName());
        assertNull(schedule.getLastName());
        assertNull(schedule.getStatus());
        assertNull(schedule.getYears());
    }

    @Test
    void testRecordEquality() {
        // Arrange
        List<Month> months = new ArrayList<>();
        months.add(new Month("January", Duration.ofHours(50)));
        List<Years> years1 = new ArrayList<>();
        years1.add(new Years(2022, months));

        List<Years> years2 = new ArrayList<>();
        years2.add(new Years(2022, months));

        // Act & Assert
        Schedule schedule1 = Schedule.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .years(years2)
                .build();

        Schedule schedule2 = Schedule.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .status(true)
                .years(years2)
                .build();

        Schedule schedule3 = Schedule.builder()
                .username("jane_doe")
                .firstName("Jane")
                .lastName("Doe")
                .status(false)
                .years(years1)
                .build();

        assertEquals(schedule1, schedule1);
        assertNotEquals(schedule1, schedule3);
    }

    @Test
    public void testGetterAndSetter() {
        // Given
        Schedule schedule = new Schedule();
        String expectedUsername = "john.doe";
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        Boolean expectedStatus = true;
        List<Years> expectedYears = Arrays.asList(new Years(2022, List.of()), new Years(2023, List.of()));

        // When
        schedule.setUsername(expectedUsername);
        schedule.setFirstName(expectedFirstName);
        schedule.setLastName(expectedLastName);
        schedule.setStatus(expectedStatus);
        schedule.setYears(expectedYears);

        // Then
        assertNotNull(schedule);
        assertEquals(expectedUsername, schedule.getUsername());
        assertEquals(expectedFirstName, schedule.getFirstName());
        assertEquals(expectedLastName, schedule.getLastName());
        assertEquals(expectedStatus, schedule.getStatus());
        assertEquals(expectedYears, schedule.getYears());
    }

}
