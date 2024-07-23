package com.NikolaySHA.ExclusiveService.util;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
@Component
public class ScheduledOperations {
    private final AppointmentService appointmentService;
    
    public ScheduledOperations(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }
    
    @Scheduled(cron = "0 0 0 * * 1-5")
    @Transactional
    public void updateAppointmentStatusAtMidnight() {
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentService.findByDate(today);
        for (Appointment appointment : appointments) {
            appointmentService.updateAppointmentStatus(appointment, Status.PENDING);
        }
        appointmentService.saveAll(appointments);
    }
}
