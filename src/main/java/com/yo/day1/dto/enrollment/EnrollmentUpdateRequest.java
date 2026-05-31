package com.yo.day1.dto.enrollment;

import com.yo.day1.domain.enums.EnrollmentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentUpdateRequest {
    @NotNull
    private EnrollmentStatus status;

    @Size(max = 255)
    private String note;
}
