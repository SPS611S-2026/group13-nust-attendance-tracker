package com.nust.attendance.controller;

import com.nust.attendance.dto.*;
import com.nust.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/submit")
    @PreAuthorize("hasRole('LECTURER')")
    public ResponseEntity<String> submitAttendance(@RequestBody AttendanceSubmitRequest request) {
        attendanceService.submitAttendance(request);
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
