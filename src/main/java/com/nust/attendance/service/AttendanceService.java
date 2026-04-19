package com.nust.attendance.service;

import com.nust.attendance.dto.AttendanceDetailDTO;
import com.nust.attendance.dto.AttendanceSummaryDTO;
import com.nust.attendance.dto.AttendanceSubmitRequest;
import com.nust.attendance.dto.CreateSessionRequest;
import com.nust.attendance.dto.ModuleAttendanceDTO;
import com.nust.attendance.dto.SessionDTO;
import com.nust.attendance.dto.StudentReportDTO;
import com.nust.attendance.model.AttendanceRecord;
import com.nust.attendance.model.Enrollment;
import com.nust.attendance.model.Module;
import com.nust.attendance.model.Session;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.AttendanceRecordRepository;
import com.nust.attendance.repository.EnrollmentRepository;
import com.nust.attendance.repository.ModuleRepository;
import com.nust.attendance.repository.SessionRepository;
import com.nust.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository recordRepo;
    private final SessionRepository sessionRepo;
    private final UserRepository userRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final ModuleRepository moduleRepo;
    private final NotificationService notificationService;

    @Value("${app.attendance.threshold:80}")
    private int threshold;

    public AttendanceService(AttendanceRecordRepository recordRepo, SessionRepository sessionRepo,
                             UserRepository userRepo, EnrollmentRepository enrollmentRepo,
                             ModuleRepository moduleRepo, NotificationService notificationService) {
        this.recordRepo = recordRepo;
        this.sessionRepo = sessionRepo;
        this.userRepo = userRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.moduleRepo = moduleRepo;
        this.notificationService = notificationService;
    }

/**
 * Submits attendance for a given session.
 * Iterates through the attendance map and records each student's status.
 * Also checks attendance thresholds and sends alerts if necessary.
 *
 * @param request contains session ID and student attendance statuses
 */

    @Transactional
    public void submitAttendance(AttendanceSubmitRequest request) {
        Session session = sessionRepo.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found."));

        String currentUserNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User lecturer = userRepo.findByStudentNumber(currentUserNumber)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found."));

        for (Map.Entry<Long, String> entry : request.getAttendanceMap().entrySet()) {
            User student = userRepo.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + entry.getKey()));

            AttendanceRecord record = recordRepo
                    .findBySession_SessionIdAndStudent_UserId(session.getSessionId(), student.getUserId())
                    .orElse(AttendanceRecord.builder().session(session).student(student).build());

            record.setStatus("P".equalsIgnoreCase(entry.getValue())
                    ? AttendanceRecord.AttendanceStatus.P
                    : AttendanceRecord.AttendanceStatus.A);
            record.setMarkedBy(lecturer);
            recordRepo.save(record);
        }

        List<Enrollment> enrollments = enrollmentRepo.findByModule_ModuleId(session.getModule().getModuleId());
        for (Enrollment enrollment : enrollments) {
            checkAndSendAlert(enrollment.getStudent(), session.getModule());
        }
    }

