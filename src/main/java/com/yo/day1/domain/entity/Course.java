package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "courses")
public class Course extends AuditableEntity {

    @Column(columnDefinition = "varchar(20)")
    private String courseCode;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    private double tuitionFee;

    private int totalSessions;

    private byte isActive;
}
