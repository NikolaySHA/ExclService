package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolDTOTest {
    private ProtocolDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new ProtocolDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertEquals(0, dto.getId());
        assertNull(dto.getDate());
        assertNull(dto.getCustomerName());
        assertNull(dto.getLicensePlate());
        assertNull(dto.getMake());
        assertNull(dto.getModel());
        assertFalse(dto.isFinished());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setId(1L);
        dto.setDate(LocalDate.of(2024, 7, 23));
        dto.setCustomerName("Mad Max");
        dto.setLicensePlate("CB6666BC");
        dto.setMake("Audi");
        dto.setModel("RS6");
        dto.setFinished(true);
        
        assertEquals(1L, dto.getId());
        assertEquals(LocalDate.of(2024, 7, 23), dto.getDate());
        assertEquals("Mad Max", dto.getCustomerName());
        assertEquals("CB6666BC", dto.getLicensePlate());
        assertEquals("Audi", dto.getMake());
        assertEquals("RS6", dto.getModel());
        assertTrue(dto.isFinished());
    }
}
