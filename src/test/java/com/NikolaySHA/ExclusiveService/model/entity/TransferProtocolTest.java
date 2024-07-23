package com.NikolaySHA.ExclusiveService.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransferProtocolTest {
    
    private TransferProtocol transferProtocol;
    
    @BeforeEach
    void setUp() {
        transferProtocol = new TransferProtocol();
        transferProtocol.setId(1L);
        transferProtocol.setDate(LocalDate.of(2024, 1, 15));
        transferProtocol.setCustomerName("Mad Max");
        transferProtocol.setLicensePlate("CB6666BC");
        transferProtocol.setMake("Audi");
        transferProtocol.setModel("RS6");
        transferProtocol.setFinished(true);
    }
    
    @Test
    void testProtocolCreation() {
        assertNotNull(transferProtocol);
        assertEquals(1L, transferProtocol.getId());
        assertEquals(LocalDate.of(2024, 1, 15), transferProtocol.getDate());
        assertEquals("Mad Max", transferProtocol.getCustomerName());
        assertEquals("CB6666BC", transferProtocol.getLicensePlate());
        assertEquals("Audi", transferProtocol.getMake());
        assertEquals("RS6", transferProtocol.getModel());
        assertTrue(transferProtocol.isFinished());
    }
    
   
}
