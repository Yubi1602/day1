package com.yo.day1.dto.student;


import com.yo.day1.domain.enums.Gender;
import com.yo.day1.domain.enums.StudentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;

public record StudentWithParentUpsertRequest(
        // Parent Info
        @NotBlank @Size(max = 100) String parentFullName,
        @NotBlank @Size(max = 20) String parentPhone,
        @Size(max = 100) String parentEmail,
        @Size(max = 255) String parentAddress,
        @Size(max = 20) String parentRelationship,
        @Size(max = 10) String parentGender,

        // Student Info
        @NotBlank @Size(max = 20) String studentCode,
        @NotBlank @Size(max = 100) String fullName,
        LocalDate dateOfBirth,
        @NotNull Gender gender,
        @Size(max = 30) String gradeLevel,
        @Size(max = 100) String schoolName,
        @Size(max = 20) String phone,
        @Size(max = 255) String description,
        @NotNull StudentStatus status,
        @NotNull @Min(0) float latestScore,
        @Size(max = 255) String note
) {
}
