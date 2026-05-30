package com.yo.day1.services.impl;

import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.Teacher;
import com.yo.day1.dto.teacher.TeacherResponse;
import com.yo.day1.dto.teacher.TeacherUpsertRequest;
import com.yo.day1.repository.TeacherRepository;
import com.yo.day1.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    private TeacherResponse map(Teacher teacher) {
        return mapper.map(teacher, TeacherResponse.class);
    }

    @Override
    public List<TeacherResponse> findAll() {
        return teacherRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public Optional<TeacherResponse> findById(Long id) {
        return teacherRepository.findById(id)
                .map(this::map);
    }

    @Override
    public TeacherResponse create(TeacherUpsertRequest req) {
        Teacher teacher = mapper.map(req, Teacher.class);
        Teacher result = teacherRepository.save(teacher);
        return map(result);
    }

    @Override
    public TeacherResponse update(Long id, TeacherUpsertRequest req) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giáo viên với id: " + id));
        existing.setTeacherCode(req.getTeacherCode());
        existing.setFullName(req.getFullName());
        existing.setPhone(req.getPhone());
        existing.setEmail(req.getEmail());
        existing.setTeacherRole(req.getTeacherRole());
        existing.setCccdImageUrl(req.getCccdImageUrl());
        existing.setActive(req.isActive());
        Teacher result = teacherRepository.save(existing);
        return map(result);
    }

    @Override
    public void delete(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            throw new NotFoundException("Không tìm thấy giáo viên với id: " + id);
        }
    }
}
