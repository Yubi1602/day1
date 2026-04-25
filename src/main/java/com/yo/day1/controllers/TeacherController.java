package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.domain.entity.Teacher;
import com.yo.day1.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Teacher>>> getTeachers(){
        return ResponseEntity.ok(ApiResponse.success("lay danh sach giao vien thanh cong", teacherService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Teacher>> getTeacherById(@PathVariable Long id){
        Optional<Teacher> teacher = teacherService.findById(id);
        if (teacher.isPresent()){
            return ResponseEntity.ok(ApiResponse.success("lay giao vien thanh cong", teacher.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Teacher>> create(@RequestBody Teacher teacher){
        return ResponseEntity.ok(ApiResponse.success("tao giao vien thanh cong", teacherService.save(teacher)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Teacher>> update(@PathVariable Long id, @RequestBody Teacher teacher){
        return ResponseEntity.ok(ApiResponse.success("cap nhat giao vien thanh cong", teacherService.update(id, teacher)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        teacherService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa giao vien thanh cong"));
    }
}
