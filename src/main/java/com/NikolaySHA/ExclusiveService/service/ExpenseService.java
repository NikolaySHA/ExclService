package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseInDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ExpenseOutDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseService {
    void createExpense(ExpenseOutDTO expenseOutDTO);
    void deleteExpense(Long id);
    ExpenseInDTO getExpenseById(Long id);
    List<ExpenseInDTO> GetExpensesForAppointment(Long id);
    public double getTotalExpensesForAppointment(Long appointmentId);
}
