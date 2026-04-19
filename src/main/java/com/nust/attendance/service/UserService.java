package com.nust.attendance.service;

import com.nust.attendance.dto.UserCreateRequest;
import com.nust.attendance.dto.UserDTO;
import com.nust.attendance.model.Role;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.RoleRepository;
import com.nust.attendance.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDTO> getAllUsers(String search, String role, int page, int size) {
        return userRepository.searchUsers(search, role, PageRequest.of(page, size)).map(this::toDTO);
    }

    public UserDTO getUserById(Long id) {
        return toDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    public User getEntityByStudentNumber(String studentNumber) {
        return userRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("User not found: " + studentNumber));
    }

    @Transactional
    public UserDTO createUser(UserCreateRequest request) {
        if (userRepository.existsByStudentNumber(request.getStudentNumber()))
            throw new RuntimeException("Student number already exists.");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists.");

        Role role = roleRepository.findByRoleName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));

        User user = User.builder()
                .studentNumber(request.getStudentNumber())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .active(true)
                .build();

        return toDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO updateUser(Long id, UserCreateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank())
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        if (request.getRoleName() != null) {
            Role role = roleRepository.findByRoleName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found."));
            user.setRole(role);
        }
        return toDTO(userRepository.save(user));
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.setActive(true);
        userRepository.save(user);
    }

    private UserDTO toDTO(User u) {
        return UserDTO.builder()
                .userId(u.getUserId())
                .studentNumber(u.getStudentNumber())
                .fullName(u.getFullName())
                .email(u.getEmail())
                .roleName(u.getRole().getRoleName())
                .active(u.isActive())
                .build();
    }
}
