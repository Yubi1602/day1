package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.parent.ParentResponse;
import com.yo.day1.dto.parent.ParentUpsertRequest;
import com.yo.day1.services.ParentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parents")
public class ParentController {
    private final ParentService parentService;

    @GetMapping
    public ApiResponse<List<ParentResponse>> getParents() {
        return ApiResponse.success("lay danh sach phu huynh thanh cong", parentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ParentResponse> getParentById(@PathVariable Long id) {
        Optional<ParentResponse> parent = parentService.findById(id);
        if (parent.isPresent()) {
            return ApiResponse.success("lay phu huynh thanh cong", parent.get());
        } else {
            return ApiResponse.error("khong tim thay phu huynh voi id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<ParentResponse> create(@Valid @RequestBody ParentUpsertRequest req) {
        return ApiResponse.success("tao phu huynh thanh cong", parentService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ParentResponse> update(@PathVariable Long id, @Valid @RequestBody ParentUpsertRequest req) {
        return ApiResponse.success("cap nhat phu huynh thanh cong", parentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        parentService.delete(id);
        return ApiResponse.successMessage("xoa phu huynh thanh cong");
    }
}
