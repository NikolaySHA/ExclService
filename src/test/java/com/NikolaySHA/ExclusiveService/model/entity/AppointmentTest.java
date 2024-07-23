package com.NikolaySHA.ExclusiveService.model.entity;

import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppointmentTest {
    private Appointment appointment;
    private User user;
    private Car car;
    private TransferProtocol protocol;
    
    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password", "Mad Max");
        
        car = new Car();
        car.setId(1L);
        car.setModel("Turbo");
        car.setMake("Porsche");
        car.setLicensePlate("CB9999BC");
        
        protocol = new TransferProtocol();
        protocol.setId(1L);
        
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDate(LocalDate.now());
        appointment.setUser(user);
        appointment.setCar(car);
        appointment.setPaintDetails(5);
        appointment.setPaymentMethod(PaymentMethod.ASSIGMENT_LETTER);
        appointment.setStatus(Status.SCHEDULED);
        appointment.setComment("Test Comment");
        appointment.getProtocols().add(protocol);
    }
    
    @Test
    void testAppointmentCreation() {
        assertNotNull(appointment);
        assertEquals(1L, appointment.getId());
        assertEquals(LocalDate.now(), appointment.getDate());
        assertEquals(user, appointment.getUser());
        assertEquals(car, appointment.getCar());
        assertEquals(5, appointment.getPaintDetails());
        assertEquals(PaymentMethod.ASSIGMENT_LETTER, appointment.getPaymentMethod());
        assertEquals(Status.SCHEDULED, appointment.getStatus());
        assertEquals("Test Comment", appointment.getComment());
        assertNotNull(appointment.getProtocols());
        assertEquals(1, appointment.getProtocols().size());
        assertEquals(protocol, appointment.getProtocols().get(0));
    }
    
    @Test
    void testSetAndGetDate() {
        LocalDate newDate = LocalDate.now().plusDays(1);
        appointment.setDate(newDate);
        assertEquals(newDate, appointment.getDate());
    }
    
    @Test
    void testSetAndGetUser() {
        User newUser = new User("new@example.com", "newpassword", "New User");
        appointment.setUser(newUser);
        assertEquals(newUser, appointment.getUser());
    }
    
    @Test
    void testSetAndGetCar() {
        Car newCar = new Car();
        newCar.setId(2L);
        newCar.setModel("New Model");
        appointment.setCar(newCar);
        assertEquals(newCar, appointment.getCar());
    }
    
    @Test
    void testSetAndGetPaintDetails() {
        appointment.setPaintDetails(10);
        assertEquals(10, appointment.getPaintDetails());
    }
    
    @Test
    void testSetAndGetPaymentMethod() {
        appointment.setPaymentMethod(PaymentMethod.PRIVATE_ORDER);
        assertEquals(PaymentMethod.PRIVATE_ORDER, appointment.getPaymentMethod());
    }
    
    @Test
    void testSetAndGetStatus() {
        appointment.setStatus(Status.COMPLETED);
        assertEquals(Status.COMPLETED, appointment.getStatus());
    }
    
    @Test
    void testSetAndGetComment() {
        String newComment = "New Comment";
        appointment.setComment(newComment);
        assertEquals(newComment, appointment.getComment());
    }
    
    @Test
    void testAddProtocol() {
        TransferProtocol newProtocol = new TransferProtocol();
        newProtocol.setId(2L);
        
        appointment.getProtocols().add(newProtocol);
        assertEquals(2, appointment.getProtocols().size());
        Assertions.assertTrue(appointment.getProtocols().contains(newProtocol));
    }
}
