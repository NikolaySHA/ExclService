package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseInDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.ShowAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import com.NikolaySHA.ExclusiveService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerIT {
    
    @Mock
    private AppointmentService appointmentService;
    
    @Mock
    private CarService carService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private ExpenseService expenseService;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private AppointmentController appointmentController;
    
    private User loggedInUser;
    private Appointment appointment;
    
    
    @BeforeEach
    void setUp() {
        loggedInUser = new User();
        loggedInUser.setId(1L);
        loggedInUser.setName("user");
        
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setUser(loggedInUser);
    }
    
    
    @Test
    void testDoAddAppointmentSuccess() {
        AddAppointmentDTO dto = new AddAppointmentDTO();
        when(userService.findLoggedUser()).thenReturn(loggedInUser);
        when(appointmentService.create(any(AddAppointmentDTO.class))).thenReturn(true);
        
        String viewName = appointmentController.doAddAppointment(dto, mock(BindingResult.class), mock(RedirectAttributes.class));
        
        assertEquals("redirect:/home", viewName);
    }
    
    @Test
    void testDoAddAppointmentFailure() {
        AddAppointmentDTO dto = new AddAppointmentDTO();
        when(userService.findLoggedUser()).thenReturn(loggedInUser);
        when(appointmentService.create(any(AddAppointmentDTO.class))).thenReturn(false);
        
        String viewName = appointmentController.doAddAppointment(dto, mock(BindingResult.class), mock(RedirectAttributes.class));
        
        assertEquals("redirect:/appointments/add", viewName);
    }
    @Test
    void testUpdateAppointmentSuccess() {
        EditAppointmentDTO dto = new EditAppointmentDTO();
        when(userService.loggedUserHasRole("ADMIN")).thenReturn(true);
        when(appointmentService.updateAppointment(anyLong(), any(EditAppointmentDTO.class))).thenReturn(true);
        
        String viewName = appointmentController.updateAppointment(1L, dto, mock(BindingResult.class), mock(RedirectAttributes.class), mock(Model.class));
        
        assertEquals("redirect:/appointments/1", viewName);
    }
    
    @Test
    void testUpdateAppointmentFailure() {
        EditAppointmentDTO dto = new EditAppointmentDTO();
        when(userService.loggedUserHasRole("ADMIN")).thenReturn(true);
        when(appointmentService.updateAppointment(anyLong(), any(EditAppointmentDTO.class))).thenReturn(false);
        
        String viewName = appointmentController.updateAppointment(1L, dto, mock(BindingResult.class), mock(RedirectAttributes.class), mock(Model.class));
        
        assertEquals("form-appointment", viewName);
    }
    
   
}
