package com.NikolaySHA.ExclusiveService.model.entity;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseDTO;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Assertions.assertEquals(1L, appointment.getId());
        Assertions.assertEquals(LocalDate.now(), appointment.getDate());
        Assertions.assertEquals(user, appointment.getUser());
        Assertions.assertEquals(car, appointment.getCar());
        Assertions.assertEquals(5, appointment.getPaintDetails());
        Assertions.assertEquals(PaymentMethod.ASSIGMENT_LETTER, appointment.getPaymentMethod());
        Assertions.assertEquals(Status.SCHEDULED, appointment.getStatus());
        Assertions.assertEquals("Test Comment", appointment.getComment());
        Assertions.assertNotNull(appointment.getProtocols());
        Assertions.assertEquals(1, appointment.getProtocols().size());
        Assertions.assertEquals(protocol, appointment.getProtocols().get(0));
        assertNotNull(appointment.getExpensesList());
        assertEquals(0, appointment.getExpensesList().size());
    }
    
    @Test
    void testSetAndGetDate() {
        LocalDate newDate = LocalDate.now().plusDays(1);
        appointment.setDate(newDate);
        Assertions.assertEquals(newDate, appointment.getDate());
    }
    
    @Test
    void testSetAndGetUser() {
        User newUser = new User("new@example.com", "newpassword", "New User");
        appointment.setUser(newUser);
        Assertions.assertEquals(newUser, appointment.getUser());
    }
    
    @Test
    void testSetAndGetCar() {
        Car newCar = new Car();
        newCar.setId(2L);
        newCar.setModel("New Model");
        appointment.setCar(newCar);
        Assertions.assertEquals(newCar, appointment.getCar());
    }
    
    @Test
    void testSetAndGetPaintDetails() {
        appointment.setPaintDetails(10);
        Assertions.assertEquals(10, appointment.getPaintDetails());
    }
    
    @Test
    void testSetAndGetPaymentMethod() {
        appointment.setPaymentMethod(PaymentMethod.PRIVATE_ORDER);
        Assertions.assertEquals(PaymentMethod.PRIVATE_ORDER, appointment.getPaymentMethod());
    }
    
    @Test
    void testSetAndGetStatus() {
        appointment.setStatus(Status.COMPLETED);
        Assertions.assertEquals(Status.COMPLETED, appointment.getStatus());
    }
    
    @Test
    void testSetAndGetComment() {
        String newComment = "New Comment";
        appointment.setComment(newComment);
        Assertions.assertEquals(newComment, appointment.getComment());
    }
    
    @Test
    void testAddProtocol() {
        TransferProtocol newProtocol = new TransferProtocol();
        newProtocol.setId(2L);
        
        appointment.getProtocols().add(newProtocol);
        Assertions.assertEquals(2, appointment.getProtocols().size());
        Assertions.assertTrue(appointment.getProtocols().contains(newProtocol));
    }
    
    @Test
    void testAddExpense() {
        Long newExpense = 400L;
        appointment.getExpensesList().add(newExpense);
        Assertions.assertTrue(appointment.getExpensesList().contains(newExpense));
    }
    
    @Test
    void testInitializeExpensesList() {
        Assertions.assertNotNull(appointment.getExpensesList());
        Assertions.assertEquals(0, appointment.getExpensesList().size());
    }
    @Test
    void testSetExpensesList() {
        Assertions.assertEquals(0, appointment.getExpensesList().size());
        List<Long> expenses = new ArrayList<>();
        expenses.add(1L);
        appointment.setExpensesList(expenses);
        Assertions.assertEquals(1, appointment.getExpensesList().size());
        Assertions.assertEquals(1L, appointment.getExpensesList().get(0));
    }
}