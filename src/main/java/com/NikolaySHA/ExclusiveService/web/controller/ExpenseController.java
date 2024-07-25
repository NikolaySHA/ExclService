package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseDTO;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    
    public ExpenseController(ExpenseService expenseService, AppointmentService appointmentService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/")
    public String createNewExpense(ExpenseDTO dto){
        expenseService.createExpense(dto);

        return "redirect:/appointments/" + dto.getAppointmentId();
    }
   
}
