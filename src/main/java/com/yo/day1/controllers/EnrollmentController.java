package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.enrollment.EnrollmentCreateRequest;
import com.yo.day1.dto.enrollment.EnrollmentResponse;
import com.yo.day1.dto.enrollment.EnrollmentUpdateRequest;
import com.yo.day1.services.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<EnrollmentResponse> create(@Valid @RequestBody EnrollmentCreateRequest request) {
        return ApiResponse.success("Đăng ký học sinh vào lớp thành công", enrollmentService.create(request));
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<List<EnrollmentResponse>> findByClassId(@PathVariable Long classId) {
        return ApiResponse.success("Lấy danh sách đăng ký theo lớp thành công", enrollmentService.findByClassId(classId));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','PARENT')")
    public ApiResponse<List<EnrollmentResponse>> findByStudentId(@PathVariable Long studentId) {
        return ApiResponse.success("Lấy danh sách đăng ký theo học sinh thành công", enrollmentService.findByStudentId(studentId));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<EnrollmentResponse> update(@PathVariable Long id, @Valid @RequestBody EnrollmentUpdateRequest request) {
        return ApiResponse.success("Cập nhật trạng thái đăng ký thành công", enrollmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ApiResponse.successMessage("Xóa đăng ký thành công");
    }
}
