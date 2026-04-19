package com.nust.attendance.dto;
import java.util.List;
public class AttendanceDetailDTO {
    private List<AttendanceSummaryDTO> records;
    private int totalSessions; private int totalPresent; private int totalAbsent;
    private double attendancePercentage; private boolean belowThreshold;
    public AttendanceDetailDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private List<AttendanceSummaryDTO> records;
        private int totalSessions; private int totalPresent; private int totalAbsent;
        private double attendancePercentage; private boolean belowThreshold;
        public Builder records(List<AttendanceSummaryDTO> v){ this.records = v; return this; }
        public Builder totalSessions(int v)      { this.totalSessions = v; return this; }
        public Builder totalPresent(int v)       { this.totalPresent = v; return this; }
        public Builder totalAbsent(int v)        { this.totalAbsent = v; return this; }
        public Builder attendancePercentage(double v){ this.attendancePercentage = v; return this; }
        public Builder belowThreshold(boolean v) { this.belowThreshold = v; return this; }
        public AttendanceDetailDTO build() {
            AttendanceDetailDTO d = new AttendanceDetailDTO();
            d.records = records; d.totalSessions = totalSessions; d.totalPresent = totalPresent;
            d.totalAbsent = totalAbsent; d.attendancePercentage = attendancePercentage;
            d.belowThreshold = belowThreshold; return d;
        }
    }
    public List<AttendanceSummaryDTO> getRecords() { return records; }
    public int getTotalSessions()       { return totalSessions; }
    public int getTotalPresent()        { return totalPresent; }
    public int getTotalAbsent()         { return totalAbsent; }
    public double getAttendancePercentage(){ return attendancePercentage; }
    public boolean isBelowThreshold()   { return belowThreshold; }
}
