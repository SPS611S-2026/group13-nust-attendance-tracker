package com.nust.attendance.controller;

import com.nust.attendance.model.Notification;
import com.nust.attendance.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('STUDENT','LECTURER','ADMIN')")
    public ResponseEntity<List<Map<String, Object>>> getNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);

        List<Map<String, Object>> result = notifications.stream().map(n -> {
            Map<String, Object> map = new HashMap<>();
            map.put("notificationId", n.getNotificationId());
            map.put("message", n.getMessage());
            map.put("type", n.getType());
            map.put("sentAt", n.getSentAt());
            map.put("read", n.isRead());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}/unread-count")
    @PreAuthorize("hasAnyRole('STUDENT','LECTURER','ADMIN')")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(Map.of("count", notificationService.countUnread(userId)));
    }

    @PatchMapping("/user/{userId}/mark-read")
    @PreAuthorize("hasAnyRole('STUDENT','LECTURER','ADMIN')")
    public ResponseEntity<String> markAllRead(@PathVariable Long userId) {
        notificationService.markAllRead(userId);
        return ResponseEntity.ok("Notifications marked as read.");
    }
}