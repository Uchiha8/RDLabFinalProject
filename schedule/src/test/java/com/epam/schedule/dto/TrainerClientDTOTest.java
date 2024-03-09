package com.epam.schedule.dto;

import com.epam.schedule.domain.ActionType;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TrainerClientDTOTest {
    @Test
    void testRecordEquality() {
        // Arrange
        Date date = new Date();
        TrainerClientDTO trainerClientDTO1 = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(date)
                .duration(Duration.ofHours(1))
                .actionType(ActionType.CREATE)
                .build();

        TrainerClientDTO trainerClientDTO2 = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(date)
                .duration(Duration.ofHours(1))
                .actionType(ActionType.CREATE)
                .build();

        TrainerClientDTO trainerClientDTO3 = TrainerClientDTO.builder()
                .username("jane_doe")
                .firstName("Jane")
                .lastName("Doe")
                .isActive(false)
                .dateTime(date)
                .duration(Duration.ofHours(2))
                .actionType(ActionType.DELETE)
                .build();

        // Act & Assert
        assertEquals(trainerClientDTO1, trainerClientDTO2);
        assertNotEquals(trainerClientDTO1, trainerClientDTO3);
    }

    @Test
    void testToStringMethod() {
        // Arrange
        TrainerClientDTO trainerClientDTO = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .actionType(ActionType.CREATE)
                .build();

        // Act
        String toStringResult = trainerClientDTO.toString();

        // Assert
        String expectedToString = "TrainerClientDTO[username=john_doe, firstName=John, lastName=Doe, " +
                "isActive=true, dateTime=" + trainerClientDTO.dateTime() + ", duration=PT1H, actionType=CREATE]";
        assertEquals(expectedToString, toStringResult);
    }
}
