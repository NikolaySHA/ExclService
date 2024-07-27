package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AppointmentService appointmentService;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private CarService carService;
    
    private User loggedUser;
    
    @BeforeEach
    public void setup() {
        loggedUser = new User();
        loggedUser.setId(1L);
        loggedUser.setName("John Doe");
        loggedUser.setEmail("john@example.com");
        
        // Mocking the services
        Mockito.when(userService.findLoggedUser()).thenReturn(loggedUser);
        
        Appointment appointment = new Appointment();
        // Set appointment details if needed
        Mockito.when(appointmentService.getAppointmentsByUserEmail("john@example.com"))
                .thenReturn(Collections.singletonList(appointment));
        
        Car car = new Car();
        // Set car details if needed
        Mockito.when(carService.findCarsByUser(1L)).thenReturn(Collections.singletonList(car));
    }
    
    
    @Test
    public void testNonLoggedIndex() throws Exception {
        Mockito.when(userService.findLoggedUser()).thenReturn(null);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
    
    @Test
    @WithMockUser(username = "john@example.com")
    public void testAboutUs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home-about"));
    }
    
    @Test
    @WithMockUser(username = "john@example.com")
    public void testGallery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/gallery"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home-gallery"));
    }
    
    @Test
    @WithMockUser(username = "john@example.com")
    public void testServices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/services"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home-services"));
    }
    
    @Test
    @WithMockUser(username = "john@example.com")
    public void testContacts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home-contacts"));
    }
}
