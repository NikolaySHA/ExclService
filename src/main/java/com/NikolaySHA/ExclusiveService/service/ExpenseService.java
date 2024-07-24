package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    void createExpense(ExpenseDTO expenseOutDTO, Long id);
    
    void deleteExpense(Long id);
    
    ExpenseDTO getExpenseById(Long id);
    
    List<ExpenseDTO> getAllExpenses();
}
