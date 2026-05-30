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
        return ApiResponse.success("Lấy danh sách phụ huynh thành công", parentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ParentResponse> getParentById(@PathVariable Long id) {
        Optional<ParentResponse> parent = parentService.findById(id);
        if (parent.isPresent()) {
            return ApiResponse.success("Lấy thông tin phụ huynh thành công", parent.get());
        } else {
            return ApiResponse.error("Không tìm thấy phụ huynh với id: " + id);
        }
    }

    @PostMapping
    public ApiResponse<ParentResponse> create(@Valid @RequestBody ParentUpsertRequest req) {
        return ApiResponse.success("Tạo phụ huynh thành công", parentService.create(req));
    }

    @PutMapping("/{id}")
    public ApiResponse<ParentResponse> update(@PathVariable Long id, @Valid @RequestBody ParentUpsertRequest req) {
        return ApiResponse.success("Cập nhật thông tin phụ huynh thành công", parentService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        parentService.delete(id);
        return ApiResponse.successMessage("Xóa phụ huynh thành công");
    }
}
