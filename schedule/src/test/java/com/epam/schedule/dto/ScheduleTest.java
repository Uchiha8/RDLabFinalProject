package com.epam.schedule.dto;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
public class ScheduleTest {
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

        assertEquals(schedule1, schedule2);
        assertNotEquals(schedule1, schedule3);
    }
}
