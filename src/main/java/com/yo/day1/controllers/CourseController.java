package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.domain.entity.Course;
import com.yo.day1.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ApiResponse<List<Course>> getCourse() {
        return ApiResponse.success("lay danh sach khoa hoc thanh cong", courseService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            return ApiResponse.success("lay khoa hoc thanh cong", course.get());
        } else {
            return ApiResponse.error("khong tim thay khoa hoc voi id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<Course> create(@RequestBody Course course) {
        return ApiResponse.success("tao khoa hoc thanh cong", courseService.save(course));
    }

    @PutMapping("/{id}")
    public ApiResponse<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return ApiResponse.success("cap nhat khoa hoc thanh cong", courseService.update(id, course));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ApiResponse.successMessage("xoa khoa hoc thanh cong");
    }
}
