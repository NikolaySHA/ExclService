package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.dto.AddAppointmentDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.Status;
import com.ExclusiveService.repo.AppointmentRepository;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    
    @Override
    public boolean create(AddAppointmentDTO data) {
        User user = userService.findLoggedUser();
        Appointment appointment = new Appointment();
        appointment.setPaymentMethod(data.getPaymentMethod());
        appointment.setDate(data.getDate());
        appointment.setCar(data.getCar());
        appointment.setUser(data.getCar().getOwner());
        appointment.setStatus(Status.SCHEDULED);
        appointment.setPaintDetails(data.getPaintDetails());
        this.appointmentRepository.save(appointment);
        return true;
    }
    
    @Override
    public List<Appointment> getAppointments(String email) {
        List<Appointment> appointments = new ArrayList<>();
        List<Appointment> byCustomerEmail = this.appointmentRepository.findByUser_Email(email);
        if (!byCustomerEmail.isEmpty()) {
            appointments = byCustomerEmail;
        }
        return appointments;
    }
    
    @Override
    public List<Appointment> getAppointmentsForCar(String email, String licenseNumber) {
        List<Appointment> appointments = new ArrayList<>();
        List<Appointment> byCustomerEmailForCar = this.appointmentRepository.findByUser_EmailAndCar_LicensePlate(email, licenseNumber);
        if (!byCustomerEmailForCar.isEmpty()) {
            appointments = byCustomerEmailForCar;
        }
        return appointments;
    }
    
    @Override
    public List<Appointment> searchAppointments(String date, String licensePlate, String make, String client, Status status) {
        LocalDate localDate = null;
        if (date != null && !date.isEmpty()) {
            localDate = LocalDate.parse(date);
        }
        return appointmentRepository.findAppointments(localDate, licensePlate, make, client, status);
    }
    
    @Override
    public List<Appointment> getAllAppointments() {
        return this.appointmentRepository.findAll();
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateAppointmentStatus() {
        LocalDate today = LocalDate.now();
        List<Appointment> appointments = appointmentRepository.findByDate(today);
        for (Appointment appointment : appointments) {
            appointment.setStatus(Status.PENDING);
        }
        appointmentRepository.saveAll(appointments);
    }
    
    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    @Override
    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
}
