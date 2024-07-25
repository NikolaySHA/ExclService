package com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO;

import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentSearchDTOTest {
    
    private AppointmentSearchDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new AppointmentSearchDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getDate());
        assertNull(dto.getLicensePlate());
        assertNull(dto.getMake());
        assertNull(dto.getCustomer());
        assertNull(dto.getStatus());
        
    }
    
    @Test
    void testGettersAndSetters() {
        String date = "2024-07-23";
        String licensePlate = "CB1234BC";
        String make = "Audi";
        String customer = "Mad Max";
        Status status = Status.SCHEDULED;
        boolean unread = true;
        
        dto.setDate(date);
        dto.setLicensePlate(licensePlate);
        dto.setMake(make);
        dto.setCustomer(customer);
        dto.setStatus(status);
        
        assertEquals(date, dto.getDate());
        assertEquals(licensePlate, dto.getLicensePlate());
        assertEquals(make, dto.getMake());
        assertEquals(customer, dto.getCustomer());
        assertEquals(status, dto.getStatus());
       
    }
}
