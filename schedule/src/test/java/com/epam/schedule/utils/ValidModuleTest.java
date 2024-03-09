package com.epam.schedule.utils;
import com.epam.schedule.dto.TrainerClientDTO;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidModuleTest {
    @Test
    void testIsValid_AllFieldsValid() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingFirstName() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingLastName() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .firstName("John")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingUsername() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingStatus() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .dateTime(new Date())
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingDuration() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(new Date())
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValid_MissingDateTime() {
        // Arrange
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .duration(Duration.ofHours(1))
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValid(request));
    }

    @Test
    void testIsValidUsername_ValidUsername() {
        // Arrange
        String username = "john_doe";

        // Act & Assert
        assertDoesNotThrow(() -> new ValidModule().isValidUsername(username));
    }

    @Test
    void testIsValidUsername_MissingUsername() {
        // Arrange
        String username = null;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> new ValidModule().isValidUsername(username));
    }
}
