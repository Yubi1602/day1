package com.yo.day1.dto.payment;

import com.yo.day1.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PaymentCreateRequest {

    @NotNull
    private Long tuitionInvoiceId;

    @NotBlank
    @Size(max = 30)
    private String paymentCode;

    @NotNull
    @Positive
    private BigDecimal paidAmount;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private LocalDateTime paidAt;

    private Long cashierUserId;

    @Size(max = 255)
    private String note;
}
