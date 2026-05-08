package com.yo.day1.dto.parent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentUpsertRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^(84|0[35789])+([0-9]{8})$")
    private String phone;

    private String email;

    private String address;

    @Size(max = 20)
    private String relationship;

    @Size(max = 10)
    private String gender;
}
