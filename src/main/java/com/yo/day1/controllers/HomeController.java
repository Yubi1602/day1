package com.yo.day1.controllers;

import com.yo.day1.domain.entity.Student;
import com.yo.day1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor // thây cho constructor
public class HomeController {
    private final StudentService studentService;

    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Hello World");
    }
    @GetMapping("/student")
    public ResponseEntity<List<Student>> findAll(){
        return ResponseEntity.ok(studentService.findAll());
    }
}
