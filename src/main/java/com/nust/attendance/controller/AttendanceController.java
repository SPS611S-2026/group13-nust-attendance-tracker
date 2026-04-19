package com.nust.attendance.controller;

import com.nust.attendance.dto.*;
import com.nust.attendance.service.AttendanceService;
import com.nust.attendance.service.AuditLogService;
import com.nust.attendance.service.UserService;

import com.nust.attendance.model.User;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")

public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AuditLogService auditLogService;
    private final UserService userService;

    public AttendanceController(AttendanceService attendanceService,
                            AuditLogService auditLogService,
                            UserService userService) {
    this.attendanceService = attendanceService;
    this.auditLogService = auditLogService;
    this.userService = userService;
}

@PostMapping("/submit")
@PreAuthorize("hasRole('LECTURER')")
public ResponseEntity<String> submitAttendance(@RequestBody AttendanceSubmitRequest request,
                                               HttpServletRequest httpRequest) {
    attendanceService.submitAttendance(request);
    String lecturerNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    User lecturer = userService.getEntityByStudentNumber(lecturerNumber);
    auditLogService.log(lecturer, "mark_attendance — session:" + request.getSessionId(), httpRequest.getRemoteAddr());
    return ResponseEntity.ok("Attendance submitted successfully.");
}
    @GetMapping("/student/{studentId}/module/{moduleId}")
    @PreAuthorize("hasAnyRole('STUDENT','LECTURER','ADMIN')")
    public ResponseEntity<AttendanceDetailDTO> getStudentModuleAttendance(
            @PathVariable Long studentId, @PathVariable Long moduleId) {
        return ResponseEntity.ok(attendanceService.getStudentModuleAttendance(studentId, moduleId));
    }

    @GetMapping("/module/{moduleId}/report")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<List<StudentReportDTO>> getModuleReport(@PathVariable Long moduleId) {
        return ResponseEntity.ok(attendanceService.getModuleReport(moduleId));
    }

    @GetMapping("/qr-checkin/{token}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> qrCheckIn(@PathVariable String token) {
        return ResponseEntity.ok(attendanceService.processQrCheckIn(token));
    }

    @PostMapping("/sessions")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<SessionDTO> createSession(@RequestBody CreateSessionRequest req) {
        return ResponseEntity.ok(
                attendanceService.createSession(req.getModuleId(), req.getSessionName(), req.getSessionDate()));
    }

    @GetMapping("/sessions/module/{moduleId}")
    @PreAuthorize("hasAnyRole('LECTURER','ADMIN')")
    public ResponseEntity<List<SessionDTO>> getSessionsForModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(attendanceService.getSessionsForModule(moduleId));
    }

    @GetMapping("/dashboard/student/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT','LECTURER','ADMIN')")
    public ResponseEntity<List<ModuleAttendanceDTO>> getStudentDashboard(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceService.getStudentDashboard(studentId));
    }
}
