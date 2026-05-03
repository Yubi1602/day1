package com.yo.day1.dto.parent;

import com.yo.day1.domain.AudittableEntity;
import com.yo.day1.domain.enums.StudentStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentResponse  {



    private String fullName;


    private String phone;


    private String email;


    private String address;


    private String relationship;



    private String gender;
}
