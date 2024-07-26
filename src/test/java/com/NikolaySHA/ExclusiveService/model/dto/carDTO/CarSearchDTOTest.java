package com.NikolaySHA.ExclusiveService.model.dto.carDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarSearchDTOTest {
    
    private CarSearchDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new CarSearchDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getLicensePlate());
        assertNull(dto.getMake());
        assertNull(dto.getCustomer());
    }
    
    @Test
    void testGettersAndSetters() {
        String licensePlate = "CB1234BC";
        String make = "Audi";
        String customer = "Mad Max";
        
        dto.setLicensePlate(licensePlate);
        dto.setMake(make);
        dto.setCustomer(customer);
        
        assertEquals(licensePlate, dto.getLicensePlate());
        assertEquals(make, dto.getMake());
        assertEquals(customer, dto.getCustomer());
    }
}
