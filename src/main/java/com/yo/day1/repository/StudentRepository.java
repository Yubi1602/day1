package com.yo.day1.repository;

import com.yo.day1.domain.entity.Student;
import com.yo.day1.dto.student.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByParentId(Long parentId);

}
