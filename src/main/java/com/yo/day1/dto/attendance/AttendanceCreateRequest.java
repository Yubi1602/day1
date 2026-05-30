package com.yo.day1.dto.attendance;

import com.yo.day1.domain.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceCreateRequest {
    @NotNull
    private Long courseClassId;

    @NotNull
    private Long studentId;

    @NotNull
    private LocalDate attendanceDate;

    @NotNull
    private AttendanceStatus status;

    @Size(max = 255)
    private String note;
}

