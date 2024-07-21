package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.EditUserDTO;
import com.ExclusiveService.model.dto.LoginDTO;
import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.dto.ShowUserDTO;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.service.AppointmentService;
import com.ExclusiveService.service.CarService;
import com.ExclusiveService.service.UserService;
import com.ExclusiveService.web.aop.WarnIfExecutionExceeds;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @ModelAttribute("editData")
    public EditUserDTO editUserDTO(){
        return new EditUserDTO();
    }
    
    
    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @WarnIfExecutionExceeds(threshold = 1500)
    @PostMapping("/register")
    public String doRegister(@Valid RegisterDTO data,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws InterruptedException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            redirectAttributes.addFlashAttribute("registerData", data);
            return "redirect:/register";
        }
        
        if (!data.getPassword().equals(data.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "redirect:/register";
        }
        
        boolean success = userService.register(data);
        if (!success) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute("registrationFailed", true);
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("successfulRegistration", true);
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
    @GetMapping("/users")
    public String transitPoint() {
        return "redirect:/users/" + userService.findLoggedUser().getId();
    }
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") Long id, ShowUserDTO data, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.getUserById(id);
        if (!userService.findLoggedUser().getId().equals(id)){
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        data.setName(user.getName());
        data.setEmail(user.getEmail());
        data.setPhoneNumber(user.getPhoneNumber());
        data.setCars(carService.findCarsByUser(id));
        data.setAppointments(appointmentService.getAppointments(userService.getUserById(id).getEmail()));
        model.addAttribute("userData", data);
        return "user";
    }
    
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        User loggedUser = userService.findLoggedUser();
        User user = userService.getUserById(id);
        if (!loggedUser.getId().equals(id)) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        model.addAttribute("editData", user);
        return "edit-user";
    }
    
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,@Valid EditUserDTO user,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User loggedUser = userService.findLoggedUser();
        if (!loggedUser.getId().equals(id)) {
            if (!userService.loggedUserHasRole("ADMIN")){
                redirectAttributes.addFlashAttribute("notFoundErrorMessage", true);
                return "redirect:/error/contact-admin";
            }
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editData", user);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editData", bindingResult);
            redirectAttributes.addFlashAttribute("id", id);
            return "redirect:/users/edit/" + id;
        }
        
        userService.updateUser(id, user);
        return "redirect:/users/" + id;
    }
    
}
