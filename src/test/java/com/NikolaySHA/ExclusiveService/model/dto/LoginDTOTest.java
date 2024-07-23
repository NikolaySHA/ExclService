package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginDTOTest {
    private LoginDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new LoginDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
    }
}
