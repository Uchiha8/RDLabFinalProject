package com.epam.schedule.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@CompoundIndex(def = "{'firstName': 1, 'lastName': 1}")
public class Schedule {
    @Id
    private String id;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    Boolean status;
    List<Years> years;
}
