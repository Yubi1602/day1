package com.yo.day1.controllers;

import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.auth.CurrentUserResponse;
import com.yo.day1.dto.user.UserCreateRequest;
import com.yo.day1.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ApiResponse<CurrentUserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        CurrentUserResponse createdUser = userService.createUser(request);
        return ApiResponse.success("Tạo tài khoản thành công. Thông tin đăng nhập đã được gửi qua email.", createdUser);
    }
}
