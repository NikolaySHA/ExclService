package com.NikolaySHA.ExclusiveService.model.dto.userDTO;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserRegisterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterDTOTest {
    private UserRegisterDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new UserRegisterDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getEmail());
        assertNull(dto.getPassword());
        assertNull(dto.getConfirmPassword());
        assertNull(dto.getName());
        assertNull(dto.getPhoneNumber());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setConfirmPassword("password");
        dto.setName("Mad Max");
        dto.setPhoneNumber("12345678");
        
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("password", dto.getPassword());
        assertEquals("password", dto.getConfirmPassword());
        assertEquals("Mad Max", dto.getName());
        assertEquals("12345678", dto.getPhoneNumber());
    }
    

    
    @Test
    void testPasswordLength() {
        dto.setPassword("short");
        assertEquals("short", dto.getPassword());
        
        dto.setPassword("aVeryLongPasswordThatExceedsTheLimit");
        assertEquals("aVeryLongPasswordThatExceedsTheLimit", dto.getPassword());
    }
    
    @Test
    void testPhoneNumberLength() {
        dto.setPhoneNumber("1234567");
        assertEquals("1234567", dto.getPhoneNumber());
       
        dto.setPhoneNumber("12345678901234567890");
        assertEquals("12345678901234567890", dto.getPhoneNumber());
       
    }
}
