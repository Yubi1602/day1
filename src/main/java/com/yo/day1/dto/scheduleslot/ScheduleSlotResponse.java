package com.yo.day1.dto.scheduleslot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSlotResponse {
    private Long id;
   // private String slotCode;
    private byte weekday;
    private String weekdayLabel;
    private LocalTime startTime;
    private LocalTime endTime;
    private String note;
}
