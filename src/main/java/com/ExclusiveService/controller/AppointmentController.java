package com.ExclusiveService.controller;


import com.ExclusiveService.model.dto.AddAppointmentDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {
    
    private final AppointmentService appointmentService;
    private final CarService carService;
    private final UserService userService;
    public AppointmentController(AppointmentService appointmentService, CarService carService, UserService userService) {
        this.appointmentService = appointmentService;
        this.carService = carService;
        this.userService = userService;
    }
    
    @ModelAttribute("appointmentData")
    public AddAppointmentDTO addAppointmentDTO(){
        return new AddAppointmentDTO();
    }
    @ModelAttribute("myCarsData")
    public List<Car> myCarsData(){
        return new ArrayList<Car>();
    }
    
    @GetMapping("/add-appointment")
    public String addAppointment(Model model, RedirectAttributes redirectAttributes) {
        List<Car> carsData = new ArrayList<>();
        if (!userService.loggedUserHasRole("ADMIN")) {
            carsData = carService.findCarsByUser(userService.findLoggedUser().getId());
        } else {
            carsData = carService.findAllCars();
        }
        if (carsData.isEmpty()){
            redirectAttributes.addFlashAttribute("showErrorMessage", true);
            return "redirect:/add-car";
        }
        model.addAttribute("carsData", carsData);
        return "appointment-add";
    }
    
    @PostMapping("/add-appointment")
    public String doAddAppointment(
            @Valid AddAppointmentDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("appointmentData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentData", bindingResult);
            
            return "redirect:/add-appointment";
        }
        boolean success = appointmentService.create(data);
        
        if (!success) {
            redirectAttributes.addFlashAttribute("appointmentData", data);
            return "redirect:/add-appointment";
        }
        return "redirect:/home";
    }
    @PostMapping("/delete-appointment/{id}")
    public String deleteAppointment(@PathVariable("id") Long id) {
        
        Optional<Appointment> appointmentOptional = appointmentService.findById(id);
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointmentService.delete(appointment);
//          TODO: send message to owner
            return "redirect:/";
        } else {
            // Handle case where car with given id is not found
            // Redirect to an error page or handle accordingly
            return "redirect:/error-contactAdmin";
        }
    }
}
