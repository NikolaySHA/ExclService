package com.NikolaySHA.ExclusiveService.model.entity;

import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

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
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserRole userRole = (UserRole) obj;
        return Objects.equals(role, userRole.role);
    }
}
