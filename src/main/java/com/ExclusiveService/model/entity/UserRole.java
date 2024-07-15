package com.ExclusiveService.model.entity;

import com.ExclusiveService.model.enums.UserRolesEnum;
import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private UserRolesEnum role;
    
    public UserRole() {
    }
    
    public UserRole(UserRolesEnum role) {
        this.role = role;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public UserRolesEnum getRole() {
        return role;
    }
    
    public void setRole(UserRolesEnum name) {
        this.role = name;
    }
}
