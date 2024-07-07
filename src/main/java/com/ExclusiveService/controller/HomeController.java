package com.ExclusiveService.controller;

import com.ExclusiveService.util.UserDetails;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    
    private final AppointmentService appointmentService;
    private final UserService userService;
    private final CarService carService;
    
    public HomeController(AppointmentService appointmentService, UserService userService, CarService carService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.carService = carService;
    }
    @GetMapping("/")
    public String nonLoggedIndex(){
        User loggedUser = userService.findLoggedUser();
        if (loggedUser != null) {
            return "redirect:/home";
        }
        return "index";
    }
    
    @GetMapping("/home")
    public String loggedIn(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User loggedUser = userService.findLoggedUser();
        if (loggedUser != null) {
            model.addAttribute("welcomeMessage", userDetails.getName());
            List<Appointment> appointmentsForCustomer = appointmentService.getAppointments(userDetails.getUsername());
            model.addAttribute("appointmentsData", appointmentsForCustomer);
            List<Car> myCars = carService.findCarsByUser();
            model.addAttribute("myCarsData", myCars);
            return "home";
        } else {
            return "index";
        }
    }
    
    @GetMapping("/gallery")
    public String gallery(){
        return "gallery";
    }
}
