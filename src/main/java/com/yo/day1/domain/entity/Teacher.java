package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import com.yo.day1.domain.enums.TeacherRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "teachers")
public class Teacher extends AuditableEntity {

    @Column(columnDefinition = "varchar(20)", nullable = false, unique = true)
    private String teacherCode;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String fullName;

    @Column(columnDefinition = "varchar(20)", nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeacherRole teacherRole = TeacherRole.TEACHER;

    @Column(columnDefinition = "varchar(255)")
    private String cccdImageUrl;

    @Column(nullable = false)
    private boolean isActive = true;
}
