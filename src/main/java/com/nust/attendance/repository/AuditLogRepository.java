package com.nust.attendance.repository;
import com.nust.attendance.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    Page<AuditLog> findAllByOrderByTimestampDesc(Pageable pageable);

    @Query("SELECT a FROM AuditLog a WHERE (:action IS NULL OR LOWER(a.action) LIKE LOWER(CONCAT('%',:action,'%'))) AND (:userId IS NULL OR a.user.userId = :userId) ORDER BY a.timestamp DESC")
    Page<AuditLog> searchLogs(@Param("action") String action, @Param("userId") Long userId, Pageable pageable);
}
