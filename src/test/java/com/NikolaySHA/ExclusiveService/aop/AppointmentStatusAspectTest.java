package com.NikolaySHA.ExclusiveService.aop;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentStatusAspectTest {
    
    @Mock
    private ProtocolService protocolService;
    
    @Mock
    private AppointmentService appointmentService;
    
    @InjectMocks
    private AppointmentStatusAspect appointmentStatusAspect;
    
    private Appointment appointment;
    
    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setStatus(Status.SCHEDULED);
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withInProgress() {
        // Mock the behavior of appointmentService.findById
        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        
        // Call the method to be tested
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.IN_PROGRESS);
        
        // Verify that createTransferProtocol was called
        verify(protocolService, times(1)).createTransferProtocol(appointment);
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withCompleted() {
        // Mock the behavior of appointmentService.findById
        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        
        // Call the method to be tested
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.COMPLETED);
        
        // Verify that createTransferProtocol was called
        verify(protocolService, times(1)).createTransferProtocol(appointment);
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withOtherStatus() {
        // Call the method to be tested
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.SCHEDULED);
        
        // Verify that createTransferProtocol was not called
        verify(protocolService, times(0)).createTransferProtocol(any(Appointment.class));
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withNonExistentAppointment() {
        // Mock the behavior of appointmentService.findById
        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.empty());
        
        // Call the method to be tested
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.IN_PROGRESS);
        
        // Verify that createTransferProtocol was not called
        verify(protocolService, times(0)).createTransferProtocol(any(Appointment.class));
    }
}
