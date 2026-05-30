package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.learningresult.LearningResultCreateRequest;
import com.yo.day1.dto.learningresult.LearningResultResponse;
import com.yo.day1.services.LearningResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/learning-result")
public class LearningResultController {
    private final LearningResultService learningResultService;
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<LearningResultResponse> create(@Valid @RequestBody LearningResultCreateRequest request, Principal principal) {
        return ApiResponse.success("Tạo kết quả học tập thành công", learningResultService.create(request, principal.getName()));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','PARENT')")
    public ApiResponse<List<LearningResultResponse>> findByStudentId(@PathVariable Long studentId, Principal principal) {
        return ApiResponse.success("Lấy danh sách kết quả học tập thành công",learningResultService.findByStudentId(studentId, principal.getName()));
    }

}
