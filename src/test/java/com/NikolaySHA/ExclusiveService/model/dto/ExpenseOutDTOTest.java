package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpenseOutDTOTest {
    @Test
    void testDefaultConstructor() {
        ExpenseOutDTO expenseOutDTO = new ExpenseOutDTO();
        Assertions.assertNull(expenseOutDTO.getName());
        Assertions.assertEquals (0.0, expenseOutDTO.getPrice());
    }
    
    @Test
    void testGettersAndSetters() {
        ExpenseOutDTO expenseOutDTO = new ExpenseOutDTO();
        expenseOutDTO.setName("Test Expense");
        expenseOutDTO.setPrice(100.0);
        expenseOutDTO.setAppointmentId(1);
        
        Assertions.assertEquals("Test Expense", expenseOutDTO.getName());
        Assertions.assertEquals(100.0, expenseOutDTO.getPrice());
        Assertions.assertEquals(1, expenseOutDTO.getAppointmentId());
    }
}
