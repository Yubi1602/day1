package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.promotion.PromotionResponse;
import com.yo.day1.dto.promotion.PromotionUpsertRequest;
import com.yo.day1.services.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    public ApiResponse<List<PromotionResponse>> findAll() {
        return ApiResponse.success("Lấy danh sách khuyến mãi thành công", promotionService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF','CASHIER')")
    public ApiResponse<PromotionResponse> findById(@PathVariable Long id) {
        return promotionService.findById(id)
                .map(res -> ApiResponse.success("Lấy thông tin khuyến mãi thành công", res))
                .orElse(ApiResponse.error("Không tìm thấy khuyến mãi với id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<PromotionResponse> create(@Valid @RequestBody PromotionUpsertRequest req) {
        return ApiResponse.success("Tạo khuyến mãi thành công", promotionService.create(req));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<PromotionResponse> update(@PathVariable Long id, @Valid @RequestBody PromotionUpsertRequest req) {
        return ApiResponse.success("Cập nhật khuyến mãi thành công", promotionService.update(id, req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        promotionService.delete(id);
        return ApiResponse.successMessage("Xóa khuyến mãi thành công");
    }
}
