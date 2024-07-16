package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.LoginDTO;
import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    
    private final UserService userService;
  
    
    public UserController(UserService userService) {
        this.userService = userService;
        
    }
    
    @ModelAttribute("registerData")
    public RegisterDTO registerDTO(){
        return new RegisterDTO();
    }
    @ModelAttribute("loginData")
    public LoginDTO loginDTO(){
        return new LoginDTO();
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
                                 BindingResult bindingResult, Model model) {
        model.addAttribute("loginData", data);
        model.addAttribute("org.springframework.validation.BindingResult.loginData", bindingResult);
        model.addAttribute("showErrorMessage", true);
        return "redirect:/users/login";
    }

    
}
