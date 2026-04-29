package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.domain.entity.Parent;
import com.yo.day1.services.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Parent>>> getParents(){
        return ResponseEntity.ok(ApiResponse.success("lay danh sach phu huynh thanh cong", parentService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Parent>> getParentById(@PathVariable Long id){
        Optional<Parent> parent = parentService.findById(id);
        if (parent.isPresent()){
            return ResponseEntity.ok(ApiResponse.success("lay phu huynh thanh cong", parent.get()));
        } else {
            return ResponseEntity.status(404).body(ApiResponse.error("khong tim thay phu huynh voi id: " + id));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Parent>> create(@RequestBody Parent parent){
        return ResponseEntity.ok(ApiResponse.success("tao phu huynh thanh cong", parentService.save(parent)));
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<Parent>> update(@PathVariable Long id, @RequestBody Parent parent){
        return ResponseEntity.ok(ApiResponse.success("cap nhat phu huynh thanh cong", parentService.update(id, parent)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        parentService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa phu huynh thanh cong"));
    }
}
