package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EditAppointmentDTOTest {
    
    private EditAppointmentDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new EditAppointmentDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getDate());
        assertNull(dto.getCar());
        assertNull(dto.getPaintDetails());
        assertNull(dto.getPaymentMethod());
        assertNull(dto.getUser());
        assertNull(dto.getComment());
        assertNull(dto.getStatus());
    }
    
    @Test
    void testGettersAndSetters() {
        LocalDate date = LocalDate.now();
        Car car = new Car();
        int paintDetails = 5;
        PaymentMethod paymentMethod = PaymentMethod.PRIVATE_ORDER;
        User user = new User();
        String comment = "Test Comment";
        Status status = Status.SCHEDULED;
        
        dto.setDate(date);
        dto.setCar(car);
        dto.setPaintDetails(paintDetails);
        dto.setPaymentMethod(paymentMethod);
        dto.setUser(user);
        dto.setComment(comment);
        dto.setStatus(status);
        
        assertEquals(LocalDate.now(), dto.getDate());
        assertEquals(car, dto.getCar());
        assertEquals(5, dto.getPaintDetails());
        assertEquals(PaymentMethod.PRIVATE_ORDER, dto.getPaymentMethod());
        assertEquals(user, dto.getUser());
        assertEquals("Test Comment", dto.getComment());
        assertEquals(status, dto.getStatus());
    }
}
