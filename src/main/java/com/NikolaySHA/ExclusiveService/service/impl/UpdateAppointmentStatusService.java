package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import org.springframework.stereotype.Service;

@Service
public class UpdateAppointmentStatusService {
    public void updateAppointmentStatus(Appointment appointment, Status status) {
        appointment.setStatus(status);
    }
}
