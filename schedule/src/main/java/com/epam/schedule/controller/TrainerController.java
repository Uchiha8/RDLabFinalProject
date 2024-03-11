package com.epam.schedule.controller;

import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.service.ScheduleService;
import com.epam.schedule.service.TrainerService;
import com.epam.schedule.utils.ValidModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
