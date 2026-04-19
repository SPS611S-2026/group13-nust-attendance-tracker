package com.nust.attendance.dto;
public class ModuleAttendanceDTO {
    private Long moduleId; private String moduleCode; private String moduleName;
    private int totalSessions; private int sessionsAttended;
    private double attendancePercentage; private boolean belowThreshold;
    public ModuleAttendanceDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long moduleId; private String moduleCode; private String moduleName;
        private int totalSessions; private int sessionsAttended;
        private double attendancePercentage; private boolean belowThreshold;
        public Builder moduleId(Long v)             { this.moduleId = v; return this; }
        public Builder moduleCode(String v)          { this.moduleCode = v; return this; }
        public Builder moduleName(String v)          { this.moduleName = v; return this; }
        public Builder totalSessions(int v)          { this.totalSessions = v; return this; }
        public Builder sessionsAttended(int v)       { this.sessionsAttended = v; return this; }
        public Builder attendancePercentage(double v){ this.attendancePercentage = v; return this; }
        public Builder belowThreshold(boolean v)     { this.belowThreshold = v; return this; }
        public ModuleAttendanceDTO build() {
            ModuleAttendanceDTO d = new ModuleAttendanceDTO();
            d.moduleId = moduleId; d.moduleCode = moduleCode; d.moduleName = moduleName;
            d.totalSessions = totalSessions; d.sessionsAttended = sessionsAttended;
            d.attendancePercentage = attendancePercentage; d.belowThreshold = belowThreshold; return d;
        }
    }
    public Long getModuleId()               { return moduleId; }
    public String getModuleCode()           { return moduleCode; }
    public String getModuleName()           { return moduleName; }
    public int getTotalSessions()           { return totalSessions; }
    public int getSessionsAttended()        { return sessionsAttended; }
    public double getAttendancePercentage() { return attendancePercentage; }
    public boolean isBelowThreshold()       { return belowThreshold; }
}
