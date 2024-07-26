package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpenseInDTOTest {
    
    @Test
    public void testExpenseInDTO() {
        // Create an instance of ExpenseInDTO
        ExpenseInDTO expense = new ExpenseInDTO();
        
        // Set values
        expense.setId(1L);
        expense.setName("Test Expense");
        expense.setPrice(99.99);
        expense.setAppointmentId(100L);
        
        // Assert values
        assertNotNull(expense);
        assertEquals(1L, expense.getId());
        assertEquals("Test Expense", expense.getName());
        assertEquals(99.99, expense.getPrice());
        assertEquals(100L, expense.getAppointmentId());
    }
}
