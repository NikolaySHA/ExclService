package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.LoginDTO;
import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.dto.ShowUserDTO;
import com.ExclusiveService.model.entity.Appointment;
import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final CarService carService;
    private final ModelMapper modelMapper;
    
    
    public UserController(UserService userService, AppointmentService appointmentService, CarService carService, ModelMapper modelMapper) {
        this.userService = userService;
        
        this.appointmentService = appointmentService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }
    
    @ModelAttribute("registerData")
    public RegisterDTO registerDTO(){
        return new RegisterDTO();
    }
    @ModelAttribute("loginData")
    public LoginDTO loginDTO(){
        return new LoginDTO();
    }
    @ModelAttribute("userData")
    public ShowUserDTO showUserDTO(){
        return new ShowUserDTO();
    }
    
    
    
    @GetMapping("/users/register")
    public String register(){
        return "register";
    }
    
    @PostMapping("/users/register")
    public String doRegister(@Valid RegisterDTO data,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", data);
            return "redirect:/users/register";
        }
        
        if (!data.getPassword().equals(data.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/users/register";
        }
        
        boolean success = userService.register(data);
        if (!success) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("registrationFailed", true);
            return "redirect:/users/register";
        }
        return "redirect:/users/login";
    }
    @GetMapping("/users/login")
    public String viewLogin() {
        if (userService.findLoggedUser() != null) {
            return "redirect:/home";
        }
        return "login";
    }
    @GetMapping("/users/login-error")
    public String viewLoginError(@Valid LoginDTO data,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("loginData", data);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
        redirectAttributes.addFlashAttribute("showErrorMessage", true);
        return "redirect:/users/login";
    }
    @GetMapping("users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUserById(@PathVariable("id") Long id, ShowUserDTO data, Model model) {
        User user = userService.getUserById(id);
        data.setName(user.getName());
        data.setEmail(user.getEmail());
        data.setPhoneNumber(user.getPhoneNumber());
        data.setCars(carService.findCarsByUser(id));
        data.setAppointments(appointmentService.getAppointments(userService.getUserById(id).getEmail()));
        model.addAttribute("userData", data);
        return "user";
    }
    @GetMapping("/users/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }
    
    @PostMapping("/users/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") @Valid RegisterDTO user,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/users/edit/" + id;
        }
        
        userService.updateUser(id, user);
        return "redirect:/users/" + id;
    }
    
}
