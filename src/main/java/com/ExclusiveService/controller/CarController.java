package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.AddCarDataDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CarController {
    
    private final UserService userService;
    private final CarService carService;
    private final AppointmentService appointmentService;
    
    public CarController(UserService userService, CarService carService, AppointmentService appointmentService) {
        this.userService = userService;
        this.carService = carService;
        this.appointmentService = appointmentService;
    }
    
    @ModelAttribute("addCarData")
    public AddCarDataDTO addCarDataDTO(){
        return new AddCarDataDTO();
    }
    
    
    
    @GetMapping("/add-car")
    public String addCar(){
        return "car-add";
    }
    
    @PostMapping("/add-car")
    public String doAddCar(@Valid AddCarDataDTO data, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if (userService.findLoggedUser() == null) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCarData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCarData", bindingResult);
            
            return "redirect:/add-car";
        }
        boolean success = carService.addCar(data);
        if (!success) {
            redirectAttributes.addFlashAttribute("addCarData", data);
            redirectAttributes.addFlashAttribute("showErrorMessageExistingCar", true);
            return "redirect:/add-car";
        }
        return "redirect:/";
    }
    @PostMapping("/delete-car/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        // Find the car by id
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            List<Appointment> appointments = appointmentService.getAppointmentsForCar(userService.findLoggedUser().getEmail(), car.getLicensePlate());
            if (!appointments.isEmpty()) {
                return "redirect:/error-contactAdmin";
            }
            carService.delete(car);
//            TODO: send message to owner
            return "redirect:/";
        } else {
            // Handle case where car with given id is not found
            // Redirect to an error page or handle accordingly
            return "redirect:/error-contactAdmin";
        }
    }
    @GetMapping("/error-contactAdmin")
    public String errorContactAdmin(){
        return "error-contact-admin";
    }
  
}
