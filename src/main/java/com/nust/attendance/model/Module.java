package com.nust.attendance.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    @Column(nullable = false, unique = true, length = 20)
    private String moduleCode;

    @Column(nullable = false, length = 150)
    private String moduleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id", nullable = false)
    private User lecturer;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Session> sessions;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    public Module() {}

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long moduleId; private String moduleCode; private String moduleName; private User lecturer;
        public Builder moduleId(Long v)      { this.moduleId = v; return this; }
        public Builder moduleCode(String v)  { this.moduleCode = v; return this; }
        public Builder moduleName(String v)  { this.moduleName = v; return this; }
        public Builder lecturer(User v)      { this.lecturer = v; return this; }
        public Module build() {
            Module m = new Module();
            m.moduleId = moduleId; m.moduleCode = moduleCode;
            m.moduleName = moduleName; m.lecturer = lecturer;
            return m;
        }
    }

    public Long getModuleId()            { return moduleId; }
    public void setModuleId(Long v)      { moduleId = v; }
    public String getModuleCode()        { return moduleCode; }
    public void setModuleCode(String v)  { moduleCode = v; }
    public String getModuleName()        { return moduleName; }
    public void setModuleName(String v)  { moduleName = v; }
    public User getLecturer()            { return lecturer; }
    public void setLecturer(User v)      { lecturer = v; }
}
