package com.yo.day1.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponse {
    Long id;
    Long courseClassId;
    String className;
    Long studentId;
    String studentName;
    LocalDate attendanceDate;
    String status;
    String note;
    Long recordedByUserId;
    String recordedByUsername;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

