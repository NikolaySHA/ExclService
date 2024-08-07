package com.NikolaySHA.ExclusiveService.web.aop;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.enums.Status;

public class Pointcut {
    @org.aspectj.lang.annotation.Pointcut("execution(* com.NikolaySHA.ExclusiveService.service.impl.UpdateAppointmentStatusService.updateAppointmentStatus(..)) && args(appointment, newStatus)")
    public void updateAppointmentStatusPointcut(Appointment appointment, Status newStatus) {}
}
