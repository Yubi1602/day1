package com.yo.day1.services.impl;

import com.yo.day1.domain.entity.Teacher;
import com.yo.day1.repository.TeacherRepository;
import com.yo.day1.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll(){
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findById(Long id){
        return teacherRepository.findById(id);
    }

    public Teacher save(Teacher teacher){
        return teacherRepository.save(teacher);
    }

    public Teacher update(Long id, Teacher teacher){
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + id));
        existing.setTeacherCode(teacher.getTeacherCode());
        existing.setFullName(teacher.getFullName());
        existing.setPhone(teacher.getPhone());
        existing.setEmail(teacher.getEmail());
        existing.setTeacherRole(teacher.getTeacherRole());
        existing.setCccdImageUrl(teacher.getCccdImageUrl());
        existing.setActive(teacher.isActive());
        return teacherRepository.save(existing);
    }

    public void delete(Long id){
        teacherRepository.deleteById(id);
    }
}
