package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import com.yo.day1.domain.enums.ClassStatus;
import com.yo.day1.domain.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "enrollments")
@Entity
public class Enrollment extends AuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_class_id")
    private CourseClass courseClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    @Column(length = 255)
    private String note;







}
