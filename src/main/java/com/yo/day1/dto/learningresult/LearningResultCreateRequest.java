package com.yo.day1.dto.learningresult;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDate;

@Data
public class LearningResultCreateRequest {
    @NotNull
    private Long studentId;
    @NotNull
    private Long courseClassId;
    @NotNull
    private LocalDate resultMonth;
    @Min(0)
    private float score;
    private String teacherComment;
}