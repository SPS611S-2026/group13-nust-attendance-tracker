package com.nust.attendance.dto;
import java.time.LocalDate;
public class AttendanceSummaryDTO {
    private Long recordId; private LocalDate sessionDate; private String sessionName;
    private String status; private String moduleCode; private String moduleName;
    public AttendanceSummaryDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long recordId; private LocalDate sessionDate; private String sessionName;
        private String status; private String moduleCode; private String moduleName;
        public Builder recordId(Long v)       { this.recordId = v; return this; }
        public Builder sessionDate(LocalDate v){ this.sessionDate = v; return this; }
        public Builder sessionName(String v)  { this.sessionName = v; return this; }
        public Builder status(String v)       { this.status = v; return this; }
        public Builder moduleCode(String v)   { this.moduleCode = v; return this; }
        public Builder moduleName(String v)   { this.moduleName = v; return this; }
        public AttendanceSummaryDTO build() {
            AttendanceSummaryDTO d = new AttendanceSummaryDTO();
            d.recordId = recordId; d.sessionDate = sessionDate; d.sessionName = sessionName;
            d.status = status; d.moduleCode = moduleCode; d.moduleName = moduleName; return d;
        }
    }
    public Long getRecordId()         { return recordId; }
    public LocalDate getSessionDate() { return sessionDate; }
    public String getSessionName()    { return sessionName; }
    public String getStatus()         { return status; }
    public String getModuleCode()     { return moduleCode; }
    public String getModuleName()     { return moduleName; }
}
