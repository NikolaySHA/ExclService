package com.NikolaySHA.ExclusiveService.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    
    private Car car;
    private User owner;
    private Appointment appointment;
    
    @BeforeEach
    void setUp() {
        owner = new User("owner@example.com", "password", "Mad Max");
        
        car = new Car();
        car.setId(1L);
        car.setLicensePlate("CB9999BC");
        car.setMake("Porsche");
        car.setModel("Turbo");
        car.setVin("WP0ZZZ12345678901");
        car.setColor("Red");
        car.setOwner(owner);
    }
    
    @Test
    void testCarCreation() {
        assertNotNull(car);
        assertEquals(1L, car.getId());
        assertEquals("CB9999BC", car.getLicensePlate());
        assertEquals("Porsche", car.getMake());
        assertEquals("Turbo", car.getModel());
        assertEquals("WP0ZZZ12345678901", car.getVin());
        assertEquals("Red", car.getColor());
        assertEquals(owner, car.getOwner());
        assertTrue(car.getAppointments().isEmpty());
    }
    @Test
    void testSetAppointments(){
        appointment = new Appointment();
        appointment.setId(1L);
        List<Appointment> list = new ArrayList<>();
        list.add(appointment);
        car.setAppointments(list);
        assertEquals(car.getAppointments().size(), 1);
    }
}
