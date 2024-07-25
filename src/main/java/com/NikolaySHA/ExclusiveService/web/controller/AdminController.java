package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.AppointmentSearchDTO;
import com.NikolaySHA.ExclusiveService.model.dto.carDTO.CarSearchDTO;
import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserSearchDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.UserService;
import com.NikolaySHA.ExclusiveService.aop.WarnIfExecutionExceeds;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final CarService carService;
    
    public AdminController(UserService userService, AppointmentService appointmentService, CarService carService) {
        this.userService = userService;
        this.appointmentService = appointmentService;
        this.carService = carService;
    }
    @GetMapping("/error/contact-admin")
    public String errorContactAdmin(){
        return "error-contact-admin";
    }
    @WarnIfExecutionExceeds(threshold = 1000)
    @GetMapping("/garage/appointments")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchAppointments(@AuthenticationPrincipal UserDetails userDetails, Model model,
                           @ModelAttribute("searchCriteria") AppointmentSearchDTO searchCriteria) {
        model.addAttribute("statuses", Status.values());
        List<Appointment> appointments;
        if (searchCriteria.getDate() != null || searchCriteria.getLicensePlate() != null || searchCriteria.getMake() != null || searchCriteria.getCustomer() != null || searchCriteria.getStatus() != null) {
            appointments = appointmentService.searchAppointments(searchCriteria.getDate(), searchCriteria.getLicensePlate(), searchCriteria.getMake(), searchCriteria.getCustomer(), searchCriteria.getStatus());
        } else {
            appointments = appointmentService.getAllAppointments();
        }
        model.addAttribute("appointmentsData", appointments);
        model.addAttribute("searchCriteria", searchCriteria);
        
        return "garage-appointments";
    }
    @WarnIfExecutionExceeds(threshold = 1000)
    @GetMapping("/garage/cars")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchAppointments(@AuthenticationPrincipal UserDetails userDetails, Model model,
                                     @ModelAttribute("searchCriteria") CarSearchDTO searchCriteria) {
        List<Car> cars;
        if (searchCriteria.getLicensePlate() != null || searchCriteria.getMake() != null || searchCriteria.getCustomer() != null) {
            cars = carService.searchCars(searchCriteria.getLicensePlate(), searchCriteria.getMake(), searchCriteria.getCustomer());
        } else {
            cars = carService.findAllCars();
        }
        
        model.addAttribute("carsData", cars);
        model.addAttribute("searchCriteria", searchCriteria);
        
        return "garage-cars";
    }
    @WarnIfExecutionExceeds(threshold = 1000)
    @GetMapping("/garage/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String searchCustomers(@AuthenticationPrincipal UserDetails userDetails, Model model,
                                     @ModelAttribute("searchCriteria") UserSearchDTO searchCriteria) {
        model.addAttribute("userRoles", UserRolesEnum.values());
        List<User> users;
        if (searchCriteria.getName() != null || searchCriteria.getEmail() != null || searchCriteria.getRole() != null) {
            users = userService.searchUsers(searchCriteria.getName(), searchCriteria.getEmail(), searchCriteria.getRole());
        } else {
            users = userService.findAllUsersWithRoles();
        }
        
        model.addAttribute("usersData", users);
        model.addAttribute("searchCriteria", searchCriteria);
        
        return "garage-users";
    }
   
}
