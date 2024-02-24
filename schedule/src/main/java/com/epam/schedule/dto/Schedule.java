package com.epam.schedule.dto;

import jakarta.persistence.Column;
import lombok.Builder;

import java.util.List;
@Builder
public record Schedule(
        @Column(nullable = false)
        String username,
        @Column(nullable = false)
        String firstName,
        @Column(nullable = false)
        String lastName,
        Boolean status,
        List<Years> years) {
}
