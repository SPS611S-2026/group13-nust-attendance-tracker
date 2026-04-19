package com.nust.attendance.service;

import com.nust.attendance.model.AuditLog;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(User user, String action, String ipAddress) {
        AuditLog log = AuditLog.builder()
                .user(user).action(action).ipAddress(ipAddress).build();
        auditLogRepository.save(log);
    }

    public Page<AuditLog> getLogs(String action, Long userId, int page, int size) {
        return auditLogRepository.searchLogs(action, userId, PageRequest.of(page, size));
    }
}
