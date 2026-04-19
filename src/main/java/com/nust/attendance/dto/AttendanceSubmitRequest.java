package com.nust.attendance.dto;
import java.util.Map;
public class AttendanceSubmitRequest {
    private Long sessionId;
    private Map<Long, String> attendanceMap;
    public AttendanceSubmitRequest() {}
    public Long getSessionId()                     { return sessionId; }
    public void setSessionId(Long v)               { sessionId = v; }
    public Map<Long, String> getAttendanceMap()    { return attendanceMap; }
    public void setAttendanceMap(Map<Long,String> v){ attendanceMap = v; }
}
