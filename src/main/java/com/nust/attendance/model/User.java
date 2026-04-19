package com.nust.attendance.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 20)
    private String studentNumber;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }

    public User() {}

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long userId; private String studentNumber; private String fullName;
        private String email; private String passwordHash; private Role role;
        private boolean active = true;
        public Builder userId(Long v)          { this.userId = v; return this; }
        public Builder studentNumber(String v)  { this.studentNumber = v; return this; }
        public Builder fullName(String v)       { this.fullName = v; return this; }
        public Builder email(String v)          { this.email = v; return this; }
        public Builder passwordHash(String v)   { this.passwordHash = v; return this; }
        public Builder role(Role v)             { this.role = v; return this; }
        public Builder active(boolean v)        { this.active = v; return this; }
        public User build() {
            User u = new User();
            u.userId = userId; u.studentNumber = studentNumber; u.fullName = fullName;
            u.email = email; u.passwordHash = passwordHash; u.role = role; u.active = active;
            return u;
        }
    }

    public Long getUserId()              { return userId; }
    public void setUserId(Long v)        { userId = v; }
    public String getStudentNumber()     { return studentNumber; }
    public void setStudentNumber(String v){ studentNumber = v; }
    public String getFullName()          { return fullName; }
    public void setFullName(String v)    { fullName = v; }
    public String getEmail()             { return email; }
    public void setEmail(String v)       { email = v; }
    public String getPasswordHash()      { return passwordHash; }
    public void setPasswordHash(String v){ passwordHash = v; }
    public Role getRole()                { return role; }
    public void setRole(Role v)          { role = v; }
    public boolean isActive()            { return active; }
    public void setActive(boolean v)     { active = v; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
}
