package com.yo.day1.dto.teacher;

import com.yo.day1.domain.enums.TeacherRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUpsertRequest {

    @NotBlank
    @Size(min = 2, max = 20)
    private String teacherCode;

    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^(84|0[35789])+([0-9]{8})$")
    private String phone;

    private String email;

    @NotNull
    private TeacherRole teacherRole = TeacherRole.TEACHER;

    private String cccdImageUrl;

    private boolean isActive = true;
}
