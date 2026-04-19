package com.nust.attendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(length = 50)
    private String type;

    private LocalDateTime sentAt;

    @Column(name = "is_read")
    private boolean read = false;

    @PrePersist
    protected void onCreate() { sentAt = LocalDateTime.now(); }

    public Notification() {}

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private User recipient; private String message; private String type; private boolean read;
        public Builder recipient(User v)  { this.recipient = v; return this; }
        public Builder message(String v)  { this.message = v; return this; }
        public Builder type(String v)     { this.type = v; return this; }
        public Builder read(boolean v)    { this.read = v; return this; }
        public Notification build() {
            Notification n = new Notification();
            n.recipient = recipient; n.message = message; n.type = type; n.read = read;
            return n;
        }
    }

    public Long getNotificationId()      { return notificationId; }
    public User getRecipient()           { return recipient; }
    public void setRecipient(User v)     { recipient = v; }
    public String getMessage()           { return message; }
    public void setMessage(String v)     { message = v; }
    public String getType()              { return type; }
    public void setType(String v)        { type = v; }
    public LocalDateTime getSentAt()     { return sentAt; }
    public boolean isRead()              { return read; }
    public void setRead(boolean v)       { read = v; }
}
