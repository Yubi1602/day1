package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Parent extends AuditableEntity {

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String fullName;

    @Column(columnDefinition = "varchar(20)", nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Column(columnDefinition = "varchar(255)")
    private String address;

    @Column(length = 20)
    private String relationship;

    @Column(length = 10)
    private String gender;

}
