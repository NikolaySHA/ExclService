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
    private UserRolesEnum name;
    
    public UserRole() {
    }
    
    public UserRole(UserRolesEnum name) {
        this.name = name;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public UserRolesEnum getName() {
        return name;
    }
    
    public void setName(UserRolesEnum name) {
        this.name = name;
    }
}
