package com.epam.schedule.controller;

import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.service.TrainerService;
import com.epam.schedule.utils.ValidModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/trainer")
public class TrainerController {
    private final TrainerService trainerService;
    private final ValidModule validModule;
    private static Logger logger = LogManager.getLogger(TrainerController.class);

    @Autowired
    public TrainerController(TrainerService trainerService, ValidModule validModule) {
        this.trainerService = trainerService;
        this.validModule = validModule;
    }

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
            return ResponseEntity.ok(trainerService.getSchedule(username));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
