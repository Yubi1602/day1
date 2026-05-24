package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.student.StudentResponse;
import com.yo.day1.dto.student.StudentUpsertRequest;
import com.yo.day1.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ApiResponse<List<StudentResponse>> getStudents() {
        return ApiResponse.success("lay danh sach hoc sinh thanh cong", studentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable Long id) {
        Optional<StudentResponse> student = studentService.findById(id);
        if (student.isPresent()) {
            return ApiResponse.success("lay hoc sinh thanh cong", student.get());
        } else {
            return ApiResponse.error("khong tim thay hoc sinh voi id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<StudentResponse> create(@Valid @RequestBody StudentUpsertRequest req) {
        return ApiResponse.success("tao hoc sinh thanh cong", studentService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentUpsertRequest req) {
        return ApiResponse.success("cap nhat hoc sinh thanh cong", studentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ApiResponse.successMessage("xoa hoc sinh thanh cong");
    }
}
