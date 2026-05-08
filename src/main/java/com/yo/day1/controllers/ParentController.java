package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.parent.ParentResponse;
import com.yo.day1.dto.parent.ParentUpsertRequest;
import com.yo.day1.services.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {
    private final ParentService parentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParentResponse>>> getParents() {
        return ResponseEntity.ok(ApiResponse.success("lay danh sach phu huynh thanh cong", parentService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentResponse>> getParentById(@PathVariable Long id) {
        return parentService.findById(id)
                .map(parent -> ResponseEntity.ok(ApiResponse.success("lay phu huynh thanh cong", parent)))
                .orElse(ResponseEntity.status(404).body(ApiResponse.error("khong tim thay phu huynh voi id: " + id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ParentResponse>> create(@Valid @RequestBody ParentUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("tao phu huynh thanh cong", parentService.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ParentResponse>> update(@PathVariable Long id, @Valid @RequestBody ParentUpsertRequest req) {
        return ResponseEntity.ok(ApiResponse.success("cap nhat phu huynh thanh cong", parentService.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        parentService.delete(id);
        return ResponseEntity.ok(ApiResponse.successMessage("xoa phu huynh thanh cong"));
    }
}
