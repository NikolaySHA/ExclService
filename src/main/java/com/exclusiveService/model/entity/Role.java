package com.exclusiveService.model.entity;

import com.exclusiveService.model.enums.UserRoles;
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
    
    public Role(String name) {
        this.name = UserRoles.valueOf(name);
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
