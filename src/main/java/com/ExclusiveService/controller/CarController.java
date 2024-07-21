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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;
    
    public CarController(UserService userService, CarService carService, AppointmentService appointmentService, ModelMapper modelMapper) {
        this.userService = userService;
        this.carService = carService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }
    
    @ModelAttribute("addCarData")
    public AddCarDataDTO addCarDataDTO(){
        return new AddCarDataDTO();
    }
    @ModelAttribute("carData")
    public ShowCarDTO showCarDTO(){
        return new ShowCarDTO();
    }
    @ModelAttribute("editCarData")
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
        if (carOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("noSuchCarErrorMessage", true);
            return "redirect:/error/contact-admin";
        } else {
            Car car = carOptional.get();
            List<Appointment> appointments = car.getAppointments();
            if (!appointments.isEmpty()) {
                redirectAttributes.addFlashAttribute("deleteCarErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
            carService.delete(car);
//            TODO: send message to owner
            return "redirect:/";
        }
    }
    @GetMapping("/error/contact-admin")
    public String errorContactAdmin(){
        return "error-contact-admin";
    }
    @GetMapping("/cars/{id}")
    public String viewCar(@PathVariable("id") Long id, ShowCarDTO data, RedirectAttributes redirectAttributes, Model model) {
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        Car car = carOptional.get();
        if (!car.getOwner().equals(userService.findLoggedUser())){
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
    public String editCarForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        User loggedUser = userService.findLoggedUser();
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        Car car = carOptional.get();
        if (!car.getOwner().equals(loggedUser)) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        EditCarDTO editCarDTO = modelMapper.map(car, EditCarDTO.class);
        model.addAttribute("editCarData", editCarDTO);
        return "edit-car";
    }
    
    @PostMapping("/cars/edit/{id}")
    @Transactional
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
            redirectAttributes.addFlashAttribute("editCarData", car);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editCarData", bindingResult);
            return "redirect:/cars/edit/" + id;
        }
        
        boolean success = carService.updateCar(id, car);
        if (!success) {
            redirectAttributes.addFlashAttribute("editCarData", car);
            return "redirect:/cars/edit/" + id;
        }
        return "redirect:/cars/" + id;
    }
}
