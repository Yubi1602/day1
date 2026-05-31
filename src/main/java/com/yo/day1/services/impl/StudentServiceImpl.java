package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Student;
import com.yo.day1.dto.parent.ParentResponse;
import com.yo.day1.dto.student.StudentResponse;
import com.yo.day1.dto.student.StudentUpsertRequest;
import com.yo.day1.repository.ParentRepository;
import com.yo.day1.repository.StudentRepository;
import com.yo.day1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final ModelMapper mapper;

    private StudentResponse map(Student student) {
        StudentResponse res = mapper.map(student, StudentResponse.class);
        if (student.getParent() != null) {
            res.setParentId(student.getParent().getId());
            res.setParent(mapper.map(student.getParent(), ParentResponse.class));
        } else {
            res.setParentId(null);
            res.setParent(null);
        }
        return res;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> findAll() {
        return studentRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentResponse> findById(Long id) {
        return studentRepository.findById(id)
                .map(this::map);
    }

    @Override
    @Transactional
    public StudentResponse create(StudentUpsertRequest req) {
        Student stu = mapper.map(req, Student.class);
        parentRepository.findById(req.getParentId())
                .ifPresent(stu::setParent);
        Student result = studentRepository.save(stu);
        return map(result);
    }

    @Override
    @Transactional
    public StudentResponse update(Long id, StudentUpsertRequest req) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy học sinh với id: " + id));
        existing.setStudentCode(req.getStudentCode());
        existing.setFullName(req.getFullName());
        existing.setDateOfBirth(req.getDateOfBirth());
        existing.setGender(req.getGender());
        existing.setGradeLevel(req.getGradeLevel());
        existing.setSchoolName(req.getSchoolName());
        existing.setPhone(req.getPhone());
        existing.setDescription(req.getDescription());
        existing.setStatus(req.getStatus());
        existing.setLatestScore(req.getLatestScore());
        existing.setNote(req.getNote());
        parentRepository.findById(req.getParentId())
                .ifPresent(existing::setParent);
        Student result = studentRepository.save(existing);
        return map(result);
    }

    @Override
    public void delete(Long id) {
        if (studentRepository.existsById(id)){
            studentRepository.deleteById(id);
        }else {
            throw new NotFoundException("Không tìm thấy học sinh để xóa với id: " + id);
        }

    }

    @Transactional(readOnly = true)
    public Student getStudentForParent(Long studentId, Long parentId) throws NotFoundException {
        Student student = getStudent(studentId);
        if (student.getParent() == null || !student.getParent().getId().equals(parentId)) {
            throw new org.springframework.security.access.AccessDeniedException("Student does not belong to current parent account");
        }
        return student;
    }

    public Student getStudent(Long id) throws NotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));
    }
    @Transactional(readOnly = true)
    public List<StudentResponse> findByParentId(Long parentId) {
        return studentRepository.findByParentId(parentId).stream().map(this::map).toList();
    }

}

