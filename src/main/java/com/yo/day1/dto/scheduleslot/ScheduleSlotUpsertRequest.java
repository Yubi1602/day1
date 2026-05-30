package com.yo.day1.dto.scheduleslot;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSlotUpsertRequest {

    @NotBlank
    @Size(max = 20)
    private String slotCode;

    @NotNull
    private Byte weekday;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @Size(max = 255)
    private String note;
}
