package com.epam.schedule.service;

import com.epam.schedule.domain.ActionType;
import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.repository.TrainerRepository;
import jakarta.jms.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        // Given
        Date date = new Date();
        TrainerClientDTO request = TrainerClientDTO.builder()
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .isActive(true)
                .dateTime(date)
                .duration(Duration.ofHours(1))
                .build();
        Trainer trainer = Trainer.builder()
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .isActive(request.isActive())
                .dateTime(request.dateTime())
                .duration(request.duration())
                .build();
        // When
        trainerService.save(request);
        // Then
        verify(trainerRepository, times(0)).save(trainer);
    }

    @Test
    void testMonthReporter_ValidInput() {
        // Act & Assert
        assertEquals("JANUARY", trainerService.monthReporter(1));
        assertEquals("FEBRUARY", trainerService.monthReporter(2));
        assertEquals("MARCH", trainerService.monthReporter(3));
        assertEquals("APRIL", trainerService.monthReporter(4));
        assertEquals("MAY", trainerService.monthReporter(5));
        assertEquals("JUNE", trainerService.monthReporter(6));
        assertEquals("JULY", trainerService.monthReporter(7));
        assertEquals("AUGUST", trainerService.monthReporter(8));
        assertEquals("SEPTEMBER", trainerService.monthReporter(9));
        assertEquals("OCTOBER", trainerService.monthReporter(10));
        assertEquals("NOVEMBER", trainerService.monthReporter(11));
        assertEquals("DECEMBER", trainerService.monthReporter(12));
    }

    @Test
    void testMonthReporter_InvalidInput() {
        // Act & Assert
        assertThrows(RuntimeException.class, () -> trainerService.monthReporter(13));
        assertThrows(RuntimeException.class, () -> trainerService.monthReporter(0));
    }

    @Test
    void testSaveAll() {
        // Given
        List<TrainerClientDTO> trainerDTOList = Collections.singletonList(
                TrainerClientDTO.builder()
                        .username("john_doe")
                        .firstName("John")
                        .lastName("Doe")
                        .isActive(true)
                        .dateTime(new Date())
                        .duration(Duration.ofHours(1))
                        .actionType(ActionType.CREATE)
                        .build());
        trainerService.saveAll(trainerDTOList);
        // Then
        verify(trainerRepository, times(0)).save(Mockito.any(Trainer.class));
    }
}
