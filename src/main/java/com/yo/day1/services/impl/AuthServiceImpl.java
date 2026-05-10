package com.yo.day1.services.impl;

import com.yo.day1.domain.entity.User;
import com.yo.day1.dto.auth.AuthResponse;
import com.yo.day1.dto.auth.LoginRequest;
import com.yo.day1.repository.UserRepository;
import com.yo.day1.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    @Transactional
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        return buildTokensForUser(user, request.password()) ;
    }

}
