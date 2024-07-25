package com.NikolaySHA.ExclusiveService.web.controller;


import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.AddAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.EditAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.dto.appointmentDTO.ShowAppointmentDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appointments")
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
    @ModelAttribute("statuses")
    public List<Status> statuses(){
        return List.of(Status.values());
    }
    @ModelAttribute("carsData")
    public List<Car> cars(){
        return getCarList();
    }
    @ModelAttribute("users")
    public List<User> users(){
        return userService.findAll();
    }

    @GetMapping("/add")
    public String addAppointment(Model model, RedirectAttributes redirectAttributes) {
        List<Car> carsData = getCarList();
        if (carsData.isEmpty()){
            redirectAttributes.addFlashAttribute("showErrorMessage", true);
            return "redirect:/add-car";
        }
        model.addAttribute("isEdit", false);
        model.addAttribute("carsData", carsData);
        return "form-appointment";
    }
    
  
    
    @PostMapping("/add")
    public String doAddAppointment(
            @Valid AddAppointmentDTO data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes){
        if (userService.findLoggedUser() == null) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("appointmentData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.appointmentData", bindingResult);
            return "redirect:/appointments/add";
        }
        boolean success = appointmentService.create(data);
        
        if (!success) {
            redirectAttributes.addFlashAttribute("appointmentData", data);
            return "redirect:/appointments/add";
        }
        return "redirect:/home";
    }
    
    @GetMapping("/{id}")
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
        ShowAppointmentDTO data = modelMapper.map(appointment, ShowAppointmentDTO.class);
        model.addAttribute("showAppointmentData", data);
        return "view-appointment";
    }
    
    @GetMapping("/edit/{id}")
    @Transactional
    public String editAppointmentForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        
        model.addAttribute("isEdit", true);
        Optional<Appointment> appointmentOptional = appointmentService.findByIdInitializingUsersWithCars(id);
       
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
        EditAppointmentDTO appointmentDTO = modelMapper.map(appointment, EditAppointmentDTO.class);
        model.addAttribute("id", id);
        model.addAttribute("appointmentData", appointmentDTO);
        
        return "form-appointment";
    }
    
    @PostMapping("/edit/{id}")
    @Transactional
    public String updateAppointment(@PathVariable("id") Long id, @Valid EditAppointmentDTO appointment,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (!userService.loggedUserHasRole("ADMIN")){
            redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
            return "redirect:/error/contact-admin";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("appointmentData", appointment);
            model.addAttribute("org.springframework.validation.BindingResult.appointmentData", bindingResult);
            model.addAttribute("id", id);
            model.addAttribute("isEdit", true);
            return "form-appointment";
        }
        boolean success = appointmentService.updateAppointment(id, appointment);
        if (!success) {
            return "form-appointment";
        }
        return "redirect:/appointments/" + id;
    }
    private List<Car> getCarList() {
        List<Car> carsData;
        if (!userService.loggedUserHasRole("ADMIN")) {
            carsData = carService.findCarsByUser(userService.findLoggedUser().getId());
        } else {
            carsData = carService.findAllCars();
        }
        return carsData;
    }
    @PostMapping("/delete/{id}")
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
}
