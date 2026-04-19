package com.yo.day1.common;

import java.time.LocalDateTime;

public record ApiResponse<T>(boolean success, String message, T data, LocalDateTime timestamp) {

    // --- CÁC HÀM THÀNH CÔNG (SUCCESS) ---

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> success(T data) {
        return success("Success", data);
    }

    public static ApiResponse<Void> successMessage(String message) {
        return success(message, null);
    }

    // --- CÁC HÀM LỖI (ERROR) ---

    // 1. Sửa lỗi hàm này: Bạn đang thiếu tham số truyền vào constructor
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data, LocalDateTime.now());
    }

    // 2. Hàm lỗi chỉ có message
    public static ApiResponse<Void> error(String message) {
        return error(message, null);
    }

    // 3. (Bổ sung thêm) Hàm lỗi mặc định khi không muốn viết message dài dòng
    public static ApiResponse<Void> fail() {
        return error("An error occurred", null);
    }
}