package com.NikolaySHA.ExclusiveService.model.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddCarDataDTOTest {
    
    private AddCarDataDTO dto;
    
    @BeforeEach
    void setUp() {
        dto = new AddCarDataDTO();
    }
    @Test
    void testDefaultConstructor(){
        assertNull(dto.getColor());
        assertNull(dto.getLicensePlate());
        assertNull(dto.getMake());
        assertNull(dto.getModel());
        assertNull(dto.getVin());
    }
    @Test
    void testLicensePlateValidation() {
        dto.setLicensePlate("CB123");
        assertTrue(isValidLicensePlate(dto.getLicensePlate()));
        
        dto.setLicensePlate("CB");
        assertFalse(isValidLicensePlate(dto.getLicensePlate()));
        
        dto.setLicensePlate("CB12345678901");
        assertFalse(isValidLicensePlate(dto.getLicensePlate()));
        
        dto.setLicensePlate(null);
        assertFalse(isValidLicensePlate(dto.getLicensePlate()));
    }
    
    @Test
    void testMakeValidation() {
        dto.setMake("Audi");
        assertTrue(isValidMake(dto.getMake()));
        
        dto.setMake("A");
        assertFalse(isValidMake(dto.getMake()));
        
        dto.setMake("AudiAudiAudiAudiAudiAudiAudiAudiAudiAudiAudiAudi");
        assertFalse(isValidMake(dto.getMake()));
        
        dto.setMake(null);
        assertFalse(isValidMake(dto.getMake()));
    }
    
    @Test
    void testModelValidation() {
        dto.setModel("RS6");
        assertTrue(isValidModel(dto.getModel()));
        
        dto.setModel("A");
        assertFalse(isValidModel(dto.getModel()));
        
        dto.setModel("RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6RS6");
        assertFalse(isValidModel(dto.getModel()));
        
        dto.setModel(null);
        assertFalse(isValidModel(dto.getModel()));
    }
    
    @Test
    void testVinValidation() {
        dto.setVin("WAUZZZ4G8FN123456");
        assertTrue(isValidVin(dto.getVin()));
        
        dto.setVin("WAUZZZ4G8FN12345");
        assertFalse(isValidVin(dto.getVin()));
        
        dto.setVin("WAUZZZ4G8FN1234567");
        assertFalse(isValidVin(dto.getVin()));
        
        dto.setVin("");
        assertFalse(isValidVin(dto.getVin()));
        
        dto.setVin(null);
        assertTrue(isValidVin(dto.getVin()));
    }
    
    // Helper methods to simulate validation logic
    
    private boolean isValidLicensePlate(String licensePlate) {
        return licensePlate != null && licensePlate.length() >= 5 && licensePlate.length() <= 12;
    }
    
    private boolean isValidMake(String make) {
        return make != null && make.length() >= 2 && make.length() <= 30;
    }
    
    private boolean isValidModel(String model) {
        return model != null && model.length() >= 2 && model.length() <= 50;
    }
    
    private boolean isValidVin(String vin) {
        return vin == null || vin.length() == 17;
    }
}
