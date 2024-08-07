package com.NikolaySHA.ExclusiveService.util;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.impl.UpdateAppointmentStatusService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
public class ScheduledOperations {
    private final AppointmentService appointmentService;
    private final UpdateAppointmentStatusService updateStatusService;
    
    public ScheduledOperations(AppointmentService appointmentService, UpdateAppointmentStatusService updateStatusService) {
        this.appointmentService = appointmentService;
        this.updateStatusService = updateStatusService;
    }
    
    @Scheduled(cron = "0 0 0 * * 1-5")
    @Transactional
    public void updateAppointmentStatusAtMidnight() {
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentService.findByDate(today);
        for (Appointment appointment : appointments) {
            if (appointment.getStatus().equals(Status.SCHEDULED)){
                updateStatusService.updateAppointmentStatus(appointment, Status.PENDING);
            }
        }
        
        appointmentService.saveAll(appointments);
    }
}
