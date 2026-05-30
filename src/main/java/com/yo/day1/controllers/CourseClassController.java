package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.courseClass.CourseClassResponse;
import com.yo.day1.dto.courseClass.CourseClassUpsertRequest;
import com.yo.day1.services.CourseClassService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-classes")
@RequiredArgsConstructor
public class CourseClassController {

    private final CourseClassService courseClassService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<List<CourseClassResponse>> findAll() {
        return ApiResponse.success("Lấy danh sách lớp học thành công", courseClassService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<CourseClassResponse> findById(@PathVariable Long id) {
        return courseClassService.findById(id)
                .map(res -> ApiResponse.success("Lấy thông tin lớp học thành công", res))
                .orElse(ApiResponse.error("Không tìm thấy lớp học với id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<CourseClassResponse> create(@Valid @RequestBody CourseClassUpsertRequest req) {
        return ApiResponse.success("Tạo lớp học thành công", courseClassService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<CourseClassResponse> update(@PathVariable Long id, @Valid @RequestBody CourseClassUpsertRequest req) {
        return ApiResponse.success("Cập nhật lớp học thành công", courseClassService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseClassService.delete(id);
        return ApiResponse.successMessage("Xóa lớp học thành công");
    }
}
