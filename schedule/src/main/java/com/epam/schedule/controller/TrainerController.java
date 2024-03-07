package com.epam.schedule.controller;

import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.service.TrainerService;
import com.epam.schedule.utils.ValidModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final ValidModule validModule;
    private static final Logger logger = LogManager.getLogger(TrainerController.class);

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody TrainerClientDTO request) {
        try {
            validModule.isValid(request);
            trainerService.save(request);
            logger.info("Trainer with username " + request.username() + " saved");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saveAll")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveAll(@RequestBody List<TrainerClientDTO> request) {
        try {
            trainerService.saveAll(request);
            logger.info("Trainers saved");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> getSchedule(@RequestParam String username) {
        try {
            validModule.isValidUsername(username);
            logger.info("Schedule for trainer with username " + username + " returned");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return ResponseEntity.ok(trainerService.getSchedule(username));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
