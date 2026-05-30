package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.teacher.TeacherResponse;
import com.yo.day1.dto.teacher.TeacherUpsertRequest;
import com.yo.day1.services.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ApiResponse<List<TeacherResponse>> getTeachers() {
        return ApiResponse.success("Lấy danh sách giáo viên thành công", teacherService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherResponse> getTeacherById(@PathVariable Long id) {
        Optional<TeacherResponse> teacher = teacherService.findById(id);
        if (teacher.isPresent()) {
            return ApiResponse.success("Lấy thông tin giáo viên thành công", teacher.get());
        } else {
            return ApiResponse.error("Không tìm thấy giáo viên với id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<TeacherResponse> create(@Valid @RequestBody TeacherUpsertRequest req) {
        return ApiResponse.success("Tạo giáo viên thành công", teacherService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<TeacherResponse> update(@PathVariable Long id, @Valid @RequestBody TeacherUpsertRequest req) {
        return ApiResponse.success("Cập nhật thông tin giáo viên thành công", teacherService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ApiResponse.successMessage("Xóa giáo viên thành công");
    }
}
