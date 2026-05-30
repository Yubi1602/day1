package com.yo.day1.services.impl;


import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.CourseClass;
import com.yo.day1.dto.courseClass.CourseClassResponse;
import com.yo.day1.dto.courseClass.CourseClassUpsertRequest;
import com.yo.day1.repository.*;

import com.yo.day1.services.CourseClassService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseClassServiceImpl implements CourseClassService {

    private final CourseClassRepository courseClassRepository;

    private final ScheduleSlotRepository scheduleSlotRepository;

    private final CourseRepository courseRepository;

    private final TeacherRepository teacherRepository;

    private final RoomRepository roomRepository;

    private final ModelMapper mapper;

    CourseClassResponse toCourseClassResponse(CourseClass cc) {
        CourseClassResponse res = new CourseClassResponse();
        res.setId(cc.getId());
        res.setClassCode(cc.getClassCode());
        res.setName(cc.getName());
        res.setStartDate(cc.getStartDate());
        res.setEndDate(cc.getEndDate());
        res.setMaxStudents(cc.getMaxStudents());
        res.setTuitionFee(cc.getTuitionFee());
        res.setStatus(cc.getStatus());
        if (cc.getCourse() != null) {
            res.setCourseId(cc.getCourse().getId());
            res.setCourseName(cc.getCourse().getName());
        }
        if (cc.getRoom() != null) {
            res.setRoomId(cc.getRoom().getId());
            res.setRoomName(cc.getRoom().getName());
        }
        if (cc.getScheduleSlot() != null) {
            res.setScheduleSlotId(cc.getScheduleSlot().getId());
            res.setSlotCode(cc.getScheduleSlot().getSlotCode());
            res.setStartTime(cc.getScheduleSlot().getStartTime());
            res.setEndTime(cc.getScheduleSlot().getEndTime());
        }
        if (cc.getMainTeacher() != null) {
            res.setMainTeacherId(cc.getMainTeacher().getId());
            res.setMainTeacherName(cc.getMainTeacher().getFullName());
        }
        if (cc.getAssistantTeacher() != null) {
            res.setAssistantTeacherId(cc.getAssistantTeacher().getId());
            res.setAssistantTeacherName(cc.getAssistantTeacher().getFullName());
        }
        return res;
    }

    CourseClass copyToCourseClass(CourseClassUpsertRequest req, CourseClass cc) {
        cc.setClassCode(req.getClassCode());
        cc.setName(req.getName());
        cc.setStartDate(req.getStartDate());
        cc.setEndDate(req.getEndDate());
        cc.setMaxStudents(req.getMaxStudents());
        cc.setTuitionFee(req.getTuitionFee());
        cc.setStatus(req.getStatus());

        if (req.getCourseId() != null) {
            courseRepository.findById(req.getCourseId()).ifPresent(cc::setCourse);
        }
        if (req.getScheduleSlotId() != null) {
            scheduleSlotRepository.findById(req.getScheduleSlotId()).ifPresent(cc::setScheduleSlot);
        }
        if (req.getRoomId() != null) {
            roomRepository.findById(req.getRoomId()).ifPresent(cc::setRoom);
        }
        if (req.getMainTeacherId() != null) {
            teacherRepository.findById(req.getMainTeacherId()).ifPresent(cc::setMainTeacher);
        }
        if (req.getAssistantTeacherId() != null) {
            teacherRepository.findById(req.getAssistantTeacherId()).ifPresent(cc::setAssistantTeacher);
        }
        return cc;
    }

    public List<CourseClassResponse> findAll() {
        return courseClassRepository.findAll().stream()
                .map(this::toCourseClassResponse)
                .toList();
    }

    public Optional<CourseClassResponse> findById(Long id) {
        return courseClassRepository.findById(id)
                .map(this::toCourseClassResponse);
    }

    public CourseClassResponse create(CourseClassUpsertRequest req) {
        CourseClass cc = new CourseClass();
        copyToCourseClass(req, cc);
        CourseClass result = courseClassRepository.save(cc);
        return toCourseClassResponse(result);
    }

    public CourseClassResponse update(Long id, CourseClassUpsertRequest req) throws NotFoundException {
        CourseClass cc = courseClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với id: " + id));
        copyToCourseClass(req, cc);
        CourseClass result = courseClassRepository.save(cc);
        return toCourseClassResponse(result);
    }

    public void delete(Long id) {
        courseClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lớp học với id: " + id));
        courseClassRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CourseClass getCourseClass(Long id) throws NotFoundException {
        return courseClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course class not found: " + id));
    }
}