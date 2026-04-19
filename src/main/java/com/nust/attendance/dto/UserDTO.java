package com.nust.attendance.dto;
public class UserDTO {
    private Long userId; private String studentNumber; private String fullName;
    private String email; private String roleName; private boolean active;
    public UserDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long userId; private String studentNumber; private String fullName;
        private String email; private String roleName; private boolean active;
        public Builder userId(Long v)          { this.userId = v; return this; }
        public Builder studentNumber(String v)  { this.studentNumber = v; return this; }
        public Builder fullName(String v)       { this.fullName = v; return this; }
        public Builder email(String v)          { this.email = v; return this; }
        public Builder roleName(String v)       { this.roleName = v; return this; }
        public Builder active(boolean v)        { this.active = v; return this; }
        public UserDTO build() {
            UserDTO d = new UserDTO();
            d.userId = userId; d.studentNumber = studentNumber; d.fullName = fullName;
            d.email = email; d.roleName = roleName; d.active = active; return d;
        }
    }
    public Long getUserId()          { return userId; }
    public String getStudentNumber() { return studentNumber; }
    public String getFullName()      { return fullName; }
    public String getEmail()         { return email; }
    public String getRoleName()      { return roleName; }
    public boolean isActive()        { return active; }
}
