//package com.NikolaySHA.ExclusiveService.web.controller;
//
//import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
//import com.NikolaySHA.ExclusiveService.model.entity.Car;
//import com.NikolaySHA.ExclusiveService.model.entity.User;
//import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
//import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
//import com.NikolaySHA.ExclusiveService.model.enums.Status;
//import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
//import com.NikolaySHA.ExclusiveService.service.AppointmentService;
//import com.NikolaySHA.ExclusiveService.service.CarService;
//import com.NikolaySHA.ExclusiveService.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//@WebMvcTest(AdminController.class)
//public class AdminControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private AppointmentService appointmentService;
//
//    @MockBean
//    private CarService carService;
//
//    @BeforeEach
//    public void setup() {
//        // Създайте и настройте обекти за тестовите данни
//        Appointment appointment = new Appointment();
//        appointment.setId(1L);
//        appointment.setDate(LocalDate.now());
//        appointment.setPaymentMethod(PaymentMethod.PRIVATE_ORDER);
//        appointment.setStatus(Status.COMPLETED);
//
//
//        Car car = new Car();
//        car.setId(1L);
//        car.setLicensePlate("ABC123");
//        car.setMake("Toyota");
//        car.setModel("Corolla");
//
//        User user = new User();
//        user.setId(1L);
//        user.setName("John Doe");
//        user.setEmail("john@example.com");
//        user.setRoles(List.of(new UserRole(UserRolesEnum.ADMIN)));
//        car.setOwner(user);
//
//        appointment.setCar(car);
//        appointment.setUser(user);
//
//        Mockito.when(appointmentService.getAllAppointments()).thenReturn(
//                Arrays.asList(appointment)
//        );
//
//        Mockito.when(carService.findAllCars()).thenReturn(
//                Arrays.asList(car)
//        );
//
//        Mockito.when(userService.findAllUsersWithRoles()).thenReturn(
//                Arrays.asList(user)
//        );
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testSearchAppointments() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/garage/appointments")
//                        .param("searchCriteria", "{}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("garage-appointments"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("appointmentsData"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("statuses"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("searchCriteria"));
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testSearchCars() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/garage/cars")
//                        .param("searchCriteria", "{}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("garage-cars"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("carsData"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("searchCriteria"));
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void testSearchUsers() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/garage/users")
//                        .param("searchCriteria", "{}"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.view().name("garage-users"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("usersData"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("userRoles"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("searchCriteria"));
//    }
//}
