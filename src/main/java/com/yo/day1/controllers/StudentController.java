package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.student.StudentResponse;
import com.yo.day1.dto.student.StudentUpsertRequest;
import com.yo.day1.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudents() {
        return ResponseEntity.ok(ApiResponse.success("lay danh sach hoc sinh thanh cong", studentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(stu -> ResponseEntity.ok(ApiResponse.success("lay hoc sinh thanh cong", stu)))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error("khong tim thay hoc sinh voi id: " + id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("tao hoc sinh thanh cong", studentService.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponse>> update(@PathVariable Long id, @RequestBody StudentUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("cap nhat hoc sinh thanh cong", studentService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa hoc sinh thanh cong"));
    }
}

