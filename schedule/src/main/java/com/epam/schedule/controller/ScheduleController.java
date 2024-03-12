package com.epam.schedule.controller;

import com.epam.schedule.dto.Schedule;
import com.epam.schedule.service.ScheduleService;
import com.epam.schedule.utils.ValidModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ValidModule validModule;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ValidModule validModule) {
        this.scheduleService = scheduleService;
        this.validModule = validModule;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getScheduleByUsername(@PathVariable(name = "username") String username) {
        try {
            validModule.isValidUsername(username);
            Schedule schedule = scheduleService.getSchedule(username);
            return ResponseEntity.ok(schedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<?> getScheduleByFirstname(@PathVariable(name = "firstName") String firstName) {
        try {
            validModule.isValidUsername(firstName);
            Schedule schedule = scheduleService.findByFirstName(firstName);
            return ResponseEntity.ok(schedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<?> getScheduleByLastname(@PathVariable(name = "lastName") String lastName) {
        try {
            validModule.isValidUsername(lastName);
            Schedule schedule = scheduleService.findByLastName(lastName);
            return ResponseEntity.ok(schedule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> save(@PathVariable(name = "username") String username, @RequestBody Schedule schedule) {
        try {
            validModule.isValidUsername(username);
            scheduleService.updateSchedule(username, schedule);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
