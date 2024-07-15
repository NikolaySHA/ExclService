package com.ExclusiveService.controller;

import com.ExclusiveService.model.dto.LoginDTO;
import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.service.UserService;
import jakarta.validation.Valid;
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
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            model.addAttribute("registerData", data);
            return "register";
        }
        
        if (!data.getPassword().equals(data.getConfirmPassword())) {
            model.addAttribute("registerData", data);
            model.addAttribute("passwordMismatch", true);
            model.addAttribute("org.springframework.validation.BindingResult.registerData", bindingResult);
            return "register";
        }
        
        boolean success = userService.register(data);
        if (!success) {
            model.addAttribute("registerData", data);
            model.addAttribute("registrationFailed", true);
            return "register";
        }
        return "redirect:/login";
    }
    @GetMapping("/users/login")
    public String viewLogin() {
        return "login";
    }
    @GetMapping("/users/login-error")
    public ModelAndView viewLoginError(@ModelAttribute("loginData") LoginDTO loginDTO) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginData", loginDTO);
        modelAndView.addObject("showErrorMessage", true);
        return modelAndView;
    }

    
}
