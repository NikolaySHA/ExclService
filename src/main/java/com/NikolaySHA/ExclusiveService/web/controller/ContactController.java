package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.service.impl.EmailSenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    
    private final EmailSenderService emailSenderService;
    
    public ContactController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }
    
    @GetMapping("/")
    public String contacts(){
        return "home-contacts";
    }
    
    @PostMapping("/")
    public String sendEmail(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam String subject,
            @RequestParam String message,
            Model model) {
        
        
        String to = "exclautoservice@gmail.com";
        String emailSubject = "Ново запитване: " + subject;
        String emailText = String.format(
                "Име: %s\nEmail: %s\nТелефон: %s\nТема: %s\nСъобщение: %s",
                name, email, phone, subject, message
        );
        
        emailSenderService.sendSimpleEmail(to, emailSubject, emailText);
        
        model.addAttribute("successContactMessage", true);
        
       
        return "home-contacts";
    }
}
