package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserEditDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserEditDTOTest {
    private UserEditDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new UserEditDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getEmail());
        assertNull(dto.getName());
        assertNull(dto.getPhoneNumber());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setEmail("test@example.com");
        dto.setName("Mad Max");
        dto.setPhoneNumber("1234567890");
        
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Mad Max", dto.getName());
        assertEquals("1234567890", dto.getPhoneNumber());
    }
}
