package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseInDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ExpenseOutDTO;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    
    public ExpenseController(ExpenseService expenseService, AppointmentService appointmentService) {
        this.expenseService = expenseService;
    }
    @PostMapping("/")
    public String createNewExpense(ExpenseOutDTO dto){
        expenseService.createExpense(dto);

        return "redirect:/appointments/" + dto.getAppointmentId();
    }
//    @PostMapping("/expenses/{id}")
//    public String GetExpensesForAppointment(@PathVariable Long id){
//        expenseService.GetExpensesForAppointment(id);
//
//        return "redirect:/appointments/" + id;
//    }
    @PostMapping("/{id}")
    public String deleteExpense(@PathVariable Long id, @RequestParam Long appointmentId) {
        expenseService.deleteExpense(id);
        return "redirect:/appointments/" + appointmentId;
    }
}
