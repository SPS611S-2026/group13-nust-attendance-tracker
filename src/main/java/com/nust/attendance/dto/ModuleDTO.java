package com.nust.attendance.dto;
public class ModuleDTO {
    private Long moduleId; private String moduleCode; private String moduleName;
    private String lecturerName; private Long lecturerId; private int studentCount; private int sessionCount;
    public ModuleDTO() {}
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long moduleId; private String moduleCode; private String moduleName;
        private String lecturerName; private Long lecturerId; private int studentCount; private int sessionCount;
        public Builder moduleId(Long v)       { this.moduleId = v; return this; }
        public Builder moduleCode(String v)   { this.moduleCode = v; return this; }
        public Builder moduleName(String v)   { this.moduleName = v; return this; }
        public Builder lecturerName(String v) { this.lecturerName = v; return this; }
        public Builder lecturerId(Long v)     { this.lecturerId = v; return this; }
        public Builder studentCount(int v)    { this.studentCount = v; return this; }
        public Builder sessionCount(int v)    { this.sessionCount = v; return this; }
        public ModuleDTO build() {
            ModuleDTO d = new ModuleDTO();
            d.moduleId = moduleId; d.moduleCode = moduleCode; d.moduleName = moduleName;
            d.lecturerName = lecturerName; d.lecturerId = lecturerId;
            d.studentCount = studentCount; d.sessionCount = sessionCount; return d;
        }
    }
    public Long getModuleId()       { return moduleId; }
    public String getModuleCode()   { return moduleCode; }
    public String getModuleName()   { return moduleName; }
    public String getLecturerName() { return lecturerName; }
    public Long getLecturerId()     { return lecturerId; }
    public int getStudentCount()    { return studentCount; }
    public int getSessionCount()    { return sessionCount; }
}
