package com.yo.day1.services.impl;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Promotion;
import com.yo.day1.domain.entity.TuitionInvoice;
import com.yo.day1.domain.entity.User;
import com.yo.day1.domain.enums.DiscountType;
import com.yo.day1.domain.enums.InvoiceStatus;
import com.yo.day1.dto.invoice.InvoiceCreateRequest;
import com.yo.day1.dto.invoice.InvoiceResponse;
import com.yo.day1.repository.PromotionRepository;
import com.yo.day1.repository.TuitionInvoiceRepository;
import com.yo.day1.services.AuthService;
import com.yo.day1.services.BillingService;
import com.yo.day1.services.CourseClassService;
import com.yo.day1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final TuitionInvoiceRepository tuitionInvoiceRepository;
    private final PromotionRepository promotionRepository;
    private final StudentService studentService;
    private final CourseClassService courseClassService;
    private final AuthService authService;
    private final ModelMapper mapper;

    @Transactional
    public InvoiceResponse createInvoice(InvoiceCreateRequest request) throws NotFoundException {
        TuitionInvoice invoice = new TuitionInvoice();
        invoice.setInvoiceCode(request.getInvoiceCode());
        invoice.setStudent(studentService.getStudent(request.getStudentId()));
        invoice.setCourseClass(courseClassService.getCourseClass(request.getCourseClassId()));
        invoice.setBillingMonth(request.getBillingMonth());

        BigDecimal originalAmount = request.getOriginalAmount() != 0
                ? BigDecimal.valueOf(request.getOriginalAmount())
                : BigDecimal.valueOf(invoice.getCourseClass().getTuitionFee());
        invoice.setOriginalAmount(originalAmount);

        Promotion promotion = null;
        BigDecimal discountAmount = BigDecimal.ZERO;
        if (request.getPromotionId() != null) {
            promotion = promotionRepository.findById(request.getPromotionId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy chương trình khuyến mãi với id: " + request.getPromotionId()));
            discountAmount = BigDecimal.valueOf(calculateDiscount(originalAmount.floatValue(), promotion));
        }

        BigDecimal finalAmount = originalAmount.subtract(discountAmount);
        invoice.setPromotion(promotion);
        invoice.setDiscountAmount(discountAmount);
        invoice.setFinalAmount(finalAmount);
        invoice.setAmountPaid(BigDecimal.ZERO);
        invoice.setBalanceAmount(finalAmount);
        invoice.setStatus(finalAmount.compareTo(BigDecimal.ZERO) == 0 ? InvoiceStatus.PAID : InvoiceStatus.UNPAID);
        invoice.setDueDate(request.getDueDate());
        invoice.setNote(request.getNote());
        return toInvoiceResponse(tuitionInvoiceRepository.save(invoice));
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> findInvoicesByStudent(Long studentId, String username) throws BadRequestException, NotFoundException {
        User user = authService.findActiveUserByUsername(username);
        if (user.getRole().name().equals("PARENT")) {
            studentService.getStudentForParent(studentId, user.getParent().getId());
        }
        return tuitionInvoiceRepository.findByStudentId(studentId).stream().map(this::toInvoiceResponse).toList();
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

}
