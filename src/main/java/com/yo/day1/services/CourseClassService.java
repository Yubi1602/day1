package com.yo.day1.services;

import com.yo.day1.domain.entity.CourseClass;
import com.yo.day1.dto.courseClass.CourseClassResponse;
import com.yo.day1.dto.courseClass.CourseClassUpsertRequest;

import java.util.List;
import java.util.Optional;

public interface CourseClassService {
    CourseClass getCourseClass(Long id);
    Optional<CourseClassResponse> findById(Long id);
    List<CourseClassResponse> findAll();
    CourseClassResponse create(CourseClassUpsertRequest req);
    CourseClassResponse update(Long id, CourseClassUpsertRequest req);
    void delete(Long id);
}

