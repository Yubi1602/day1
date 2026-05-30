package com.yo.day1.services;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.User;
import com.yo.day1.dto.auth.*;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(RefreshTokenRequest request);

    void changePassword(String username, ChangePasswordRequest request);

    CurrentUserResponse me(String username);

    User findActiveUserByUsername(String username) throws BadRequestException, NotFoundException;
}
