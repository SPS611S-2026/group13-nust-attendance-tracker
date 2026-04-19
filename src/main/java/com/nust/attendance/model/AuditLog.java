package com.nust.attendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 200)
    private String action;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 50)
    private String ipAddress;

    @PrePersist
    protected void onCreate() { timestamp = LocalDateTime.now(); }

    public AuditLog() {}

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private User user; private String action; private String ipAddress;
        public Builder user(User v)        { this.user = v; return this; }
        public Builder action(String v)    { this.action = v; return this; }
        public Builder ipAddress(String v) { this.ipAddress = v; return this; }
        public AuditLog build() {
            AuditLog a = new AuditLog();
            a.user = user; a.action = action; a.ipAddress = ipAddress;
            return a;
        }
    }

    public Long getLogId()              { return logId; }
    public User getUser()               { return user; }
    public void setUser(User v)         { user = v; }
    public String getAction()           { return action; }
    public void setAction(String v)     { action = v; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getIpAddress()        { return ipAddress; }
    public void setIpAddress(String v)  { ipAddress = v; }
}
