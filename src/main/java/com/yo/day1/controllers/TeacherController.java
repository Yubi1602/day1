package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.teacher.TeacherResponse;
import com.yo.day1.dto.teacher.TeacherUpsertRequest;
import com.yo.day1.services.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponse>>> getTeachers() {
        return ResponseEntity.ok(ApiResponse.success("lay danh sach giao vien thanh cong", teacherService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> getTeacherById(@PathVariable Long id) {
        return teacherService.findById(id)
                .map(teacher -> ResponseEntity.ok(ApiResponse.success("lay giao vien thanh cong", teacher)))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error("khong tim thay giao vien voi id: " + id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherResponse>> create(@Valid @RequestBody TeacherUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("tao giao vien thanh cong", teacherService.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponse>> update(@PathVariable Long id, @Valid @RequestBody TeacherUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("cap nhat giao vien thanh cong", teacherService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa giao vien thanh cong"));
    }
}
