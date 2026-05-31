package com.yo.day1.services;

import com.yo.day1.dto.auth.CurrentUserResponse;
import com.yo.day1.dto.user.UserCreateRequest;

public interface UserService {
    CurrentUserResponse createUser(UserCreateRequest request);
}
