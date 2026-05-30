package com.yo.day1.repository;

import com.yo.day1.domain.entity.TuitionInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TuitionInvoiceRepository extends JpaRepository<TuitionInvoice, Long> {
   List<TuitionInvoice> findByStudentId(Long studentId);

   List<TuitionInvoice> findByStudentParentId(Long parentId);
}
