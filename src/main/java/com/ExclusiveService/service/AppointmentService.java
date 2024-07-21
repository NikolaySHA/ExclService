package com.ExclusiveService.service;

import com.ExclusiveService.model.dto.AddAppointmentDTO;
import com.ExclusiveService.model.dto.EditAppointmentDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.Status;
import com.ExclusiveService.repo.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    
    
    
    boolean create(AddAppointmentDTO data);
    
    List<Appointment> getAppointments(String email);
    
    List<Appointment> getAppointmentsForCar(String email, String licenseNumber);
    
    public List<Appointment> getAllAppointments();
    void updateAppointmentStatus();
    
    List<Appointment> searchAppointments(String date, String licensePlate, String make, String client, Status status);
    
    Optional<Appointment> findById(Long id);
    Optional<Appointment> findByIdInitializingUsersWithCars(Long id);
    
    void delete(Appointment appointment);
    
    boolean updateAppointment(Long id, EditAppointmentDTO appointment);
}
