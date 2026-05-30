package com.yo.day1.dto.promotion;

import com.yo.day1.domain.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionUpsertRequest {

    @NotBlank
    @Size(max = 30)
    private String promoCode;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private DiscountType discountType;

    @NotNull
    @Positive
    private Float discountValue;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Boolean isActive = true;

    @Size(max = 255)
    private String note;
}
