package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Course;
import com.yo.day1.domain.entity.CourseClass;
import com.yo.day1.repository.CourseRepository;
import com.yo.day1.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;



    public List<Course> findAll() {
        return courseRepository.findAll();
    }
    @Override
    public List<Course> findByCourseActive(){
        return courseRepository.findByCourseActive();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Course update(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy khóa học với id: " + id));
        existing.setCourseCode(course.getCourseCode());
        existing.setName(course.getName());
        existing.setDescription(course.getDescription());
        existing.setTuitionFee(course.getTuitionFee());
        existing.setTotalSessions(course.getTotalSessions());
        existing.setIsActive(course.getIsActive());
        return courseRepository.save(existing);
    }


    public void delete(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new NotFoundException("Không tìm thấy khóa học với id: " + id);
        }
    }

}
