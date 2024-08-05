package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import com.NikolaySHA.ExclusiveService.service.UserService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceImplTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    
    private AddAppointmentDTO addAppointmentDTO;
    private EditAppointmentDTO editAppointmentDTO;
    private Appointment appointment;
    private User user;
    private Car car;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@test.com");
        
        car = new Car();
        car.setOwner(user);
        car.setLicensePlate("CB9999BC");
        
        addAppointmentDTO = new AddAppointmentDTO();
        addAppointmentDTO.setCar(car);
        
        appointment = new Appointment();
        appointment.setUser(user);
        appointment.setStatus(Status.SCHEDULED);
        appointment.setPaintDetails(2);
        
        editAppointmentDTO = new EditAppointmentDTO();
        editAppointmentDTO.setUser(user);
        editAppointmentDTO.setComment("New Comment");
        editAppointmentDTO.setDate(LocalDate.now());
        editAppointmentDTO.setStatus(Status.PENDING);
    }
    
    @Test
    void testGetAppointmentsByUserEmail() {
        when(appointmentRepository.findByUser_Email(anyString())).thenReturn(List.of(appointment));
        
        List<Appointment> appointments = appointmentService.getAppointmentsByUserEmail("test@test.com");
        
        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findByUser_Email(anyString());
    }
    
    @Test
    void testSearchAppointments() {
        LocalDate date = LocalDate.now();
        when(appointmentRepository.findAppointments(any(), anyString(), anyString(), anyString(), any())).thenReturn(List.of(appointment));
        
        List<Appointment> appointments = appointmentService.searchAppointments(date.toString(), "123", "Make", "Client", Status.SCHEDULED);
        
        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findAppointments(any(), anyString(), anyString(), anyString(), any());
    }
    
    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));
        
        List<Appointment> appointments = appointmentService.getAllAppointments();
        
        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdateAppointmentStatus() {
        appointmentService.updateAppointmentStatus(appointment, Status.COMPLETED);
        
        assertEquals(Status.COMPLETED, appointment.getStatus());
    }
    
    @Test
    void testFindByDate() {
        when(appointmentRepository.findByDate(any(LocalDate.class))).thenReturn(List.of(appointment));
        
        List<Appointment> appointments = appointmentService.findByDate(LocalDate.now());
        
        assertEquals(1, appointments.size());
        verify(appointmentRepository, times(1)).findByDate(any(LocalDate.class));
    }
    
    @Test
    void testFindById() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        
        Optional<Appointment> foundAppointment = appointmentService.findById(1L);
        
        assertTrue(foundAppointment.isPresent());
        assertEquals(appointment, foundAppointment.get());
        verify(appointmentRepository, times(1)).findById(anyLong());
    }
    
    @Test
    void testDelete() {
        appointmentService.delete(appointment);
        
        verify(appointmentRepository, times(1)).delete(any(Appointment.class));
    }
    
    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.findById(anyLong())).thenReturn(Optional.of(appointment));
        
        boolean result = appointmentService.updateAppointment(1L, editAppointmentDTO);
        
        assertTrue(result);
        assertEquals(editAppointmentDTO.getComment(), appointment.getComment());
        assertEquals(editAppointmentDTO.getDate(), appointment.getDate());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
    
    @Test
    void testSave() {
        appointmentService.save(appointment);
        
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
    
    @Test
    void testFindByIdInitializingUsersWithCars() {
        when(appointmentRepository.findByIdWithUserAndCars(anyLong())).thenReturn(Optional.of(appointment));
        
        Optional<Appointment> foundAppointment = appointmentService.findByIdInitializingUsersWithCars(1L);
        
        assertTrue(foundAppointment.isPresent());
        assertEquals(appointment, foundAppointment.get());
        verify(appointmentRepository, times(1)).findByIdWithUserAndCars(anyLong());
    }
    
    @Test
    void testSaveAll() {
        appointmentService.saveAll(List.of(appointment));
        
        verify(appointmentRepository, times(1)).saveAll(anyList());
    }
}
