package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.dto.invoice.InvoiceCreateRequest;
import com.yo.day1.dto.invoice.InvoiceResponse;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;
import com.yo.day1.services.BillingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@Tag(name = "Billing", description = "Quản lý hóa đơn và thanh toán học phí.")
@SecurityRequirement(name = "bearerAuth")
public class BillingController {

    private final BillingService billingService;

    @PostMapping("/invoices")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    @Operation(summary = "Tạo hóa đơn học phí")
    public ApiResponse<InvoiceResponse> createInvoice(@Valid @RequestBody InvoiceCreateRequest request) throws NotFoundException {
        return ApiResponse.success("Tạo hóa đơn thành công", billingService.createInvoice(request));
    }

    @GetMapping("/students/{studentId}/invoices")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER','PARENT')")
    @Operation(summary = "Lấy danh sách hóa đơn theo học sinh")
    public ApiResponse<List<InvoiceResponse>> findInvoicesByStudent(
            @Parameter(description = "ID học sinh", example = "1") @PathVariable Long studentId,
            @Parameter(hidden = true) Principal principal) throws BadRequestException, NotFoundException {
        return ApiResponse.success(billingService.findInvoicesByStudent(studentId, principal.getName()));
    }

    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    @Operation(summary = "Tạo thanh toán cho hóa đơn")
    public ApiResponse<PaymentResponse> addPayment(
            @Valid @RequestBody PaymentCreateRequest request, Principal principal) {
        return ApiResponse.success("Tạo thanh toán thành công", billingService.addPayment(request, principal.getName()));
    }

    @GetMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    @Operation(summary = "Lấy thông tin thanh toán theo ID")
    public ApiResponse<PaymentResponse> getPaymentById(@PathVariable Long id) {
        return ApiResponse.success("Lấy thông tin thanh toán thành công", billingService.getPaymentById(id));
    }

    @GetMapping("/invoices/{invoiceId}/payments")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    @Operation(summary = "Lấy danh sách thanh toán theo hóa đơn")
    public ApiResponse<List<PaymentResponse>> getPaymentsByInvoice(@PathVariable Long invoiceId) {
        return ApiResponse.success("Lấy danh sách thanh toán thành công", billingService.getPaymentsByInvoice(invoiceId));
    }

    @PatchMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
    @Operation(summary = "Cập nhật thanh toán")
    public ApiResponse<PaymentResponse> updatePayment(
            @PathVariable Long id, @Valid @RequestBody PaymentUpdateRequest request) {
        return ApiResponse.success("Cập nhật thanh toán thành công", billingService.updatePayment(id, request));
    }
}
