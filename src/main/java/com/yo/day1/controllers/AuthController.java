package com.yo.day1.controllers;


import com.yo.day1.common.ApiResponse;
import com.yo.day1.dto.auth.*;
import com.yo.day1.services.AuthService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success("Đăng nhập thành công", authService.login(request));
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success("Làm mới token thành công", authService.refresh(request));
    }

    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(Principal principal,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(principal.getName(), request);
        return ApiResponse.successMessage("Đổi mật khẩu thành công");
    }

    @GetMapping("/me")
    public ApiResponse<CurrentUserResponse> me(Principal principal) {
        return ApiResponse.success(authService.me(principal.getName()));
    }
}
