package com.nust.attendance.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(length = 100)
    private String sessionName;

    @Column(length = 255)
    private String qrCodeToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<AttendanceRecord> records;

    public Session() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long sessionId; private Module module; private LocalDate sessionDate;
        private String sessionName; private String qrCodeToken; private User createdBy;
        public Builder sessionId(Long v)      { this.sessionId = v; return this; }
        public Builder module(Module v)       { this.module = v; return this; }
        public Builder sessionDate(LocalDate v){ this.sessionDate = v; return this; }
        public Builder sessionName(String v)  { this.sessionName = v; return this; }
        public Builder qrCodeToken(String v)  { this.qrCodeToken = v; return this; }
        public Builder createdBy(User v)      { this.createdBy = v; return this; }
        public Session build() {
            Session s = new Session();
            s.sessionId = sessionId; s.module = module; s.sessionDate = sessionDate;
            s.sessionName = sessionName; s.qrCodeToken = qrCodeToken; s.createdBy = createdBy;
            return s;
        }
    }

    public Long getSessionId()              { return sessionId; }
    public void setSessionId(Long v)        { sessionId = v; }
    public Module getModule()               { return module; }
    public void setModule(Module v)         { module = v; }
    public LocalDate getSessionDate()       { return sessionDate; }
    public void setSessionDate(LocalDate v) { sessionDate = v; }
    public String getSessionName()          { return sessionName; }
    public void setSessionName(String v)    { sessionName = v; }
    public String getQrCodeToken()          { return qrCodeToken; }
    public void setQrCodeToken(String v)    { qrCodeToken = v; }
    public User getCreatedBy()              { return createdBy; }
    public void setCreatedBy(User v)        { createdBy = v; }
}
