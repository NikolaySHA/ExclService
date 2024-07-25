package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseDTO;
import com.NikolaySHA.ExclusiveService.service.ExpenseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final RestClient expense;
    
    public ExpenseServiceImpl( @Qualifier("expensesRestClient") RestClient expense) {
        this.expense = expense;
    }
    
    @Override
    public void createExpense(ExpenseDTO expenseOutDTO) {
        expense
                .post()
                .uri("/expenses")
                .body(expenseOutDTO)
                .retrieve();
    }
    
    @Override
    public void deleteExpense(Long id) {
    
    }
    
    @Override
    public ExpenseDTO getExpenseById(Long id) {
        return expense
                .get()
                .uri("/expenses/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExpenseDTO.class);
    }
    
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return expense
                .get()
                .uri("/expenses")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }
}
