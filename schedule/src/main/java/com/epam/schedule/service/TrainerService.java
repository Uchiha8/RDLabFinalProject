package com.epam.schedule.service;

import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.Month;
import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.dto.Years;
import com.epam.schedule.repository.TrainerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final Queue queue;
    private final JmsTemplate jmsTemplate;

    private static final Logger logger = LogManager.getLogger(TrainerService.class);

    @Autowired
    public TrainerService(TrainerRepository trainerRepository, Queue queue, JmsTemplate jmsTemplate) {
        this.trainerRepository = trainerRepository;
        this.queue = queue;
        this.jmsTemplate = jmsTemplate;
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
        logger.info("Trainer with username " + request.username() + " saved");
    }

    public Schedule getSchedule(String username) {
        List<Trainer> trainer;
        try {
            trainer = trainerRepository.findAllByUsername(username);
        } catch (Exception e) {
            logger.error("Trainer with username " + username + " not found");
            throw new RuntimeException("Trainer with username " + username + " not found");
        }
        List<Years> years = new ArrayList<>();
        for (Trainer t : trainer) {
            int year = t.getDateTime().getYear() + 1900;
            if (years.stream().noneMatch(y -> y.getYear() == year)) {
                years.add(Years.builder()
                        .year(year)
                        .months(new ArrayList<>())
                        .build());

            }
        }
        for (Trainer t : trainer) {
            for (Years y : years) {
                if (t.getDateTime().getYear() + 1900 == y.getYear()) {
                    int month = t.getDateTime().getMonth() + 1;
                    if (y.getMonths().stream().noneMatch(m -> Objects.equals(m.getMonth(), monthReporter(month)))) {
                        y.getMonths().add(Month.builder()
                                .month(monthReporter(month))
                                .summaryDuration(t.getDuration())
                                .build());
                    } else {
                        for (Month m : y.getMonths()) {
                            if (m.getMonth().equals(monthReporter(month))) {
                                m.setSummaryDuration(m.getSummaryDuration().plus(t.getDuration()));
                            }
                        }
                    }
                }
            }
        }
        logger.info("Schedule for trainer with username " + username + " returned");
        return Schedule.builder()
                .username(trainer.get(0).getUsername())
                .firstName(trainer.get(0).getFirstName())
                .lastName(trainer.get(0).getLastName())
                .status(trainer.get(0).getIsActive())
                .years(years)
                .build();
    }

    public String monthReporter(int n) {
        return switch (n) {
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

    public void saveAll(List<TrainerClientDTO> request) {
        for (TrainerClientDTO trainerClientDTO : request) {
            save(trainerClientDTO);
        }
        logger.info("All trainers saved");
    }

    @JmsListener(destination = "finaldemo", id = "1")
    public void consumeMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            TrainerClientDTO trainerClientDTO = mapper.readValue(message, TrainerClientDTO.class);
            save(trainerClientDTO);
            logger.info("Trainer saved");
        } catch (Exception e) {
            logger.error("Error while saving trainer");
        }
    }

    @JmsListener(destination = "finaldemo", id = "2")
    public void consumerUsername(String message) {
        logger.info("Received message: " + message);
        try {
            Schedule schedule = getSchedule(message);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json;
            json = mapper.writeValueAsString(schedule);
            jmsTemplate.convertAndSend(queue, json);
        } catch (Exception e) {
            logger.error("Error while converting schedule to json");
        }
    }
}

