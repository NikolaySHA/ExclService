package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.service.AvailableDateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

@Controller
public class AvailableDateController {
    private final AvailableDateService availableDateService;
    
    public AvailableDateController(AvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }
    
    @GetMapping("/next-available-date")
    @ResponseBody
    public LocalDate getNextAvailableDate(@RequestParam int detailsCount) {
        LocalDate nextAvailableDate = availableDateService.calculateNextAvailableDate(detailsCount);
        return nextAvailableDate;
    }
}
