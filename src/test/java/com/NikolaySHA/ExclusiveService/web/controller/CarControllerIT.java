package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.carDTO.CarDataDTO;
import com.NikolaySHA.ExclusiveService.model.dto.carDTO.CarViewDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIT {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private CarService carService;
    
    @MockBean
    private AppointmentService appointmentService;
    
    @MockBean
    private ModelMapper modelMapper;
    
    private User loggedUser;
    private Car car;
    
    @BeforeEach
    public void setup() {
        loggedUser = new User();
        loggedUser.setId(1L);
        loggedUser.setName("John Doe");
        loggedUser.setEmail("john@example.com");
        
        car = new Car();
        car.setId(1L);
        car.setLicensePlate("ABC123");
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setOwner(loggedUser);
        
        Mockito.when(userService.findLoggedUser()).thenReturn(loggedUser);
        Mockito.when(userService.loggedUserHasRole("ADMIN")).thenReturn(true);
        
        Mockito.when(carService.findById(1L)).thenReturn(Optional.of(car));
        Mockito.when(carService.findById(2L)).thenReturn(Optional.empty());
        
        CarDataDTO carDataDTO = new CarDataDTO();
        carDataDTO.setLicensePlate("ABC123");
        carDataDTO.setMake("Toyota");
        carDataDTO.setModel("Corolla");
        
        Mockito.when(modelMapper.map(car, CarViewDTO.class)).thenReturn(new CarViewDTO());
        Mockito.when(modelMapper.map(car, CarDataDTO.class)).thenReturn(carDataDTO);
    }
    
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testViewCarNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/error/contact-admin"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("notFoundErrorMessage"));
    }
    
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAddCarForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form-car"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("isEdit"));
    }
    
    
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testEditCarForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form-car"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("carData"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("id"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("isEdit"));
    }
    
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testEditCarFormNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/edit/2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/error/contact-admin"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("notFoundErrorMessage"));
    }
    
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUpdateCar() throws Exception {
        CarDataDTO carDataDTO = new CarDataDTO();
        carDataDTO.setLicensePlate("XYZ789");
        carDataDTO.setMake("Honda");
        carDataDTO.setModel("Civic");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/edit/1")
                        .flashAttr("carData", carDataDTO)
                        .with(csrf()))  // добавяне на CSRF токен
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cars/1"));
    }
}
