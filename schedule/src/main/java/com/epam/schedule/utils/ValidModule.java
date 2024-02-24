package com.epam.schedule.utils;

import com.epam.schedule.dto.TrainerClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ValidModule {
    public void isValid(TrainerClientDTO request) {
        if (request.firstName() == null) {
            throw new RuntimeException("First name is required");
        } else if (request.lastName() == null) {
            throw new RuntimeException("Last name is required");
        } else if (request.username() == null) {
            throw new RuntimeException("Username is required");
        } else if (request.isActive() == null) {
            throw new RuntimeException("Status is required");
        } else if (request.duration() == null) {
            throw new RuntimeException("Duration is required");
        } else if (request.dateTime() == null) {
            throw new RuntimeException("Date and time is required");
        }
    }

    public void isValidUsername(String username) {
        if (username == null) {
            throw new RuntimeException("Username is required");
        }
    }
}
