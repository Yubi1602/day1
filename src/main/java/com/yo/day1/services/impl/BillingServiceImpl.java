package com.yo.day1.services.impl;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Payment;
import com.yo.day1.domain.entity.Promotion;
import com.yo.day1.domain.entity.TuitionInvoice;
import com.yo.day1.domain.entity.User;
import com.yo.day1.domain.enums.DiscountType;
import com.yo.day1.domain.enums.InvoiceStatus;
import com.yo.day1.dto.invoice.InvoiceCreateRequest;
import com.yo.day1.dto.invoice.InvoiceResponse;
import com.yo.day1.dto.payment.PaymentCreateRequest;
import com.yo.day1.dto.payment.PaymentResponse;
import com.yo.day1.dto.payment.PaymentUpdateRequest;
import com.yo.day1.repository.PaymentRepository;
import com.yo.day1.repository.PromotionRepository;
import com.yo.day1.repository.TuitionInvoiceRepository;
import com.yo.day1.repository.UserRepository;
import com.yo.day1.services.AuthService;
import com.yo.day1.services.BillingService;
import com.yo.day1.services.CourseClassService;
import com.yo.day1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// using primitive float instead of BigDecimal
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final TuitionInvoiceRepository tuitionInvoiceRepository;
    private final PaymentRepository paymentRepository;
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final StudentService studentService;
    private final CourseClassService courseClassService;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public InvoiceResponse createInvoice(InvoiceCreateRequest request) throws NotFoundException {
        TuitionInvoice invoice = new TuitionInvoice();
        invoice.setInvoiceCode(request.getInvoiceCode());
        invoice.setStudent(studentService.getStudent(request.getStudentId()));
        invoice.setCourseClass(courseClassService.getCourseClass(request.getCourseClassId()));
        invoice.setBillingMonth(request.getBillingMonth());

        float originalAmount = request.getOriginalAmount() != 0
                ? request.getOriginalAmount()
                : (float) invoice.getCourseClass().getTuitionFee();
        invoice.setOriginalAmount(originalAmount);

        Promotion promotion = null;
        float discountAmount = 0f;
        if (request.getPromotionId() != null) {
            promotion = promotionRepository.findById(request.getPromotionId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy chương trình khuyến mãi với id: " + request.getPromotionId()));
            discountAmount = calculateDiscount(originalAmount, promotion);
        }

        float finalAmount = originalAmount - discountAmount;
        invoice.setPromotion(promotion);
        invoice.setDiscountAmount(discountAmount);
        invoice.setFinalAmount(finalAmount);
        invoice.setAmountPaid(0f);
        invoice.setBalanceAmount(finalAmount);
        invoice.setStatus(finalAmount == 0f ? InvoiceStatus.PAID : InvoiceStatus.UNPAID);
        invoice.setDueDate(request.getDueDate());
        invoice.setNote(request.getNote());
        return toInvoiceResponse(tuitionInvoiceRepository.save(invoice));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findInvoicesByStudent(Long studentId, String username) throws BadRequestException, NotFoundException {
        User user = authService.findActiveUserByUsername(username);
        if (user.getRole().name().equals("PARENT")) {
            studentService.getStudentForParent(studentId, user.getParent().getId());
        }
        return tuitionInvoiceRepository.findByStudentId(studentId).stream().map(this::toInvoiceResponse).toList();
    }

    @Override
    @Transactional
    public PaymentResponse addPayment(PaymentCreateRequest req, String username) throws BadRequestException, NotFoundException {
        TuitionInvoice invoice = tuitionInvoiceRepository.findById(req.getTuitionInvoiceId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hóa đơn với id: " + req.getTuitionInvoiceId()));

        User cashier;
        if (req.getCashierUserId() != null) {
            cashier = userRepository.findById(req.getCashierUserId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên thu ngân với id: " + req.getCashierUserId()));
        } else {
            cashier = authService.findActiveUserByUsername(username);
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPaymentCode(req.getPaymentCode());
        payment.setPaidAmount(req.getPaidAmount());
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setPaidAt(req.getPaidAt());
        payment.setCashierUser(cashier);
        payment.setNote(req.getNote());
        Payment saved = paymentRepository.save(payment);
        recalculateInvoice(invoice);
        return toPaymentResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) throws NotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thanh toán với id: " + id));
        return toPaymentResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream()
                .map(this::toPaymentResponse)
                .toList();
    }

    @Override
    @Transactional
    public PaymentResponse updatePayment(Long id, PaymentUpdateRequest req) throws BadRequestException, NotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy thanh toán với id: " + id));

        if (req.getPaidAmount() != null) payment.setPaidAmount(req.getPaidAmount());
        if (req.getPaymentMethod() != null) payment.setPaymentMethod(req.getPaymentMethod());
        if (req.getPaidAt() != null) payment.setPaidAt(req.getPaidAt());
        if (req.getCashierUserId() != null) {
            User cashier = userRepository.findById(req.getCashierUserId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy nhân viên thu ngân với id: " + req.getCashierUserId()));
            payment.setCashierUser(cashier);
        }
        if (req.getNote() != null) payment.setNote(req.getNote());

        Payment updated = paymentRepository.save(payment);
        recalculateInvoice(updated.getInvoice());
        return toPaymentResponse(updated);
    }

    // Tính lại amountPaid, balanceAmount và status của hóa đơn
    private void recalculateInvoice(TuitionInvoice invoice) {
        float totalPaid = paymentRepository.findByInvoiceId(invoice.getId()).stream()
                .map(Payment::getPaidAmount)
                .reduce(0f, Float::sum);

        invoice.setAmountPaid(totalPaid);
        invoice.setBalanceAmount(invoice.getFinalAmount() - totalPaid);

        if (totalPaid == 0f) {
            invoice.setStatus(InvoiceStatus.UNPAID);
        } else if (totalPaid >= invoice.getFinalAmount()) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIAL);
        }
        tuitionInvoiceRepository.save(invoice);
    }

    private float calculateDiscount(float originalAmount, Promotion promotion) {
        if (promotion.getDiscountType() == DiscountType.PERCENT) {
            return originalAmount * promotion.getDiscountValue() / 100;
        }
        return promotion.getDiscountValue();
    }

    private InvoiceResponse toInvoiceResponse(TuitionInvoice item) {
        InvoiceResponse result = mapper.map(item, InvoiceResponse.class);
        result.setStudentId(item.getStudent().getId());
        result.setStudentName(item.getStudent().getFullName());
        result.setCourseClassId(item.getCourseClass().getId());
        result.setClassName(item.getCourseClass().getName());
        result.setStatus(item.getStatus().name());
        if (item.getPromotion() != null) {
            result.setPromotionId(item.getPromotion().getId());
            result.setPromotionName(item.getPromotion().getName());
        }
        return result;
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        PaymentResponse response = mapper.map(payment, PaymentResponse.class);
        if (payment.getInvoice() != null) {
            response.setTuitionInvoiceId(payment.getInvoice().getId());
            response.setInvoiceCode(payment.getInvoice().getInvoiceCode());
        }
        if (payment.getCashierUser() != null) {
            response.setCashierUserId(payment.getCashierUser().getId());
            response.setCashierUsername(payment.getCashierUser().getUsername());
        }
        return response;
    }
}
