package com.ExclusiveService.controller;

import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import com.ExclusiveService.util.ExclusiveUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    
    @GetMapping("/admin-panel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String loggedIn(@AuthenticationPrincipal ExclusiveUserDetails exclusiveUserDetails, Model model) {
        User loggedUser = userService.findLoggedUser();
            model.addAttribute("welcomeMessage", exclusiveUserDetails.getName());
            List<Appointment> appointments = appointmentService.getAllAppointments();
            model.addAttribute("appointmentsData",appointments);
            List<Car> cars = carService.findAllCars();
            model.addAttribute("carsData", cars);
            return "admin-panel";
    }
}
