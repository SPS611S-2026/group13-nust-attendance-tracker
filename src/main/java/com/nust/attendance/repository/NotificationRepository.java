package com.nust.attendance.repository;
import com.nust.attendance.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipient_UserIdOrderBySentAtDesc(Long userId);
    long countByRecipient_UserIdAndReadFalse(Long userId);
}
