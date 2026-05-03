package com.yo.day1.services;

import com.yo.day1.dto.student.StudentResponse;
import com.yo.day1.dto.student.StudentUpsertRequest;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> findAll();
    Optional<StudentResponse> findById(Long id);
    StudentResponse create(StudentUpsertRequest req);
    StudentResponse update(Long id, StudentUpsertRequest req);
    void delete(Long id);
}
