package com.nust.attendance.controller;

import com.nust.attendance.model.AuditLog;
import com.nust.attendance.service.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLogs(
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<AuditLog> logsPage = auditLogService.getLogs(action, userId, page, size);

        // Map to plain objects to avoid Hibernate proxy serialization issues
        List<Map<String, Object>> content = logsPage.getContent().stream().map(log -> {
            Map<String, Object> map = new HashMap<>();
            map.put("logId", log.getLogId());
            map.put("action", log.getAction());
            map.put("timestamp", log.getTimestamp());
            map.put("ipAddress", log.getIpAddress());

            if (log.getUser() != null) {
                Map<String, Object> user = new HashMap<>();
                user.put("userId", log.getUser().getUserId());
                user.put("fullName", log.getUser().getFullName());
                user.put("studentNumber", log.getUser().getStudentNumber());
                if (log.getUser().getRole() != null) {
                    Map<String, Object> role = new HashMap<>();
                    role.put("roleName", log.getUser().getRole().getRoleName());
                    user.put("role", role);
                }
                map.put("user", user);
            }
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", content);
        response.put("totalElements", logsPage.getTotalElements());
        response.put("totalPages", logsPage.getTotalPages());
        response.put("numberOfElements", logsPage.getNumberOfElements());
        response.put("page", page);

        return ResponseEntity.ok(response);
    }
}