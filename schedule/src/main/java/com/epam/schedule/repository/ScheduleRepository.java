package com.epam.schedule.repository;

import com.epam.schedule.dto.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    Schedule findByUsername(String username);
    //create index for search with firstName or lastName
    Schedule findByFirstName(String firstName);
    Schedule findByLastName(String lastName);

    void deleteByUsername(String username);
}
