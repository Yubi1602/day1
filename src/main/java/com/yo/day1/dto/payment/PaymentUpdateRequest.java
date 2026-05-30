package com.yo.day1.dto.payment;

import com.yo.day1.domain.enums.PaymentMethod;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentUpdateRequest {

    @Positive
    private BigDecimal paidAmount;

    private PaymentMethod paymentMethod;

    private LocalDateTime paidAt;

    private Long cashierUserId;

    @Size(max = 255)
    private String note;
}
