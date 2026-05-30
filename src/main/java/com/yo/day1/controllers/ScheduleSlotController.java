package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.domain.entity.ScheduleSlot;
import com.yo.day1.dto.scheduleslot.ScheduleSlotResponse;
import com.yo.day1.dto.scheduleslot.ScheduleSlotUpsertRequest;
import com.yo.day1.services.ScheduleSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule-slots")
@RequiredArgsConstructor
public class ScheduleSlotController {

    private final ScheduleSlotService scheduleSlotService;
    private final ModelMapper mapper;

    private static final Map<Integer, String> WEEKDAY_LABELS = Map.of(
            2, "Thứ 2", 3, "Thứ 3", 4, "Thứ 4",
            5, "Thứ 5", 6, "Thứ 6", 7, "Thứ 7", 8, "Chủ nhật"
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<List<ScheduleSlotResponse>> findAll() {
        List<ScheduleSlotResponse> responses = scheduleSlotService.findAll().stream()
                .map(this::toResponse).toList();
        return ApiResponse.success("Lấy danh sách ca học thành công", responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<ScheduleSlotResponse> findById(@PathVariable Long id) {
        return scheduleSlotService.findById(id)
                .map(slot -> ApiResponse.success("Lấy thông tin ca học thành công", toResponse(slot)))
                .orElse(ApiResponse.error("Không tìm thấy ca học với id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<ScheduleSlotResponse> create(@Valid @RequestBody ScheduleSlotUpsertRequest req) {
        ScheduleSlot slot = mapper.map(req, ScheduleSlot.class);
        slot.setId(null);
        ScheduleSlot saved = scheduleSlotService.save(slot);
        return ApiResponse.success("Tạo ca học thành công", toResponse(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<ScheduleSlotResponse> update(@PathVariable Long id, @Valid @RequestBody ScheduleSlotUpsertRequest req) {
        ScheduleSlot slot = scheduleSlotService.findById(id)
                .orElseThrow(() -> new com.yo.day1.common.exception.NotFoundException("Không tìm thấy ca học với id: " + id));
        mapper.map(req, slot);
        ScheduleSlot updated = scheduleSlotService.save(slot);
        return ApiResponse.success("Cập nhật ca học thành công", toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        scheduleSlotService.findById(id)
                .orElseThrow(() -> new com.yo.day1.common.exception.NotFoundException("Không tìm thấy ca học với id: " + id));
        scheduleSlotService.deleteById(id);
        return ApiResponse.successMessage("Xóa ca học thành công");
    }

    private ScheduleSlotResponse toResponse(ScheduleSlot slot) {
        ScheduleSlotResponse res = mapper.map(slot, ScheduleSlotResponse.class);
        res.setWeekdayLabel(WEEKDAY_LABELS.getOrDefault((int) slot.getWeekday(), "Không xác định"));
        return res;
    }
}
