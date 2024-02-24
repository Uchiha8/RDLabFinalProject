package com.epam.schedule.controller;

import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.service.TrainerService;
import com.epam.schedule.utils.ValidModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/trainer")
public class TrainerController {
    private final TrainerService trainerService;
    private final ValidModule validModule;

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
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
