package com.epam.schedule.service;

import com.epam.schedule.domain.ActionType;
import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.Years;
import com.epam.schedule.repository.ScheduleRepository;
import com.epam.schedule.repository.TrainerRepository;
import jakarta.jms.Queue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Queue queue;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    public void testSave() {
        // Given
        String username = "testUser";
        List<Trainer> trainers = List.of(new Trainer(1L, username, "John", "Doe", true, new Date(), Duration.ofHours(2), ActionType.CREATE));
        Schedule schedule = new Schedule("id", username, "John", "Doe", true, List.of(new Years(2022, List.of())));
        // Mock behavior for dependencies
        when(trainerRepository.findAllByUsername(username)).thenReturn(trainers);
        when(scheduleRepository.findByUsername(username)).thenReturn(schedule);

        // When
        scheduleService.save(username);

        // Then
        verify(trainerRepository, times(1)).findAllByUsername(username);
        verify(scheduleRepository, times(1)).findByUsername(username);
        verify(scheduleRepository, times(1)).insert(any(Schedule.class));
        // Add more assertions as needed
    }

    @Test
    public void testGetSchedule() {
        // Given
        String username = "testUser";
        Schedule expectedSchedule = new Schedule();
        expectedSchedule.setUsername(username);

        // Mock behavior for dependencies
        when(scheduleRepository.findByUsername(username)).thenReturn(expectedSchedule);

        // When
        Schedule actualSchedule = scheduleService.getSchedule(username);

        // Then
        verify(scheduleRepository, times(1)).findByUsername(username);
        assertEquals(expectedSchedule, actualSchedule);
    }

    @Test
    public void testGetScheduleNotFound() {
        // Given
        String username = "nonExistentUser";
        // Mock behavior for dependencies
        when(scheduleRepository.findByUsername(username)).thenReturn(null);
        // When / Then
        assertThrows(RuntimeException.class, () -> scheduleService.getSchedule(username));
        verify(scheduleRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testFindTrainersByUsername() {
        // Given
        String username = "testUser";
        List<Trainer> expectedTrainers = List.of(new Trainer(1L, username, "John", "Doe", true, new Date(), Duration.ofHours(2), ActionType.CREATE));
        when(trainerRepository.findAllByUsername(username)).thenReturn(expectedTrainers);

        // When
        List<Trainer> result = scheduleService.findTrainersByUsername(username);

        // Then
        assertNotNull(result);
        assertSame(expectedTrainers, result);
        verify(trainerRepository, times(1)).findAllByUsername(username);
    }

    @Test
    public void testFindTrainersByUsernameNotFound() {
        // Given
        String username = "nonExistentUser";
        when(trainerRepository.findAllByUsername(username)).thenThrow(new RuntimeException("Not found"));
        // When and Then
        assertThrows(RuntimeException.class, () -> scheduleService.findTrainersByUsername(username));
    }

    @Test
    public void testBuildSchedule() {
        // Given
        Trainer trainer = new Trainer(1L, "testUser", "John", "Doe", true, new Date(), Duration.ofHours(2), ActionType.CREATE);
        List<Years> years = Arrays.asList(new Years(2022, List.of()), new Years(2023, List.of()));
        // When
        Schedule result = scheduleService.buildSchedule(trainer, years);
        // Then
        assertEquals(trainer.getUsername(), result.getUsername());
        assertEquals(trainer.getFirstName(), result.getFirstName());
        assertEquals(trainer.getLastName(), result.getLastName());
        assertEquals(trainer.getIsActive(), result.getStatus());
        assertEquals(years, result.getYears());
    }

    @Test
    public void testValidMonths() {
        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            // Given
            // When
            String result = scheduleService.monthReporter(monthNumber);
            // Then
            assertEquals(getExpectedMonthName(monthNumber), result);
        }
    }

    @Test
    public void testInvalidMonth() {
        // Given
        int invalidMonthNumber = 13;
        // When and Then
        assertThrows(RuntimeException.class, () -> scheduleService.monthReporter(invalidMonthNumber));
    }

    private String getExpectedMonthName(int monthNumber) {
        return switch (monthNumber) {
            case 1 -> "JANUARY";
            case 2 -> "FEBRUARY";
            case 3 -> "MARCH";
            case 4 -> "APRIL";
            case 5 -> "MAY";
            case 6 -> "JUNE";
            case 7 -> "JULY";
            case 8 -> "AUGUST";
            case 9 -> "SEPTEMBER";
            case 10 -> "OCTOBER";
            case 11 -> "NOVEMBER";
            case 12 -> "DECEMBER";
            default -> throw new RuntimeException("Invalid month number");
        };
    }

    @Test
    public void testFindByFirstNameFound() {
        // Given
        String firstName = "John";
        Schedule expectedSchedule = new Schedule();
        when(scheduleRepository.findByFirstName(firstName)).thenReturn(expectedSchedule);

        // When
        Schedule result = scheduleService.findByFirstName(firstName);

        // Then
        assertNotNull(result);
        assertSame(expectedSchedule, result);
        verify(scheduleRepository, times(1)).findByFirstName(firstName);
    }

    @Test
    public void testFindByFirstNameNotFound() {
        // Given
        String nonExistentFirstName = "NonExistent";
        when(scheduleRepository.findByFirstName(nonExistentFirstName)).thenReturn(null);

        // When and Then
        assertThrows(RuntimeException.class, () -> scheduleService.findByFirstName(nonExistentFirstName));
        verify(scheduleRepository, times(1)).findByFirstName(nonExistentFirstName);
    }

    @Test
    public void testFindByLastNameFound() {
        // Given
        String lastName = "Doe";
        Schedule expectedSchedule = new Schedule();
        when(scheduleRepository.findByLastName(lastName)).thenReturn(expectedSchedule);

        // When
        Schedule result = scheduleService.findByLastName(lastName);

        // Then
        assertNotNull(result);
        assertSame(expectedSchedule, result);
        verify(scheduleRepository, times(1)).findByLastName(lastName);
    }

    @Test
    public void testFindByLastNameNotFound() {
        // Given
        String nonExistentLastName = "NonExistent";
        when(scheduleRepository.findByLastName(nonExistentLastName)).thenReturn(null);

        // When and Then
        assertThrows(RuntimeException.class, () -> scheduleService.findByLastName(nonExistentLastName));
        verify(scheduleRepository, times(1)).findByLastName(nonExistentLastName);
    }

    @Test
    public void testUpdateSchedule() {
        // Given
        String username = "testUser";
        Schedule existingSchedule = new Schedule();
        existingSchedule.setUsername(username);
        existingSchedule.setFirstName("John");
        existingSchedule.setLastName("Doe");
        existingSchedule.setStatus(true);

        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setUsername(username);
        updatedSchedule.setFirstName("Jane");
        updatedSchedule.setLastName("Smith");
        updatedSchedule.setStatus(false);

        when(scheduleRepository.findByUsername(username)).thenReturn(existingSchedule);

        // When
        scheduleService.updateSchedule(username, updatedSchedule);

        // Then
        verify(scheduleRepository, times(1)).findByUsername(username);
        verify(scheduleRepository, times(1)).save(existingSchedule);
        assertEquals(updatedSchedule.getFirstName(), existingSchedule.getFirstName());
        assertEquals(updatedSchedule.getLastName(), existingSchedule.getLastName());
        assertEquals(updatedSchedule.getStatus(), existingSchedule.getStatus());
    }

    @Test
    public void testUpdateScheduleNotFound() {
        // Given
        String nonExistentUsername = "nonExistentUser";
        Schedule updatedSchedule = new Schedule();

        when(scheduleRepository.findByUsername(nonExistentUsername)).thenReturn(null);

        // When and Then
        assertThrows(RuntimeException.class, () -> scheduleService.updateSchedule(nonExistentUsername, updatedSchedule));
        verify(scheduleRepository, times(1)).findByUsername(nonExistentUsername);
        verify(scheduleRepository, never()).save(any());
    }
}

