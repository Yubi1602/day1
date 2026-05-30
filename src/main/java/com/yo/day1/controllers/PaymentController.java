package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;
import com.yo.day1.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    public ApiResponse<PaymentResponse> create(@Valid @RequestBody PaymentCreateRequest request, Principal principal) {
        return ApiResponse.success("Tạo thanh toán thành công", paymentService.create(request, principal.getName()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    public ApiResponse<PaymentResponse> getById(@PathVariable Long id) {
        return ApiResponse.success("Lấy thông tin thanh toán thành công", paymentService.getById(id));
    }

    @GetMapping("/invoice/{invoiceId}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    public ApiResponse<List<PaymentResponse>> findByInvoiceId(@PathVariable Long invoiceId) {
        return ApiResponse.success("Lấy danh sách thanh toán của hóa đơn thành công", paymentService.findByInvoiceId(invoiceId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
    public ApiResponse<PaymentResponse> update(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequest request) {
        return ApiResponse.success("Cập nhật thanh toán thành công", paymentService.update(id, request));
    }
}
