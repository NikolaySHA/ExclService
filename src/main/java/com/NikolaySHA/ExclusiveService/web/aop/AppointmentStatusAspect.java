package com.NikolaySHA.ExclusiveService.web.aop;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ProtocolService;
import com.NikolaySHA.ExclusiveService.service.impl.GmailSender;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Aspect
@Component
public class AppointmentStatusAspect {
    
    private final ProtocolService protocolService;
    private final AppointmentService appointmentService;
    private final GmailSender emailSender;
    
    public AppointmentStatusAspect(ProtocolService protocolService, AppointmentService appointmentService, GmailSender emailSender) {
        this.protocolService = protocolService;
        this.appointmentService = appointmentService;
        this.emailSender = emailSender;
    }
    
    @After("execution(* com.NikolaySHA.ExclusiveService.service.AppointmentService.updateAppointmentStatus(..)) && args(appointment, newStatus)")
    @Transactional
    public void issueTransferProtocolAfterUpdateAppointmentStatus(Appointment appointment, Status newStatus) {
        if (newStatus.equals(Status.IN_PROGRESS) || newStatus.equals(Status.COMPLETED)) {
            Optional<Appointment> updatedAppointment = appointmentService.findById(appointment.getId());
            updatedAppointment.ifPresent(protocolService::createTransferProtocol);
            System.out.println();
        }
    }
    @After("execution(* com.NikolaySHA.ExclusiveService.service.AppointmentService.updateAppointmentStatus(..)) && args(appointment, newStatus)")
    @Transactional
    public void sendEmailAfterUpdateAppointmentStatus(Appointment appointment, Status newStatus) throws MessagingException, GeneralSecurityException, IOException {
        User user = appointment.getUser();
        switch (newStatus){
            case PENDING:
                emailSender.sendMail("Записан час за сервиз", String.format("Вие имате записан час, днес - %s,\n за вашия автомобил: %s, Марка: %s, Модел: %s.\n Очакваме ви!",
                        appointment.getDate(),
                        appointment.getCar().getLicensePlate(),
                        appointment.getCar().getMake(),
                        appointment.getCar().getModel()), appointment.getUser().getEmail());
                emailSender.sendMail("exclautoservice@gmail.com", "Предстоящ ремонт",
                        String.format("Очаквайте днес - %s,/n автомобил: %s, Марка: %s, Модел: %s./n !",
                                appointment.getDate(),
                                appointment.getCar().getLicensePlate(),
                                appointment.getCar().getMake(),
                                appointment.getCar().getModel()));
            case IN_PROGRESS:
                 emailSender.sendMail(user.getEmail(), "Приет автомобил",
                        String.format("Вашия автомобил: %s, Марка: %s, Модел: %s.\n е приет за ремонт. Ще ви информираме при промяна на статуса на ремонта.",
                                appointment.getCar().getLicensePlate(),
                                appointment.getCar().getMake(),
                                appointment.getCar().getModel()));
            case COMPLETED:
                emailSender.sendMail(user.getEmail(), "Завършен ремонт",
                        String.format("Вашия автомобил: %s, Марка: %s, Модел: %s.\n е готов. Заповядайте да си го получите!.",
                                appointment.getCar().getLicensePlate(),
                                appointment.getCar().getMake(),
                                appointment.getCar().getModel()));
            
        }
    }
}
