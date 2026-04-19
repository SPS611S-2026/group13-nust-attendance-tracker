package com.nust.attendance.repository;
import com.nust.attendance.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByStudent_UserIdAndSession_Module_ModuleIdOrderBySession_SessionDateAsc(Long studentId, Long moduleId);
    long countByStudent_UserIdAndSession_Module_ModuleId(Long studentId, Long moduleId);
    long countByStudent_UserIdAndSession_Module_ModuleIdAndStatus(Long studentId, Long moduleId, AttendanceRecord.AttendanceStatus status);
    Optional<AttendanceRecord> findBySession_SessionIdAndStudent_UserId(Long sessionId, Long studentId);
}
