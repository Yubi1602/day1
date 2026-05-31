package com.yo.day1.services.impl;

import com.yo.day1.common.exception.BadRequestException;
import com.yo.day1.common.exception.NotFoundException;
import com.yo.day1.domain.entity.User;
import com.yo.day1.dto.auth.CurrentUserResponse;
import com.yo.day1.dto.user.UserCreateRequest;
import com.yo.day1.repository.ParentRepository;
import com.yo.day1.repository.TeacherRepository;
import com.yo.day1.repository.UserRepository;
import com.yo.day1.services.EmailService;
import com.yo.day1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";

    @Override
    @Transactional
    public CurrentUserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Tên đăng nhập đã tồn tại");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setIsActive(true);

        if (request.getParentId() != null) {
            user.setParent(parentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy phụ huynh")));
        }

        if (request.getTeacherId() != null) {
            user.setTeacher(teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy giáo viên")));
        }

        // Generate a random 10-character password
        String randomPassword = generateRandomPassword(10);
        user.setPasswordHash(passwordEncoder.encode(randomPassword));

        User savedUser = userRepository.save(user);

        // Send email notification asynchronously
        emailService.sendAccountInfoEmail(
                savedUser.getEmail(),
                savedUser.getFullName(),
                savedUser.getUsername(),
                randomPassword
        );

        return new CurrentUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFullName(),
                savedUser.getRole().name(),
                savedUser.getParent() != null ? savedUser.getParent().getId() : null,
                savedUser.getTeacher() != null ? savedUser.getTeacher().getId() : null
        );
    }

    private String generateRandomPassword(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
