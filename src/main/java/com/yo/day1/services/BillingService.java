package com.yo.day1.services;

import com.yo.day1.domain.entity.Promotion;
import com.yo.day1.domain.entity.TuitionInvoice;
import com.yo.day1.dto.invoice.InvoiceCreateRequest;
import com.yo.day1.dto.invoice.InvoiceResponse;

import java.util.List;

public interface BillingService {
    InvoiceResponse createInvoice(InvoiceCreateRequest request);
    List<InvoiceResponse> findInvoicesByStudent(Long studentId, String username);

}
