package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.*;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @ModelAttribute("carData")
    public ShowCarDTO showCarDTO(){
        return new ShowCarDTO();
    }
    @ModelAttribute("editData")
    public EditCarDTO editCarDTO(){
        return new EditCarDTO();
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
    @PostMapping("/cars/delete/{id}")
    public String deleteCar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            List<Appointment> appointments = car.getAppointments();
            appointments.stream().count();
            if (!appointments.isEmpty()) {
                redirectAttributes.addFlashAttribute("deleteCarErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
            carService.delete(car);
//            TODO: send message to owner
            return "redirect:/";
        } else {
            // Handle case where car with given id is not found
            // Redirect to an error page or handle accordingly
            redirectAttributes.addFlashAttribute("noSuchCarErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
    }
    @GetMapping("/error/contact-admin")
    public String errorContactAdmin(){
        return "error-contact-admin";
    }
    @GetMapping("/cars/{id}")
    @Transactional
    public String getCarById(@PathVariable("id") Long id, ShowCarDTO data, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.findLoggedUser();
        Car car = carService.findById(id).get();
        if (!car.getOwner().equals(user)){
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        data.setLicensePlate(car.getLicensePlate());
        data.setMake(car.getMake());
        data.setModel(car.getModel());
        data.setVin(car.getVin());
        data.setColor(car.getColor());
        data.setAppointments(car.getAppointments());
        data.setOwner(car.getOwner());
        model.addAttribute("carData", data);
        return "car";
    }
    
    @GetMapping("/cars/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        User loggedUser = userService.findLoggedUser();
        Car car = carService.findById(id).get();
        if (!car.getOwner().equals(loggedUser)) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:x/error/contact-admin";
            }
        }
        model.addAttribute("carData", car);
        return "edit-car";
    }
    
    @PostMapping("/cars/edit/{id}")
    public String updateCar(@PathVariable("id") Long id,@Valid EditCarDTO car,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User loggedUser = userService.findLoggedUser();
        if (!loggedUser.getId().equals(id)) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editData", car);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editData", bindingResult);
            redirectAttributes.addFlashAttribute("id", id);
            return "redirect:/cars/edit/" + id;
        }
        
        carService.updateCar(id, car);
        return "redirect:/cars/" + id;
    }
}
