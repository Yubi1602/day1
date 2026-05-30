package com.yo.day1.services;

import com.yo.day1.dto.learningresult.LearningResultCreateRequest;
import com.yo.day1.dto.learningresult.LearningResultResponse;

import java.util.List;

public interface LearningResultService {
    List<LearningResultResponse> findByStudentId(Long studentId, String username);
    LearningResultResponse create(LearningResultCreateRequest request, String username);
}
