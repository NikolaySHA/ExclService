package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.ExpenseInDTO;
import com.NikolaySHA.ExclusiveService.model.dto.ExpenseOutDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {
    @Mock
    private RestClient restClient;
    
    @InjectMocks
    private ExpenseServiceImpl expenseService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateExpense() {
        ExpenseOutDTO expenseOutDTO = new ExpenseOutDTO();
        // Симулирайте POST заявка
        when(restClient.post().uri(anyString()).body(any()).retrieve()).thenReturn(mock());
        
        expenseService.createExpense(expenseOutDTO);
        
        // Проверете дали POST заявката е направена с очакваните стойности
        verify(restClient.post()).uri("/expenses");
    }
    
    @Test
    void testDeleteExpense() {
        Long expenseId = 1L;
        //DELETE заявка
        when(restClient.delete().uri(anyString(), anyLong()).retrieve().toBodilessEntity()).thenReturn(ResponseEntity.noContent().build());
        
        expenseService.deleteExpense(expenseId);
        
        verify(restClient.delete()).uri("/expenses/{id}", expenseId);
    }
    
    @Test
    void testGetExpenseById() {
        Long expenseId = 1L;
        ExpenseInDTO mockExpense = new ExpenseInDTO();
        mockExpense.setId(expenseId);
        when(restClient.get().uri(anyString(), anyLong()).accept(any()).retrieve().body(ExpenseInDTO.class)).thenReturn(mockExpense);
        
        ExpenseInDTO result = expenseService.getExpenseById(expenseId);
        
        assertNotNull(result);
        assertEquals(expenseId, result.getId());
        verify(restClient.get()).uri("/expenses/{id}", expenseId);
    }
    
    @Test
    void testGetExpensesForAppointment() {
        Long appointmentId = 1L;
        ExpenseInDTO expense = new ExpenseInDTO();
        expense.setPrice(100.0);
        List<ExpenseInDTO> mockExpenses = Collections.singletonList(expense);
        
        when(restClient.get()
                .uri("/expenses/appointment/{id}", appointmentId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ExpenseInDTO>>() {}))
                .thenReturn(mockExpenses);
        
        List<ExpenseInDTO> result = expenseService.GetExpensesForAppointment(appointmentId);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getPrice());
    }
    
    @Test
    void testGetTotalExpensesForAppointment() {
        Long appointmentId = 1L;
        ExpenseInDTO expense1 = new ExpenseInDTO();
        expense1.setPrice(100.0);
        ExpenseInDTO expense2 = new ExpenseInDTO();
        expense2.setPrice(200.0);
        List<ExpenseInDTO> mockExpenses = List.of(expense1, expense2);
        when(restClient.get().uri(anyString(), anyLong()).accept(any()).retrieve().body(new ParameterizedTypeReference<List<ExpenseInDTO>>() {})).thenReturn(mockExpenses);
        
        double total = expenseService.getTotalExpensesForAppointment(appointmentId);
        
        assertEquals(300.0, total, 0.01);
    }
}
