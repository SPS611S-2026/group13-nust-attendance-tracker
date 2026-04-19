package com.nust.attendance.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "module_id"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;
    private LocalDateTime enrolledAt;
    @PrePersist
    protected void onCreate() { enrolledAt = LocalDateTime.now(); }
    public Enrollment() {}
    public Long getEnrollmentId()          { return enrollmentId; }
    public User getStudent()               { return student; }
    public void setStudent(User v)         { student = v; }
    public Module getModule()              { return module; }
    public void setModule(Module v)        { module = v; }
    public LocalDateTime getEnrolledAt()   { return enrolledAt; }
}
