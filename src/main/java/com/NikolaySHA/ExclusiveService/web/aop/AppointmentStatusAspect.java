package com.NikolaySHA.ExclusiveService.web.aop;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class AppointmentStatusAspect {
    
    private final ProtocolService protocolService;
    private final AppointmentService appointmentService;
    
    public AppointmentStatusAspect(ProtocolService protocolService, AppointmentService appointmentService) {
        this.protocolService = protocolService;
        this.appointmentService = appointmentService;
    }
    
    @After("execution(* com.NikolaySHA.ExclusiveService.service.AppointmentService.updateAppointmentStatus(..)) && args(appointment, newStatus)")
    @Transactional
    public void afterUpdateAppointmentStatus(Appointment appointment, Status newStatus) {
        if (newStatus.equals(Status.IN_PROGRESS) || newStatus.equals(Status.COMPLETED)) {
            Optional<Appointment> updatedAppointment = appointmentService.findById(appointment.getId());
            updatedAppointment.ifPresent(protocolService::createTransferProtocol);
            System.out.println();
        }
    }
}
