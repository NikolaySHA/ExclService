package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AddAppointmentDTOTest {
    
    private AddAppointmentDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new AddAppointmentDTO();
    }
    
    @Test
    void testDateValidation() {
      
        dto.setDate(LocalDate.now());
        assertTrue(isValidDate(dto.getDate()));
        dto.setDate(LocalDate.now().plusDays(1));
        assertTrue(isValidDate(dto.getDate()));
        dto.setDate(LocalDate.now().minusDays(1));
        assertFalse(isValidDate(dto.getDate()));
        dto.setDate(null);
        assertFalse(isValidDate(dto.getDate()));
    }
    
    @Test
    void testCarValidation() {
        dto.setCar(new Car());
        assertTrue(isValidCar(dto.getCar()));
        
        dto.setCar(null);
        assertFalse(isValidCar(dto.getCar()));
    }
    
    @Test
    void testPaintDetailsValidation() {
        dto.setPaintDetails(5);
        assertTrue(isValidPaintDetails(dto.getPaintDetails()));
        
        
        dto.setPaintDetails(null);
        assertFalse(isValidPaintDetails(dto.getPaintDetails()));
    }
    
    @Test
    void testPaymentMethodValidation() {
        
        dto.setPaymentMethod(PaymentMethod.PRIVATE_ORDER);
        assertTrue(isValidPaymentMethod(dto.getPaymentMethod()));
        
        dto.setPaymentMethod(null);
        assertFalse(isValidPaymentMethod(dto.getPaymentMethod()));
    }
    
    @Test
    void testCommentGetterAndSetter() {
    
        dto.setComment("This is a test comment.");
        assertEquals(dto.getComment(), "This is a test comment.");
      
    }
    
    // Helper methods to simulate validation logic
    
    private boolean isValidDate(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }
    
    private boolean isValidCar(Car car) {
        return car != null;
    }
    
    private boolean isValidPaintDetails(Integer paintDetails) {
        return paintDetails != null;
    }
    
    private boolean isValidPaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethod != null;
    }
 
}
