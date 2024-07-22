package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.UserService;
import com.NikolaySHA.ExclusiveService.util.ExclusiveUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
        if (userDetails instanceof ExclusiveUserDetails exclusiveUserDetails) {
            model.addAttribute("welcomeMessage", ((ExclusiveUserDetails) userDetails).getName());
            List<Appointment> appointmentsForCustomer = appointmentService.getAppointmentsByUserEmail(userDetails.getUsername());
            model.addAttribute("appointmentsData", appointmentsForCustomer);
            List<Car> myCars = carService.findCarsByUser(userService.findLoggedUser().getId());
            model.addAttribute("myCarsData", myCars);
            return "home";
        } else {
            return "index";
        }
    }
    
    @GetMapping("/about")
    public String aboutUs(){
        return "home-about";
    }
    @GetMapping("/gallery")
    public String gallery(){
        return "home-gallery";
    }
    @GetMapping("/services")
    public String services(){
        return "home-services";
    }
    @GetMapping("/contacts")
    public String contacts(){
        return "home-contacts";
    }
}
