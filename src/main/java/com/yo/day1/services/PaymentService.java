package com.yo.day1.services;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;

import java.util.List;

public interface PaymentService {
    PaymentResponse create(PaymentCreateRequest req, String username) throws BadRequestException, NotFoundException;
    PaymentResponse getById(Long id) throws NotFoundException;
    List<PaymentResponse> findByInvoiceId(Long invoiceId);
    PaymentResponse update(Long id, PaymentUpdateRequest req) throws BadRequestException, NotFoundException;
}
