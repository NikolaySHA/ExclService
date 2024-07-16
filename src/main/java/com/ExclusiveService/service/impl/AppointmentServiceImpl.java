package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.dto.AddAppointmentDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.Status;
import com.ExclusiveService.repo.AppointmentRepository;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        appointment.setUser(user);
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
    public List<Appointment> getAllAppointments() {
        return this.appointmentRepository.findAll();
    }
}
