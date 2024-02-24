package com.epam.schedule.service;

import com.epam.schedule.domain.Trainer;
import com.epam.schedule.dto.Schedule;
import com.epam.schedule.dto.TrainerClientDTO;
import com.epam.schedule.dto.Years;
import com.epam.schedule.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
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
    }

    public Schedule getSchedule(String username) {
        List<Trainer> trainer = trainerRepository.findAllByUsername(username);
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
        return Schedule.builder()
                .username(trainer.get(0).getUsername())
                .firstName(trainer.get(0).getFirstName())
                .lastName(trainer.get(0).getLastName())
                .status(trainer.get(0).getIsActive())
                .years(years)
                .build();
    }

    public void saveAll(List<TrainerClientDTO> request) {
        for (TrainerClientDTO trainerClientDTO : request) {
            save(trainerClientDTO);
        }
    }
}

