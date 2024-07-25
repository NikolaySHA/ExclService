package com.NikolaySHA.ExclusiveService.model.dto.userDTO;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserSearchDTO;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSearchDTOTest {
    
    private UserSearchDTO dto;
    @BeforeEach
    void setup(){
        dto = new UserSearchDTO();
        dto.setName("Mad Max");
        dto.setEmail("mad.max@example.com");
        dto.setRole(UserRolesEnum.ADMIN);
    }
    @Test
    void testDefaultConstructor() {
        UserSearchDTO dto2 = new UserSearchDTO();
        assertNull(dto2.getName());
        assertNull(dto2.getEmail());
        assertNull(dto2.getRole());
    }
    
    @Test
    void testGettersAndSetters() {
        assertEquals("mad.max@example.com", dto.getEmail());
        assertEquals("Mad Max", dto.getName());
        assertEquals(UserRolesEnum.ADMIN, dto.getRole());
    }
    
    

   
}
