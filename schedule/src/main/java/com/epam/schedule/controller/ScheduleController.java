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

    @GetMapping("/{username}")
    public ResponseEntity<?> getSchedule(@PathVariable(name = "username") String username) {
        try {
            validModule.isValidUsername(username);
            Schedule schedule = scheduleService.getSchedule(username);
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
