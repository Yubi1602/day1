package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.domain.entity.Course;
import com.yo.day1.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getCourse(){
        return ResponseEntity.ok(ApiResponse.success("lay danh sach khoa hoc thanh cong", courseService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id){
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()){
            return ResponseEntity.ok(ApiResponse.success("lay khoa hoc thanh cong", course.get()));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.error("khong tim thay khoa hoc voi id: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> create(@RequestBody Course course){
        return ResponseEntity.ok(ApiResponse.success("tao khoa hoc thanh cong", courseService.save(course)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Course>> update(@PathVariable Long id, @RequestBody Course course){
        return ResponseEntity.ok(ApiResponse.success("cap nhat khoa hoc thanh cong", courseService.update(id, course)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        courseService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa khoa hoc thanh cong"));
    }
}

