package com.yo.day1.dto.courseClass;

import com.yo.day1.domain.enums.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseClassResponse {
    private Long id;
    private String classCode;
    private String name;
    private Long courseId;
    private String courseName;
    private Long roomId;
    private String roomName;
    private Long scheduleSlotId;
    private String slotCode;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long mainTeacherId;
    private String mainTeacherName;
    private Long assistantTeacherId;
    private String assistantTeacherName;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxStudents;
    private double tuitionFee;
    private ClassStatus status;
}

