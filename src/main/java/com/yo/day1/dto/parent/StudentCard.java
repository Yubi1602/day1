package com.yo.day1.dto.parent;

import com.yo.day1.domain.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCard {
    private Long id;
    private String studentCode;
    private String fullName;
    private StudentStatus status;
    private float latestScore;
}