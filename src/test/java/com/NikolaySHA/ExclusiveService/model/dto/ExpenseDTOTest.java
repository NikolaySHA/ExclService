package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseDTOTest {
    @Test
    void testDefaultConstructor() {
        ExpenseDTO expenseOutDTO = new ExpenseDTO();
        Assertions.assertNull(expenseOutDTO.getName());
        Assertions.assertEquals (0.0, expenseOutDTO.getPrice());
    }
    
    @Test
    void testGettersAndSetters() {
        ExpenseDTO expenseOutDTO = new ExpenseDTO();
        expenseOutDTO.setName("Test Expense");
        expenseOutDTO.setPrice(100.0);
        
        Assertions.assertEquals("Test Expense", expenseOutDTO.getName());
        Assertions.assertEquals(100.0, expenseOutDTO.getPrice());
    }
}
