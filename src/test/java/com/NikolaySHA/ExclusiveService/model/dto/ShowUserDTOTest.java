package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowUserDTOTest {
    private ShowUserDTO dto;
    @BeforeEach
    void setUp(){
        dto = new ShowUserDTO();
    }
    @Test
    void testDefaultConstructor() {
        ShowUserDTO dto2 = new ShowUserDTO();
        assertNull(dto2.getId());
        assertNull(dto2.getName());
        assertNull(dto2.getEmail());
        assertNull(dto2.getPhoneNumber());
        assertNotNull(dto2.getCars());
        assertTrue(dto2.getCars().isEmpty());
        assertNotNull(dto2.getAppointments());
        assertTrue(dto2.getAppointments().isEmpty());
    }
    
    @Test
    void testGettersAndSetters() {
        dto.setId(1L);
        dto.setName("Mad Max");
        dto.setEmail("mad.max@example.com");
        dto.setPhoneNumber("1234567890");
        
        Car car1 = new Car();
        car1.setLicensePlate("CB6666BC");
        Car car2 = new Car();
        car2.setLicensePlate("CB9999BC");
        
        List<Car> cars = List.of(car1, car2);
        dto.setCars(cars);
        
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        
        List<Appointment> appointments = List.of(appointment1, appointment2);
        dto.setAppointments(appointments);
        
        assertEquals(1L, dto.getId());
        assertEquals("Mad Max", dto.getName());
        assertEquals("mad.max@example.com", dto.getEmail());
        assertEquals("1234567890", dto.getPhoneNumber());
        assertEquals(2, dto.getCars().size());
        assertEquals(2, dto.getAppointments().size());
    }
    
   
}
