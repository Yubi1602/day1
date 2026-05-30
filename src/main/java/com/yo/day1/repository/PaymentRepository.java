package com.yo.day1.repository;

import com.yo.day1.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTuitionInvoiceId(Long tuitionInvoiceId);
}

