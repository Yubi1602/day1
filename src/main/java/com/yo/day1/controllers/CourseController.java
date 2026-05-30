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
        return ApiResponse.success("Lấy danh sách khóa học thành công", courseService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.findById(id);
        if (course.isPresent()) {
            return ApiResponse.success("Lấy thông tin khóa học thành công", course.get());
        } else {
            return ApiResponse.error("Không tìm thấy khóa học với id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<Course> create(@RequestBody Course course) {
        return ApiResponse.success("Tạo khóa học thành công", courseService.save(course));
    }

    @PutMapping("/{id}")
    public ApiResponse<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return ApiResponse.success("Cập nhật thông tin khóa học thành công", courseService.update(id, course));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ApiResponse.successMessage("Xóa khóa học thành công");
    }
}
