package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import com.yo.day1.domain.enums.ClassStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "course_classes")
@Getter
@Setter
public class CourseClass extends AuditableEntity {

    @Column(columnDefinition = "varchar(20)")
    private String classCode;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_slot_id", nullable = false)
    private ScheduleSlot scheduleSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_teacher_id", nullable = false)
    private Teacher mainTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assistant_teacher_id")
    private Teacher assistantTeacher;

    private LocalDate startDate;

    private LocalDate endDate;

    private int maxStudents;

    @Column(name = "tuition_fee", columnDefinition = "decimal(12,2)")
    private double tuitionFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private ClassStatus status = ClassStatus.OPEN;
}
