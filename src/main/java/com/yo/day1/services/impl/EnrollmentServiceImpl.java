package com.yo.day1.services.impl;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.CourseClass;
import com.yo.day1.domain.entity.Enrollment;
import com.yo.day1.domain.entity.Student;
import com.yo.day1.dto.enrollment.EnrollmentCreateRequest;
import com.yo.day1.dto.enrollment.EnrollmentResponse;
import com.yo.day1.dto.enrollment.EnrollmentUpdateRequest;
import com.yo.day1.repository.CourseClassRepository;
import com.yo.day1.repository.EnrollmentRepository;
import com.yo.day1.repository.StudentRepository;
import com.yo.day1.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository courseClassRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public EnrollmentResponse create(EnrollmentCreateRequest request) throws BadRequestException, NotFoundException {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy học sinh với id: " + request.getStudentId()));
        CourseClass courseClass = courseClassRepository.findById(request.getCourseClassId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với id: " + request.getCourseClassId()));

        if (enrollmentRepository.existsByStudentIdAndCourseClassId(request.getStudentId(), request.getCourseClassId())) {
            throw new BadRequestException("Học sinh đã được đăng ký vào lớp học này");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourseClass(courseClass);
        enrollment.setEnrolledAt(request.getEnrolledAt());
        enrollment.setStatus(request.getStatus());
        enrollment.setNote(request.getNote());

        return toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> findByClassId(Long classId) {
        courseClassRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với id: " + classId));
        return enrollmentRepository.findByCourseClassId(classId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> findByStudentId(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy học sinh với id: " + studentId));
        return enrollmentRepository.findByStudentId(studentId).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public EnrollmentResponse update(Long id, EnrollmentUpdateRequest request) throws NotFoundException {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đăng ký với id: " + id));
        enrollment.setStatus(request.getStatus());
        if (request.getNote() != null) enrollment.setNote(request.getNote());
        return toResponse(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đăng ký với id: " + id));
        enrollmentRepository.deleteById(id);
    }

    public Enrollment getEnrollment(Long studentId, Long classId) throws BadRequestException {
        return enrollmentRepository.findByStudentIdAndCourseClassId(studentId, classId)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy thông tin đăng ký cho học sinh và lớp học."));
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        EnrollmentResponse result = mapper.map(enrollment, EnrollmentResponse.class);
        result.setStudentId(enrollment.getStudent().getId());
        result.setStudentName(enrollment.getStudent().getFullName());
        result.setCourseClassId(enrollment.getCourseClass().getId());
        result.setClassName(enrollment.getCourseClass().getName());
        result.setStatus(enrollment.getStatus().name());
        return result;
    }
}
