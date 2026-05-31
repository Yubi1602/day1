package com.yo.day1.dto.payment;

import com.yo.day1.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long tuitionInvoiceId;
    private String paymentCode;
    private String invoiceCode;
    private float paidAmount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;
    private Long cashierUserId;
    private String cashierUsername;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
