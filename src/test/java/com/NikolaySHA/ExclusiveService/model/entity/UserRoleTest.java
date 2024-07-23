package com.NikolaySHA.ExclusiveService.model.entity;

import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {
    
    private UserRole adminRole;
    private UserRole customerRole;
    
    @BeforeEach
    void setUp() {
        adminRole = new UserRole(UserRolesEnum.ADMIN);
        adminRole.setId(1L);
        customerRole = new UserRole(UserRolesEnum.CUSTOMER);
    }
    
    @Test
    void testUserRoleCreation() {
        assertNotNull(adminRole);
        assertEquals(adminRole.getId(), 1L);
        assertEquals(UserRolesEnum.ADMIN, adminRole.getRole());
    }
    
    @Test
    void testUserRoleSetAndGet() {
        adminRole.setRole(UserRolesEnum.CUSTOMER);
        assertEquals(UserRolesEnum.CUSTOMER, adminRole.getRole());
    }
    
    @Test
    void testEquals_DifferentObjectsSameRole() {
        UserRole anotherAdminRole = new UserRole(UserRolesEnum.ADMIN);
        assertEquals(adminRole, anotherAdminRole);
    }
    
    @Test
    void testEquals_DifferentRoles() {
        assertNotEquals(adminRole, customerRole);
    }
    
    
    @Test
    void testHashCode_DifferentRoles() {
        assertNotEquals(adminRole.hashCode(), customerRole.hashCode());
    }
}
