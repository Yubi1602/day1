package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.attendance.AttendanceCreateRequest;
import com.yo.day1.dto.attendance.AttendanceResponse;
import com.yo.day1.services.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping( value = "api/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<AttendanceResponse> create(@Valid @RequestBody AttendanceCreateRequest request, Principal principal) {
        return ApiResponse.success("Điểm danh thành công", attendanceService.create(request, principal.getName()));
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN','ACADEMIC_STAFF')")
    public ApiResponse<List<AttendanceResponse>> findByClassId(@PathVariable Long classId) {
        return ApiResponse.success("Lấy danh sách điểm danh thành công", attendanceService.findByClassId(classId));
    }
}