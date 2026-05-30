package com.yo.day1.services.impl;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Payment;
import com.yo.day1.domain.entity.TuitionInvoice;
import com.yo.day1.domain.entity.User;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;
import com.yo.day1.repository.PaymentRepository;
import com.yo.day1.repository.TuitionInvoiceRepository;
import com.yo.day1.repository.UserRepository;
import com.yo.day1.services.AuthService;
import com.yo.day1.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TuitionInvoiceRepository tuitionInvoiceRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public PaymentResponse create(PaymentCreateRequest req, String username) throws BadRequestException, NotFoundException {
        TuitionInvoice invoice = tuitionInvoiceRepository.findById(req.getTuitionInvoiceId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn với id: " + req.getTuitionInvoiceId()));

        User cashier = null;
        if (req.getCashierUserId() != null) {
            cashier = userRepository.findById(req.getCashierUserId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên thu ngân với id: " + req.getCashierUserId()));
        } else {
            cashier = authService.findActiveUserByUsername(username);
        }

        Payment payment = new Payment();
        payment.setTuitionInvoice(invoice);
        payment.setPaymentCode(req.getPaymentCode());
        payment.setPaidAmount(req.getPaidAmount());
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setPaidAt(req.getPaidAt());
        payment.setCashierUser(cashier);
        payment.setNote(req.getNote());

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getById(Long id) throws NotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thanh toán với id: " + id));
        return toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> findByInvoiceId(Long invoiceId) {
        return paymentRepository.findByTuitionInvoiceId(invoiceId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public PaymentResponse update(Long id, PaymentUpdateRequest req) throws BadRequestException, NotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thanh toán với id: " + id));

        if (req.getPaidAmount() != null) {
            payment.setPaidAmount(req.getPaidAmount());
        }
        if (req.getPaymentMethod() != null) {
            payment.setPaymentMethod(req.getPaymentMethod());
        }
        if (req.getPaidAt() != null) {
            payment.setPaidAt(req.getPaidAt());
        }
        if (req.getCashierUserId() != null) {
            User cashier = userRepository.findById(req.getCashierUserId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên thu ngân với id: " + req.getCashierUserId()));
            payment.setCashierUser(cashier);
        }
        if (req.getNote() != null) {
            payment.setNote(req.getNote());
        }

        Payment updated = paymentRepository.save(payment);
        return toResponse(updated);
    }

    private PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = mapper.map(payment, PaymentResponse.class);
        if (payment.getTuitionInvoice() != null) {
            response.setTuitionInvoiceId(payment.getTuitionInvoice().getId());
            response.setInvoiceCode(payment.getTuitionInvoice().getInvoiceCode());
        }
        if (payment.getCashierUser() != null) {
            response.setCashierUserId(payment.getCashierUser().getId());
            response.setCashierUsername(payment.getCashierUser().getUsername());
        }
        return response;
    }
}
