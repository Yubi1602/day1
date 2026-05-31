package com.yo.day1.domain.entity;

import com.yo.day1.domain.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room extends AuditableEntity {
    @Column(columnDefinition = "varchar(20)")
    private String roomCode;
    @Column(columnDefinition = "varchar(100)")
    private String name;

    private int capacity;
    @Column(columnDefinition = "varchar(100)")
    private String description;

}
