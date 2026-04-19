package com.nust.attendance.service;

import com.nust.attendance.model.Module;
import com.nust.attendance.model.Notification;
import com.nust.attendance.model.User;
import com.nust.attendance.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger log = Logger.getLogger(NotificationService.class.getName());
    private final NotificationRepository notificationRepo;

    public NotificationService(NotificationRepository notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public void sendLowAttendanceAlert(User student, Module module, double percentage) {
        List<Notification> existing = notificationRepo
                .findByRecipient_UserIdOrderBySentAtDesc(student.getUserId());

        String msg = String.format(
                "Your attendance in %s (%s) is currently %.1f%%, which is below the required 80%%. " +
                "Please attend upcoming sessions to avoid academic penalties.",
                module.getModuleName(), module.getModuleCode(), percentage);

        boolean alreadySent = existing.stream().limit(5).anyMatch(n ->
                n.getMessage().contains(module.getModuleCode()) &&
                "low_attendance".equals(n.getType()));

        if (!alreadySent) {
            Notification notification = Notification.builder()
                    .recipient(student)
                    .message(msg)
                    .type("low_attendance")
                    .read(false)
                    .build();
            notificationRepo.save(notification);
            log.info("Low attendance alert sent to " + student.getStudentNumber() + " for " + module.getModuleCode());
        }
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepo.findByRecipient_UserIdOrderBySentAtDesc(userId);
    }

    public long countUnread(Long userId) {
        return notificationRepo.countByRecipient_UserIdAndIsReadFalse(userId);
    }

    public void markAllRead(Long userId) {
        List<Notification> notifications = notificationRepo.findByRecipient_UserIdOrderBySentAtDesc(userId);
        notifications.forEach(n -> n.setRead(true));
        notificationRepo.saveAll(notifications);
    }
}
