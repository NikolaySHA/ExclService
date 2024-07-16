package com.ExclusiveService.model.entity;

import com.ExclusiveService.model.enums.UserRolesEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private UserRolesEnum role;
    
    public UserRole(UserRolesEnum role) {
        this.role = role;
    }
    
}
