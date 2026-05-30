package com.yo.day1.dto.payment;

import com.yo.day1.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private String paymentCode;
    private Long tuitionInvoiceId;
    private String invoiceCode;
    private BigDecimal paidAmount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;
    private Long cashierUserId;
    private String cashierUsername;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
