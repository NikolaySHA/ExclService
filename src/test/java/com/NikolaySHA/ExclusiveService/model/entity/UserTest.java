package com.NikolaySHA.ExclusiveService.model.entity;



import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    
    private User user;
    private Car car;
    private Appointment appointment;
    private UserRole role;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setPhoneNumber("1234567890");
        
        car = new Car();
        car.setId(1L);
        car.setModel("Test Model");
        
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.now());
        
        role = new UserRole();
        role.setRole(UserRolesEnum.CUSTOMER);
    }
    
    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertNotNull(user.getPhoneNumber());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals(1L, user.getId());
        assertTrue(user.getAppointments().isEmpty());
        assertTrue(user.getRoles().isEmpty());
        assertTrue(user.getCars().isEmpty());
    }
    @Test
    void testUserCreationWithEmailPasswordAndName() {
        User user1 = new User("test@example.com", "password", "Mad Max");
        assertEquals("test@example.com", user1.getEmail());
        assertEquals("password", user1.getPassword());
        assertEquals("Mad Max", user1.getName());
       
    }
    
    @Test
    void testAddCar() {
        user.getCars().add(car);
        assertNotNull(user.getCars());
        assertEquals(1, user.getCars().size());
        assertEquals(car, user.getCars().get(0));
    }
    
    @Test
    void testAddAppointment() {
        user.getAppointments().add(appointment);
        assertNotNull(user.getAppointments());
        assertEquals(1, user.getAppointments().size());
        assertEquals(appointment, user.getAppointments().get(0));
    }
    
    @Test
    void testAddRole() {
        user.getRoles().add(role);
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
        assertEquals(role, user.getRoles().get(0));
    }
    
}
