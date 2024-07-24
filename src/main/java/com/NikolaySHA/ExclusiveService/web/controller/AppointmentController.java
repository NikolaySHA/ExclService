package com.NikolaySHA.ExclusiveService.web.controller;


import com.NikolaySHA.ExclusiveService.model.dto.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ShowAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.CarService;
import com.NikolaySHA.ExclusiveService.service.UserService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppointmentController {
    
    private final AppointmentService appointmentService;
    private final CarService carService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    public AppointmentController(AppointmentService appointmentService, CarService carService, UserService userService, ModelMapper modelMapper) {
        this.appointmentService = appointmentService;
        this.carService = carService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }
    
    @ModelAttribute("appointmentData")
    public AddAppointmentDTO addAppointmentDTO(){
        return new AddAppointmentDTO();
    }
//    @ModelAttribute("editAppointmentData")
//    public EditAppointmentDTO editAppointmentDTO(){ return new EditAppointmentDTO(); }
    
    
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
    @PostMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        
        Optional<Appointment> appointmentOptional = appointmentService.findById(id);
        if (appointmentOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        } else {
            Appointment appointment = appointmentOptional.get();
            appointmentService.delete(appointment);
//          TODO: send message to owner
            return "redirect:/";
        }
    }
    @GetMapping("/appointments/{id}")
    public String viewAppointment(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        Optional<Appointment> optional = appointmentService.findById(id);
        if (optional.isEmpty()){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        Appointment appointment = optional.get();
        if (!appointment.getUser().equals(userService.findLoggedUser())){
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        ShowAppointmentDTO data = new ShowAppointmentDTO();
        data.setUser(appointment.getUser());
        data.setCar(appointment.getCar());
        data.setDate(appointment.getDate());
        data.setComment(appointment.getComment());
        data.setPaintDetails(appointment.getPaintDetails());
        data.setPaymentMethod(appointment.getPaymentMethod());
        model.addAttribute("showAppointmentData", data);
        return "appointment";
    }
    
    @GetMapping("/appointments/edit/{id}")
    public String editAppointmentForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        
        Optional<Appointment> appointmentOptional = appointmentService.findByIdInitializingUsersWithCars(id);
        model.addAttribute("statuses", Status.values());
        if (appointmentOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        Appointment appointment = appointmentOptional.get();
        if (!appointment.getUser().equals(userService.findLoggedUser())) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        EditAppointmentDTO editAppointmentDTO = modelMapper.map(appointment, EditAppointmentDTO.class);
        
        if (!model.containsAttribute("editAppointmentData")) {
            model.addAttribute("editAppointmentData", editAppointmentDTO);
        }
        return "edit-appointment";
    }
    
    @PostMapping("/appointments/edit/{id}")
    @Transactional
    public String updateAppointment(@PathVariable("id") Long id, @Valid EditAppointmentDTO appointment,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!userService.loggedUserHasRole("ADMIN")){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editAppointmentData", appointment);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editAppointmentData", bindingResult);
            return "redirect:/appointments/edit/" + id;
        }
        boolean success = appointmentService.updateAppointment(id, appointment);
        if (!success) {
            return "edit-appointment";
        }
        return "redirect:/appointments/" + id;
    }
}