/**
 * Processes QR code check-in for a student.
 * Validates session, ensures the student is enrolled,
 * and prevents duplicate check-ins.
 *
 * @param token QR code token for the session
 * @return confirmation message after successful check-in
 */

    @Transactional
    public String processQrCheckIn(String token) {
        Session session = sessionRepo.findByQrCodeToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired QR code."));

        if (!session.getSessionDate().equals(LocalDate.now()))
            throw new RuntimeException("This QR code has expired. It was valid on " + session.getSessionDate());

        String studentNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepo.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new RuntimeException("Student not found."));

        if (recordRepo.findBySession_SessionIdAndStudent_UserId(
                session.getSessionId(), student.getUserId()).isPresent())
            return "You have already checked in for this session.";

        if (!enrollmentRepo.existsByStudent_UserIdAndModule_ModuleId(
                student.getUserId(), session.getModule().getModuleId()))
            throw new RuntimeException("You are not enrolled in this module.");

        AttendanceRecord record = AttendanceRecord.builder()
                .session(session).student(student)
                .status(AttendanceRecord.AttendanceStatus.P).markedBy(student).build();
        recordRepo.save(record);

        checkAndSendAlert(student, session.getModule());
        return "Attendance recorded! You are marked present for " + session.getSessionName();
    }

    public AttendanceDetailDTO getStudentModuleAttendance(Long studentId, Long moduleId) {
        List<AttendanceRecord> records = recordRepo
                .findByStudent_UserIdAndSession_Module_ModuleIdOrderBySession_SessionDateAsc(studentId, moduleId);

        long total   = records.size();
        long present = records.stream().filter(r -> r.getStatus() == AttendanceRecord.AttendanceStatus.P).count();
        double pct   = total == 0 ? 0 : Math.round((double) present / total * 1000.0) / 10.0;

        List<AttendanceSummaryDTO> summaries = records.stream().map(r ->
            AttendanceSummaryDTO.builder()
                .recordId(r.getRecordId())
                .sessionDate(r.getSession().getSessionDate())
                .sessionName(r.getSession().getSessionName())
                .status(r.getStatus().name())
                .moduleCode(r.getSession().getModule().getModuleCode())
                .moduleName(r.getSession().getModule().getModuleName())
                .build()
        ).collect(Collectors.toList());

        return AttendanceDetailDTO.builder()
                .records(summaries)
                .totalSessions((int) total)
                .totalPresent((int) present)
                .totalAbsent((int) (total - present))
                .attendancePercentage(pct)
                .belowThreshold(pct < threshold)
                .build();
    }

    public List<StudentReportDTO> getModuleReport(Long moduleId) {
        List<Enrollment> enrollments = enrollmentRepo.findByModule_ModuleId(moduleId);
        long totalSessions = sessionRepo.countByModule_ModuleId(moduleId);

        return enrollments.stream().map(enrollment -> {
            User student = enrollment.getStudent();
            long present = recordRepo.countByStudent_UserIdAndSession_Module_ModuleIdAndStatus(
                    student.getUserId(), moduleId, AttendanceRecord.AttendanceStatus.P);
            double pct = totalSessions == 0 ? 0
                    : Math.round((double) present / totalSessions * 1000.0) / 10.0;

            return StudentReportDTO.builder()
                    .studentId(student.getUserId())
                    .studentNumber(student.getStudentNumber())
                    .fullName(student.getFullName())
                    .totalSessions((int) totalSessions)
                    .sessionsAttended((int) present)
                    .sessionsAbsent((int) (totalSessions - present))
                    .attendancePercentage(pct)
                    .belowThreshold(pct < threshold)
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public SessionDTO createSession(Long moduleId, String sessionName, LocalDate date) {
        String currentUserNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        User lecturer = userRepo.findByStudentNumber(currentUserNumber)
                .orElseThrow(() -> new RuntimeException("User not found."));

        Module module = moduleRepo.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found: " + moduleId));

        Session session = Session.builder()
                .module(module).sessionName(sessionName)
                .sessionDate(date).qrCodeToken(UUID.randomUUID().toString())
                .createdBy(lecturer).build();

        Session saved = sessionRepo.save(session);
        return toSessionDTO(saved);
    }

    public List<SessionDTO> getSessionsForModule(Long moduleId) {
        return sessionRepo.findByModule_ModuleIdOrderBySessionDateDesc(moduleId)
                .stream().map(this::toSessionDTO).collect(Collectors.toList());
    }

    public List<ModuleAttendanceDTO> getStudentDashboard(Long studentId) {
        return enrollmentRepo.findByStudent_UserId(studentId).stream().map(enrollment -> {
            Module module = enrollment.getModule();
            long total   = sessionRepo.countByModule_ModuleId(module.getModuleId());
            long present = recordRepo.countByStudent_UserIdAndSession_Module_ModuleIdAndStatus(
                    studentId, module.getModuleId(), AttendanceRecord.AttendanceStatus.P);
            double pct = total == 0 ? 0 : Math.round((double) present / total * 1000.0) / 10.0;

            return ModuleAttendanceDTO.builder()
                    .moduleId(module.getModuleId())
                    .moduleCode(module.getModuleCode())
                    .moduleName(module.getModuleName())
                    .totalSessions((int) total)
                    .sessionsAttended((int) present)
                    .attendancePercentage(pct)
                    .belowThreshold(pct < threshold)
                    .build();
        }).collect(Collectors.toList());
    }

    private void checkAndSendAlert(User student, Module module) {
        long total   = sessionRepo.countByModule_ModuleId(module.getModuleId());
        long present = recordRepo.countByStudent_UserIdAndSession_Module_ModuleIdAndStatus(
                student.getUserId(), module.getModuleId(), AttendanceRecord.AttendanceStatus.P);
        double pct = total == 0 ? 0 : (double) present / total * 100;
        if (pct < threshold && total > 0)
            notificationService.sendLowAttendanceAlert(student, module, pct);
    }

    private SessionDTO toSessionDTO(Session s) {
        return SessionDTO.builder()
                .sessionId(s.getSessionId())
                .sessionName(s.getSessionName())
                .sessionDate(s.getSessionDate())
                .qrCodeToken(s.getQrCodeToken())
                .moduleId(s.getModule().getModuleId())
                .build();
    }
}
