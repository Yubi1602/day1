package com.yo.day1.dto.student;


import com.yo.day1.domain.enums.Gender;
import com.yo.day1.domain.enums.StudentStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpsertRequest{
        @Size(min = 2 , max = 10)
        private String studentCode;

        @Size(min = 2)
        private String fullName;


        private LocalDate dateOfBirth;

        @NotNull
        private Gender gender = Gender.OTHER;

        @NotBlank
        private String gradeLevel;


        private String schoolName;


        @Pattern(regexp = "^(84|0[35789])+([0-9]{8})$")
        private String phone;


        private String description;


        private Long parentId;


        private StudentStatus status = StudentStatus.ACTIVE;

        @Min(value = 0)
        @Max(value = 10)
        private float latestScore = 0f;


        private String note;

}
