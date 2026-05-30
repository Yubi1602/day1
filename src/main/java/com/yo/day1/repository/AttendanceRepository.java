package com.yo.day1.repository;

import com.yo.day1.domain.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByCourseClassIdAndStudentIdAndAttendanceDate(Long courseClassId, Long studentId, LocalDate attendanceDate);

    List<Attendance> findByCourseClassId(Long courseClassId);

    List<Attendance> findByStudentId(Long studentId);
}
