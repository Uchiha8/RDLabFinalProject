package com.epam.schedule.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Month {
    @Enumerated(EnumType.STRING)
    private EnumMonth month;
    private Duration summaryDuration;
}
