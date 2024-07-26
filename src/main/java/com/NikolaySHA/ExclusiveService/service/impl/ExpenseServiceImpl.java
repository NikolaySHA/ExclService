package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseInDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ExpenseOutDTO;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final RestClient expense;
    
    public ExpenseServiceImpl( @Qualifier("expensesRestClient") RestClient expense) {
        this.expense = expense;
    }
    
    @Override
    public void createExpense(ExpenseOutDTO expenseOutDTO) {
        expense
                .post()
                .uri("/expenses")
                .body(expenseOutDTO)
                .retrieve();
    }
    
    @Override
    public void deleteExpense(Long id) {
        expense
                .delete()
                .uri("/expenses/{id}", id)
                .retrieve()
                .toBodilessEntity();
            
    }
    
    @Override
    public ExpenseInDTO getExpenseById(Long id) {
        return expense
                .get()
                .uri("/expenses/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExpenseInDTO.class);
    }
    @Override
    public List<ExpenseInDTO> GetExpensesForAppointment(Long id) {
        return expense
                .get()
                .uri("/expenses/appointment/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
    
    @Override
    public double getTotalExpensesForAppointment(Long appointmentId) {
        List<ExpenseInDTO> expenses = this.GetExpensesForAppointment(appointmentId);
        double total = 0.00;
        for (ExpenseInDTO expense : expenses) {
            total += expense.getPrice();
        }
        return total;
    }
}
