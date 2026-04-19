package com.nust.attendance.dto;
import java.time.LocalDate;
public class CreateSessionRequest {
    private Long moduleId; private String sessionName; private LocalDate sessionDate;
    public CreateSessionRequest() {}
    public Long getModuleId()           { return moduleId; }
    public void setModuleId(Long v)     { moduleId = v; }
    public String getSessionName()      { return sessionName; }
    public void setSessionName(String v){ sessionName = v; }
    public LocalDate getSessionDate()   { return sessionDate; }
    public void setSessionDate(LocalDate v){ sessionDate = v; }
}
