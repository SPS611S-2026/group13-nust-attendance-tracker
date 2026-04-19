package com.nust.attendance.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, unique = true, length = 50)
    private String roleName;

    public Role() {}
    public Role(Long roleId, String roleName) { this.roleId = roleId; this.roleName = roleName; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Long roleId; private String roleName;
        public Builder roleId(Long v)   { this.roleId = v; return this; }
        public Builder roleName(String v){ this.roleName = v; return this; }
        public Role build() { return new Role(roleId, roleName); }
    }

    public Long getRoleId()      { return roleId; }
    public void setRoleId(Long v){ roleId = v; }
    public String getRoleName()      { return roleName; }
    public void setRoleName(String v){ roleName = v; }
}
