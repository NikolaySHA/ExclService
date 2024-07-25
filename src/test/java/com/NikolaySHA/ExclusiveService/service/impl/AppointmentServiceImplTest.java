package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import com.NikolaySHA.ExclusiveService.service.UserService;
import com.NikolaySHA.ExclusiveService.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {
    
    @InjectMocks
    private AppointmentServiceImpl appointmentService;
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private ModelMapper modelMapper;
    
    private AddAppointmentDTO addAppointmentDTO;
    private EditAppointmentDTO editAppointmentDTO;
    private Appointment testAppointment;
    private User testUser;
    private Car testCar;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        
        testCar = new Car();
        testCar.setOwner(testUser);
        
        testAppointment = new Appointment();
        testAppointment.setId(1L);
        testAppointment.setUser(testUser);
        testAppointment.setCar(testCar);
        testAppointment.setDate(LocalDate.now());
        testAppointment.setStatus(Status.SCHEDULED);
        
        addAppointmentDTO = new AddAppointmentDTO();
        addAppointmentDTO.setDate(LocalDate.now());
        addAppointmentDTO.setPaymentMethod(PaymentMethod.ASSIGMENT_LETTER);
        addAppointmentDTO.setCar(testCar);
        addAppointmentDTO.setPaintDetails(10);
        addAppointmentDTO.setComment("Test Comment");
        
        editAppointmentDTO = new EditAppointmentDTO();
        editAppointmentDTO.setDate(LocalDate.now().plusDays(1));
        editAppointmentDTO.setPaymentMethod(PaymentMethod.PRIVATE_ORDER);
        editAppointmentDTO.setStatus(Status.COMPLETED);
    }
    
    @Test
    void testCreate() {
        appointmentRepository.save(testAppointment);
        assertEquals(1, appointmentRepository.findAll().size());
        
    }
    
    @Test
    void testGetAppointmentsByUserEmail() {
        when(appointmentRepository.findByUser_Email("test@example.com")).thenReturn(List.of(testAppointment));
        
        List<Appointment> result = appointmentService.getAppointmentsByUserEmail("test@example.com");
        
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
    }
    
    @Test
    void testSearchAppointments() {
        LocalDate date = LocalDate.now();
        when(appointmentRepository.findAppointments(date, null, null, null, Status.SCHEDULED))
                .thenReturn(List.of(testAppointment));
        
        List<Appointment> result = appointmentService.searchAppointments(date.toString(), null, null, null, Status.SCHEDULED);
        
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
    }
    
    @Test
    void testGetAllAppointments() {
        when(appointmentRepository.findAll()).thenReturn(List.of(testAppointment));
        
        List<Appointment> result = appointmentService.getAllAppointments();
        
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
    }
    
    @Test
    void testUpdateAppointmentStatus() {
        appointmentService.updateAppointmentStatus(testAppointment, Status.COMPLETED);
        
        assertEquals(Status.COMPLETED, testAppointment.getStatus());
    }
    
    @Test
    void testFindByDate() {
        LocalDate date = LocalDate.now();
        when(appointmentRepository.findByDate(date)).thenReturn(List.of(testAppointment));
        
        List<Appointment> result = appointmentService.findByDate(date);
        
        assertEquals(1, result.size());
        assertEquals(testAppointment, result.get(0));
    }
    
    @Test
    void testFindById() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        
        Optional<Appointment> result = appointmentService.findById(1L);
        
        assertTrue(result.isPresent());
        assertEquals(testAppointment, result.get());
    }
    
    @Test
    void testDelete() {
        doNothing().when(appointmentRepository).delete(testAppointment);
        
        appointmentService.delete(testAppointment);
        
        verify(appointmentRepository, times(1)).delete(testAppointment);
    }
    
    @Test
    void testUpdateAppointment() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(testAppointment));
        
        boolean result = appointmentService.updateAppointment(1L, editAppointmentDTO);
        
        assertTrue(result);
        verify(appointmentRepository, times(1)).save(testAppointment);
    }
    
    @Test
    void testSave() {
        appointmentService.save(testAppointment);
        
        verify(appointmentRepository, times(1)).save(testAppointment);
    }
    
    @Test
    void testFindByIdInitializingUsersWithCars() {
        when(appointmentRepository.findByIdWithUserAndCars(1L)).thenReturn(Optional.of(testAppointment));
        
        Optional<Appointment> result = appointmentService.findByIdInitializingUsersWithCars(1L);
        
        assertTrue(result.isPresent());
        assertEquals(testAppointment, result.get());
    }
    
    @Test
    void testSaveAll() {
        List<Appointment> appointments = List.of(testAppointment);
        
        appointmentService.saveAll(appointments);
        
        verify(appointmentRepository, times(1)).saveAll(appointments);
    }
}
