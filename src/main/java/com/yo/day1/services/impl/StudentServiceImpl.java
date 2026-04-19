package com.yo.day1.services.impl;

import com.yo.day1.domain.entity.Student;
import com.yo.day1.repository.StudentRepository;
import com.yo.day1.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
