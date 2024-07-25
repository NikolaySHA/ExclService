package com.NikolaySHA.ExclusiveService.web.aop;

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
        
        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.IN_PROGRESS);
 
        verify(protocolService, times(1)).createTransferProtocol(appointment);
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withCompleted() {

        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.COMPLETED);
        
        verify(protocolService, times(1)).createTransferProtocol(appointment);
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withOtherStatus() {
        
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.SCHEDULED);
        
        verify(protocolService, times(0)).createTransferProtocol(any(Appointment.class));
    }
    
    @Test
    public void testAfterUpdateAppointmentStatus_withNonExistentAppointment() {
        
        when(appointmentService.findById(appointment.getId())).thenReturn(Optional.empty());
        
        appointmentStatusAspect.afterUpdateAppointmentStatus(appointment, Status.IN_PROGRESS);
        
        verify(protocolService, times(0)).createTransferProtocol(any(Appointment.class));
    }
}
