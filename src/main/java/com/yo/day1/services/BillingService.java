package com.yo.day1.services;

import com.yo.day1.dto.invoice.InvoiceCreateRequest;
import com.yo.day1.dto.invoice.InvoiceResponse;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;

import java.util.List;

public interface BillingService {
    InvoiceResponse createInvoice(InvoiceCreateRequest request);
    List<InvoiceResponse> findInvoicesByStudent(Long studentId, String username);

    PaymentResponse addPayment(PaymentCreateRequest request, String username);
    PaymentResponse getPaymentById(Long id);
    List<PaymentResponse> getPaymentsByInvoice(Long invoiceId);
    PaymentResponse updatePayment(Long id, PaymentUpdateRequest request);
}
