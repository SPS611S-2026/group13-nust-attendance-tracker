package com.nust.attendance.dto;
import java.time.LocalDate;
public class SessionDTO {
    private Long sessionId; private String sessionName; private LocalDate sessionDate;
    private String qrCodeToken; private Long moduleId;
    public SessionDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long sessionId; private String sessionName; private LocalDate sessionDate;
        private String qrCodeToken; private Long moduleId;
        public Builder sessionId(Long v)      { this.sessionId = v; return this; }
        public Builder sessionName(String v)  { this.sessionName = v; return this; }
        public Builder sessionDate(LocalDate v){ this.sessionDate = v; return this; }
        public Builder qrCodeToken(String v)  { this.qrCodeToken = v; return this; }
        public Builder moduleId(Long v)       { this.moduleId = v; return this; }
        public SessionDTO build() {
            SessionDTO d = new SessionDTO();
            d.sessionId = sessionId; d.sessionName = sessionName; d.sessionDate = sessionDate;
            d.qrCodeToken = qrCodeToken; d.moduleId = moduleId; return d;
        }
    }
    public Long getSessionId()        { return sessionId; }
    public String getSessionName()    { return sessionName; }
    public LocalDate getSessionDate() { return sessionDate; }
    public String getQrCodeToken()    { return qrCodeToken; }
    public Long getModuleId()         { return moduleId; }
}
