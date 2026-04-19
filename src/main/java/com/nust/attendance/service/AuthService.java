package com.nust.attendance.service;

import com.nust.attendance.dto.LoginRequest;
import com.nust.attendance.dto.LoginResponse;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.UserRepository;
import com.nust.attendance.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByStudentNumber(request.getStudentNumber())
                .orElseThrow(() -> new RuntimeException("Invalid student number or password."));

        if (!user.isActive())
            throw new RuntimeException("This account has been deactivated. Contact the administrator.");

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new RuntimeException("Invalid student number or password.");

        String role  = user.getRole().getRoleName();
        String token = jwtUtil.generateToken(user.getStudentNumber(), role);

        return LoginResponse.builder()
                .userId(user.getUserId())
                .token(token)
                .studentNumber(user.getStudentNumber())
                .fullName(user.getFullName())
                .role(role)
                .email(user.getEmail())
                .build();
    }
}
