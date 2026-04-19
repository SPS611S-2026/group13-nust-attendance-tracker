package com.nust.attendance.dto;
public class StudentReportDTO {
    private Long studentId; private String studentNumber; private String fullName;
    private int totalSessions; private int sessionsAttended; private int sessionsAbsent;
    private double attendancePercentage; private boolean belowThreshold;
    public StudentReportDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long studentId; private String studentNumber; private String fullName;
        private int totalSessions; private int sessionsAttended; private int sessionsAbsent;
        private double attendancePercentage; private boolean belowThreshold;
        public Builder studentId(Long v)            { this.studentId = v; return this; }
        public Builder studentNumber(String v)       { this.studentNumber = v; return this; }
        public Builder fullName(String v)            { this.fullName = v; return this; }
        public Builder totalSessions(int v)          { this.totalSessions = v; return this; }
        public Builder sessionsAttended(int v)       { this.sessionsAttended = v; return this; }
        public Builder sessionsAbsent(int v)         { this.sessionsAbsent = v; return this; }
        public Builder attendancePercentage(double v){ this.attendancePercentage = v; return this; }
        public Builder belowThreshold(boolean v)     { this.belowThreshold = v; return this; }
        public StudentReportDTO build() {
            StudentReportDTO d = new StudentReportDTO();
            d.studentId = studentId; d.studentNumber = studentNumber; d.fullName = fullName;
            d.totalSessions = totalSessions; d.sessionsAttended = sessionsAttended;
            d.sessionsAbsent = sessionsAbsent; d.attendancePercentage = attendancePercentage;
            d.belowThreshold = belowThreshold; return d;
        }
    }
    public Long getStudentId()              { return studentId; }
    public String getStudentNumber()        { return studentNumber; }
    public String getFullName()             { return fullName; }
    public int getTotalSessions()           { return totalSessions; }
    public int getSessionsAttended()        { return sessionsAttended; }
    public int getSessionsAbsent()          { return sessionsAbsent; }
    public double getAttendancePercentage() { return attendancePercentage; }
    public boolean isBelowThreshold()       { return belowThreshold; }
}
