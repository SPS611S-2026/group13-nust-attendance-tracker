package com.nust.attendance.repository;
import com.nust.attendance.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByModule_ModuleIdOrderBySessionDateDesc(Long moduleId);
    Optional<Session> findByQrCodeToken(String token);
    long countByModule_ModuleId(Long moduleId);
}
