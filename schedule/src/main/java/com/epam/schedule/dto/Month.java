package com.epam.schedule.dto;


import lombok.*;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Month {
    private String month;
    private Duration summaryDuration;
}
