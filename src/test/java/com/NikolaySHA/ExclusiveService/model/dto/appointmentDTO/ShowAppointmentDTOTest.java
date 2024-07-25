package com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.TransferProtocol;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShowAppointmentDTOTest {
    private ShowAppointmentDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new ShowAppointmentDTO();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(dto.getDate());
        assertNull(dto.getUser());
        assertNull(dto.getCar());
        assertEquals(0, dto.getPaintDetails());
        assertNull(dto.getPaymentMethod());
        assertNull(dto.getStatus());
        assertNull(dto.getComment());
        assertNotNull(dto.getProtocols());
        assertTrue(dto.getProtocols().isEmpty());
    }
    
    @Test
    void testGettersAndSetters() {
        LocalDate date = LocalDate.now();
        dto.setDate(date);
        
        User user = new User();
        user.setName("Mad Max");
        dto.setUser(user);
        
        Car car = new Car();
        car.setLicensePlate("CB6666BC");
        car.setMake("Audi");
        car.setModel("RS6");
        dto.setCar(car);
        
        dto.setPaintDetails(3);
        dto.setPaymentMethod(PaymentMethod.ASSIGMENT_LETTER);
        dto.setStatus(Status.SCHEDULED);
        dto.setComment("This is a comment");
        
        TransferProtocol protocol1 = new TransferProtocol();
        protocol1.setId(1L);
        protocol1.setDate(LocalDate.now());
        protocol1.setCustomerName(user.getName());
        protocol1.setLicensePlate(car.getLicensePlate());
        protocol1.setMake(car.getMake());
        protocol1.setModel(car.getModel());
        protocol1.setFinished(true);
        
        TransferProtocol protocol2 = new TransferProtocol();
        protocol2.setId(2L);
        protocol2.setDate(LocalDate.now().plusDays(1));
        protocol2.setCustomerName(user.getName());
        protocol2.setLicensePlate(car.getLicensePlate());
        protocol2.setMake(car.getMake());
        protocol2.setModel(car.getModel());
        protocol2.setFinished(false);
        
        List<TransferProtocol> protocols = List.of(protocol1, protocol2);
        dto.setProtocols(protocols);
        
        assertEquals(date, dto.getDate());
        assertNotNull(dto.getUser());
        assertEquals("Mad Max", dto.getUser().getName());
        assertNotNull(dto.getCar());
        assertEquals("Audi", dto.getCar().getMake());
        assertEquals("RS6", dto.getCar().getModel());
        assertEquals(3, dto.getPaintDetails());
        assertEquals(PaymentMethod.ASSIGMENT_LETTER, dto.getPaymentMethod());
        assertEquals(Status.SCHEDULED, dto.getStatus());
        assertEquals("This is a comment", dto.getComment());
        assertNotNull(dto.getProtocols());
        assertEquals(2, dto.getProtocols().size());
    }
    
    @Test
    void testProtocolsInitialization() {
        ShowAppointmentDTO dto2 = new ShowAppointmentDTO();
        assertNotNull(dto2.getProtocols());
        assertTrue(dto2.getProtocols().isEmpty());
    }
}
