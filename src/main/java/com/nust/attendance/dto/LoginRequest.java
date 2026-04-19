package com.nust.attendance.dto;
public class LoginRequest {
    private String studentNumber;
    private String password;
    public LoginRequest() {}
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String v) { studentNumber = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { password = v; }
}
