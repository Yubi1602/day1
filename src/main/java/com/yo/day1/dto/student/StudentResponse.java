package com.yo.day1.dto.student;

import com.yo.day1.domain.entity.Parent;
import com.yo.day1.domain.enums.Gender;
import com.yo.day1.domain.enums.StudentStatus;
import com.yo.day1.dto.parent.ParentResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse{

    private String studentCode;


    private String fullName;


    private LocalDate dateOfBirth;


    private Gender gender = Gender.OTHER;


    private String gradeLevel;


    private String schoolName;


    private String phone;


    private String description;


    private Parent parentId;

    private ParentResponse parent;


    private StudentStatus status = StudentStatus.ACTIVE;


    private BigDecimal latestScore = BigDecimal.ZERO;


    private String note;



}
