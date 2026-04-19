package com.nust.attendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_records",
       uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "student_id"}))
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private AttendanceStatus status;

    private LocalDateTime markedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marked_by")
    private User markedBy;

    @PrePersist
    protected void onCreate() { markedAt = LocalDateTime.now(); }

    public enum AttendanceStatus { P, A }

    public AttendanceRecord() {}

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Session session; private User student;
        private AttendanceStatus status; private User markedBy;
        public Builder session(Session v)           { this.session = v; return this; }
        public Builder student(User v)              { this.student = v; return this; }
        public Builder status(AttendanceStatus v)   { this.status = v; return this; }
        public Builder markedBy(User v)             { this.markedBy = v; return this; }
        public AttendanceRecord build() {
            AttendanceRecord r = new AttendanceRecord();
            r.session = session; r.student = student;
            r.status = status; r.markedBy = markedBy;
            return r;
        }
    }

    public Long getRecordId()               { return recordId; }
    public Session getSession()             { return session; }
    public void setSession(Session v)       { session = v; }
    public User getStudent()                { return student; }
    public void setStudent(User v)          { student = v; }
    public AttendanceStatus getStatus()     { return status; }
    public void setStatus(AttendanceStatus v){ status = v; }
    public LocalDateTime getMarkedAt()      { return markedAt; }
    public User getMarkedBy()               { return markedBy; }
    public void setMarkedBy(User v)         { markedBy = v; }
}
