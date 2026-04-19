package com.nust.attendance.dto;
public class LoginResponse {
    private Long userId; private String token; private String studentNumber;
    private String fullName; private String role; private String email;
    public LoginResponse() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long userId; private String token; private String studentNumber;
        private String fullName; private String role; private String email;
        public Builder userId(Long v)         { this.userId = v; return this; }
        public Builder token(String v)        { this.token = v; return this; }
        public Builder studentNumber(String v){ this.studentNumber = v; return this; }
        public Builder fullName(String v)     { this.fullName = v; return this; }
        public Builder role(String v)         { this.role = v; return this; }
        public Builder email(String v)        { this.email = v; return this; }
        public LoginResponse build() {
            LoginResponse r = new LoginResponse();
            r.userId = userId; r.token = token; r.studentNumber = studentNumber;
            r.fullName = fullName; r.role = role; r.email = email; return r;
        }
    }
    public Long getUserId()          { return userId; }
    public String getToken()         { return token; }
    public String getStudentNumber() { return studentNumber; }
    public String getFullName()      { return fullName; }
    public String getRole()          { return role; }
    public String getEmail()         { return email; }
}
