package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseDTOTest {
    @Test
    void testDefaultConstructor() {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        Assertions.assertNull(expenseDTO.getName());
        Assertions.assertEquals (0.0, expenseDTO.getPrice());
    }
    
    @Test
    void testGettersAndSetters() {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setName("Test Expense");
        expenseDTO.setPrice(100.0);
        
        Assertions.assertEquals("Test Expense", expenseDTO.getName());
        Assertions.assertEquals(100.0, expenseDTO.getPrice());
    }
}
