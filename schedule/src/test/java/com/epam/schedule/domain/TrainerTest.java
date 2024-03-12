package com.epam.schedule.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrainerTest {

    @Test
    void testNoArgsConstructor() {
        // Act
        Trainer trainer = new Trainer();

        // Assert
        assertNotNull(trainer);
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        Boolean isActive = true;
        Date dateTime = new Date();
        Duration duration = Duration.ofHours(1);
        ActionType actionType = ActionType.CREATE;

        // Act
        Trainer trainer = new Trainer(1L, username, firstName, lastName, true, new Date(), Duration.ofHours(1), ActionType.CREATE);

        // Assert
        assertNotNull(trainer);
        assertEquals(id, trainer.getId());
        assertEquals(username, trainer.getUsername());
        assertEquals(firstName, trainer.getFirstName());
        assertEquals(lastName, trainer.getLastName());
        assertEquals(isActive, trainer.getIsActive());
        assertEquals(dateTime, trainer.getDateTime());
        assertEquals(duration, trainer.getDuration());
        assertEquals(actionType, trainer.getActionType());
    }

    @Test
    void testBuilder() {
        // Arrange
        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        Boolean isActive = true;
        Date dateTime = new Date();
        Duration duration = Duration.ofHours(1);
        ActionType actionType = ActionType.CREATE;

        // Act
        Trainer trainer = Trainer.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .dateTime(dateTime)
                .duration(duration)
                .actionType(actionType)
                .build();

        // Assert
        assertNotNull(trainer);
        assertEquals(id, trainer.getId());
        assertEquals(username, trainer.getUsername());
        assertEquals(firstName, trainer.getFirstName());
        assertEquals(lastName, trainer.getLastName());
        assertEquals(isActive, trainer.getIsActive());
        assertEquals(dateTime, trainer.getDateTime());
        assertEquals(duration, trainer.getDuration());
        assertEquals(actionType, trainer.getActionType());
    }

    @Test
    void testGettersAndSetters() {
        // Arrange
        Trainer trainer = new Trainer();

        Long id = 1L;
        String username = "john_doe";
        String firstName = "John";
        String lastName = "Doe";
        Boolean isActive = true;
        Date dateTime = new Date();
        Duration duration = Duration.ofHours(1);
        ActionType actionType = ActionType.CREATE;

        // Act
        trainer.setId(id);
        trainer.setUsername(username);
        trainer.setFirstName(firstName);
        trainer.setLastName(lastName);
        trainer.setIsActive(isActive);
        trainer.setDateTime(dateTime);
        trainer.setDuration(duration);
        trainer.setActionType(actionType);

        // Assert
        assertEquals(id, trainer.getId());
        assertEquals(username, trainer.getUsername());
        assertEquals(firstName, trainer.getFirstName());
        assertEquals(lastName, trainer.getLastName());
        assertEquals(isActive, trainer.getIsActive());
        assertEquals(dateTime, trainer.getDateTime());
        assertEquals(duration, trainer.getDuration());
        assertEquals(actionType, trainer.getActionType());
    }
}
