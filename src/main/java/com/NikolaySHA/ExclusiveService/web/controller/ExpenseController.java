package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Appointment;
import com.NikolaySHA.ExclusiveService.service.AppointmentService;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final AppointmentService appointmentService;
    
    public ExpenseController(ExpenseService expenseService, AppointmentService appointmentService) {
        this.expenseService = expenseService;
        this.appointmentService = appointmentService;
    }
    @PostMapping()
    public String createNewExpense(ExpenseDTO dto){
        expenseService.createExpense(dto);
        Optional<Appointment> byId = appointmentService.findById(dto.getAppointmentId());
        if (byId.isPresent()) {
            List<Long> expensesIdList = byId.get().getExpensesList();
            expensesIdList.add(dto.getAppointmentId());
        }
        return "redirect:/appointments/" + dto.getAppointmentId();
    }
}
