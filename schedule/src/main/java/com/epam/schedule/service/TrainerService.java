package com.epam.schedule.service;

import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.Month;
import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.dto.Years;
import com.epam.schedule.repository.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final ScheduleService scheduleService;
    private static final Logger logger = LogManager.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, ScheduleService scheduleService) {
        this.trainerRepository = trainerRepository;
        this.scheduleService = scheduleService;
    }

    public void save(TrainerClientDTO request) {
        Trainer trainer = Trainer.builder()
                .username(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .isActive(request.isActive())
                .dateTime(request.dateTime())
                .duration(request.duration())
                .actionType(request.actionType())
                .build();
        trainerRepository.save(trainer);
        scheduleService.save(request.username());
        logger.info("Trainer with username " + request.username() + " saved");
    }

    @JmsListener(destination = "finaldemo", id = "1")
    public void consumeMessage(String message) {
        try {
            if (message.length() < 15) {
                scheduleService.consumerUsername(message);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                TrainerClientDTO trainerClientDTO = mapper.readValue(message, TrainerClientDTO.class);
                save(trainerClientDTO);
                logger.info("Trainer saved");
            }
        } catch (Exception e) {
            logger.error("Error while saving trainer");
        }
    }


}

