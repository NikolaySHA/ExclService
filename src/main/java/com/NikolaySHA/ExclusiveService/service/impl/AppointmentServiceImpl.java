package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.UserService;
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
        appointment.setComment(data.getComment());
        this.appointmentRepository.save(appointment);
        return true;
    }
    
    @Override
    public List<Appointment> getAppointmentsByUserEmail(String email) {
        List<Appointment> appointments = new ArrayList<>();
        List<Appointment> byCustomerEmail = this.appointmentRepository.findByUser_Email(email);
        if (!byCustomerEmail.isEmpty()) {
            appointments = byCustomerEmail;
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
    public void updateAppointmentStatus(Appointment appointment, Status status) {
        appointment.setStatus(status);
    }
    
    @Override
    public List<Appointment> findByDate(LocalDate today) {
        return appointmentRepository.findByDate(today);
    }
    
    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    @Override
    public void delete(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
    
    @Override
    public boolean updateAppointment(Long id, EditAppointmentDTO appointment) {
        Optional<Appointment> toEdit = appointmentRepository.findById(id);
        if (toEdit.isEmpty()){
            return false;
        }
        Appointment editedAppointment = toEdit.get();
        if (appointment.getComment() != null && !appointment.getComment().equals(editedAppointment.getComment())){
            editedAppointment.setComment(appointment.getComment());
        }
        if (appointment.getDate() != null && !appointment.getDate().equals(editedAppointment.getDate())){
            editedAppointment.setDate(appointment.getDate());
        }
        if (appointment.getUser() != null && !appointment.getUser().equals(editedAppointment.getUser())){
            editedAppointment.setUser(appointment.getUser());
        }
        if (appointment.getPaintDetails() != null && !appointment.getPaintDetails().equals(editedAppointment.getPaintDetails())){
            editedAppointment.setPaintDetails(appointment.getPaintDetails());
        }
        if (appointment.getPaymentMethod() != null && !appointment.getPaymentMethod().equals(editedAppointment.getPaymentMethod())){
            editedAppointment.setPaymentMethod(appointment.getPaymentMethod());
        }
        if (appointment.getCar() != null && !appointment.getCar().equals(editedAppointment.getCar())){
            editedAppointment.setCar(appointment.getCar());
        }
   
        
        
        
        this.updateAppointmentStatus(editedAppointment, appointment.getStatus());
       
       
       
        this.appointmentRepository.save(editedAppointment);
        return true;
    }
    
    @Override
    public void save(Appointment data) {
        appointmentRepository.save(data);
    }
    
    @Override
    public Optional<Appointment> findByIdInitializingUsersWithCars(Long id) {
        return this.appointmentRepository.findByIdWithUserAndCars(id);
    }
    @Override
    public void saveAll(List<Appointment> appointments) {
        appointmentRepository.saveAll(appointments);
    }
}
