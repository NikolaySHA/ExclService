package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.AppointmentSearchDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.Status;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @GetMapping("/garage/appointments")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String searchAppointments(@AuthenticationPrincipal UserDetails userDetails, Model model,
                           @ModelAttribute("searchCriteria") AppointmentSearchDTO searchCriteria) {
        User loggedUser = userService.findLoggedUser();
        model.addAttribute("statuses", Status.values());
        model.addAttribute("welcomeMessage", userDetails.getUsername());
        
        List<Appointment> appointments;
        if (searchCriteria.getDate() != null || searchCriteria.getLicensePlate() != null || searchCriteria.getMake() != null || searchCriteria.getClient() != null || searchCriteria.getStatus() != null) {
            appointments = appointmentService.searchAppointments(searchCriteria.getDate(), searchCriteria.getLicensePlate(), searchCriteria.getMake(), searchCriteria.getClient(), searchCriteria.getStatus());
        } else {
            appointments = appointmentService.getAllAppointments();
        }
        
        model.addAttribute("appointmentsData", appointments);
        model.addAttribute("searchCriteria", searchCriteria);
        
        return "garage-appointments";
    }
}
