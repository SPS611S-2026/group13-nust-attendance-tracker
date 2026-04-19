package com.nust.attendance.controller;

import com.nust.attendance.dto.UserCreateRequest;
import com.nust.attendance.dto.UserDTO;
import com.nust.attendance.model.User;
import com.nust.attendance.service.AuditLogService;
import com.nust.attendance.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuditLogService auditLogService;

    public UserController(UserService userService, AuditLogService auditLogService) {
        this.userService = userService;
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(userService.getAllUsers(search, role, page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','LECTURER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateRequest req,
                                              HttpServletRequest httpRequest) {
        UserDTO created = userService.createUser(req);
        User admin = userService.getEntityByStudentNumber(
                SecurityContextHolder.getContext().getAuthentication().getName());
        auditLogService.log(admin, "create_user — " + req.getStudentNumber() + " (" + req.getRoleName() + ")", httpRequest.getRemoteAddr());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserCreateRequest req,
                                              HttpServletRequest httpRequest) {
        UserDTO updated = userService.updateUser(id, req);
        User admin = userService.getEntityByStudentNumber(
                SecurityContextHolder.getContext().getAuthentication().getName());
        auditLogService.log(admin, "update_user — id:" + id, httpRequest.getRemoteAddr());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id,
                                                  HttpServletRequest httpRequest) {
        userService.deactivateUser(id);
        User admin = userService.getEntityByStudentNumber(
                SecurityContextHolder.getContext().getAuthentication().getName());
        auditLogService.log(admin, "deactivate_user — id:" + id, httpRequest.getRemoteAddr());
        return ResponseEntity.ok("User deactivated.");
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activateUser(@PathVariable Long id,
                                               HttpServletRequest httpRequest) {
        userService.activateUser(id);
        User admin = userService.getEntityByStudentNumber(
                SecurityContextHolder.getContext().getAuthentication().getName());
        auditLogService.log(admin, "activate_user — id:" + id, httpRequest.getRemoteAddr());
        return ResponseEntity.ok("User activated.");
    }
}