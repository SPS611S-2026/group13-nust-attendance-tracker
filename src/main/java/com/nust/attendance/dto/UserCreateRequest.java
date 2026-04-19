package com.nust.attendance.dto;
public class UserCreateRequest {
    private String studentNumber; private String fullName; private String email;
    private String password; private String roleName;
    public UserCreateRequest() {}
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String v) { studentNumber = v; }
    public String getFullName()      { return fullName; }
    public void setFullName(String v){ fullName = v; }
    public String getEmail()         { return email; }
    public void setEmail(String v)   { email = v; }
    public String getPassword()      { return password; }
    public void setPassword(String v){ password = v; }
    public String getRoleName()      { return roleName; }
    public void setRoleName(String v){ roleName = v; }
}
