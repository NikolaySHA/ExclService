package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarViewDTOTest {
    private CarViewDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new CarViewDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        CarViewDTO dto2 = new CarViewDTO();
        assertNull(dto2.getLicensePlate());
        assertNull(dto2.getMake());
        assertNull(dto2.getModel());
        assertNull(dto2.getVin());
        assertNull(dto2.getColor());
        assertNull(dto2.getOwner());
        assertNotNull(dto2.getAppointments());
        assertTrue(dto2.getAppointments().isEmpty());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setLicensePlate("CB6666BC");
        dto.setMake("Audi");
        dto.setModel("RS6");
        dto.setVin("1234567890ABCDEF");
        dto.setColor("Blue");
        
        User owner = new User();
        owner.setName("Mad Max");
        dto.setOwner(owner);
        
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        
        List<Appointment> appointments = List.of(appointment1, appointment2);
        dto.setAppointments(appointments);
        
        assertEquals("CB6666BC", dto.getLicensePlate());
        assertEquals("Audi", dto.getMake());
        assertEquals("RS6", dto.getModel());
        assertEquals("1234567890ABCDEF", dto.getVin());
        assertEquals("Blue", dto.getColor());
        assertNotNull(dto.getOwner());
        assertEquals("Mad Max", dto.getOwner().getName());
        assertEquals(2, dto.getAppointments().size());
    }
    
    @Test
    void testAppointmentsInitialization() {
        CarViewDTO dto2 = new CarViewDTO();
        assertNotNull(dto2.getAppointments());
        assertTrue(dto2.getAppointments().isEmpty());
    }
}
