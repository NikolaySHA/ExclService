package com.ExclusiveService.model.entity;

import com.ExclusiveService.model.enums.UserRoles;
import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private UserRoles name;
    
    public Role() {
    }
    
    public Role(UserRoles name) {
        this.name = name;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public UserRoles getName() {
        return name;
    }
    
    public void setName(UserRoles name) {
        this.name = name;
    }
}
