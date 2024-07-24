package com.NikolaySHA.ExclusiveService.util;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledOperationsTest {
    
    @Mock
    private AppointmentService appointmentService;
    
    @InjectMocks
    private ScheduledOperations scheduledOperations;
    
    @Captor
    private ArgumentCaptor<List<Appointment>> appointmentListCaptor;
    
    private List<Appointment> mockAppointments;
    
    @BeforeEach
    public void setUp() {
        mockAppointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setDate(LocalDate.now());
        appointment1.setStatus(Status.SCHEDULED);
        mockAppointments.add(appointment1);
        
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setDate(LocalDate.now());
        appointment2.setStatus(Status.SCHEDULED);
        mockAppointments.add(appointment2);
    }
    
    @Test
    public void testUpdateAppointmentStatusAtMidnight() {
        // Mock the behavior of appointmentService
        when(appointmentService.findByDate(any(LocalDate.class))).thenReturn(mockAppointments);
        
        // Mock the behavior of updateAppointmentStatus to update the status of the appointments
        doAnswer(invocation -> {
            Appointment appointment = invocation.getArgument(0);
            appointment.setStatus(Status.PENDING);
            return null;
        }).when(appointmentService).updateAppointmentStatus(any(Appointment.class), eq(Status.PENDING));
        
        // Call the method to be tested
        scheduledOperations.updateAppointmentStatusAtMidnight();
        
        // Verify that findByDate was called with today's date
        verify(appointmentService, times(1)).findByDate(LocalDate.now());
        
        // Verify that updateAppointmentStatus was called for each appointment
        verify(appointmentService, times(mockAppointments.size()))
                .updateAppointmentStatus(any(Appointment.class), eq(Status.PENDING));
        
        // Capture the list of appointments passed to saveAll
        verify(appointmentService).saveAll(appointmentListCaptor.capture());
        
        // Check that the statuses of the appointments were updated
        List<Appointment> capturedAppointments = appointmentListCaptor.getValue();
        for (Appointment appointment : capturedAppointments) {
            assertEquals(Status.PENDING, appointment.getStatus());
        }
    }
}
