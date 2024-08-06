package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvailableDateServiceImplTest {
    
    @Mock
    private AppointmentRepository appointmentRepository;
    
    @InjectMocks
    private AvailableDateServiceImpl availableDateService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCalculateNextAvailableDate() {
        // Arrange
        int detailsCount = 5;
        LocalDate today = LocalDate.now();
        
        // Mock current week load as 30 (exactly on the limit) to force the method to move to the next week
        when(appointmentRepository.findTotalDetailsForWeek(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.FRIDAY), Status.COMPLETED))
                .thenReturn(30);
        
        // Mock next week load as 0 (free week) to satisfy the condition
        LocalDate nextMonday = today.plusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate nextFriday = today.plusWeeks(1).with(DayOfWeek.FRIDAY);
        when(appointmentRepository.findTotalDetailsForWeek(nextMonday, nextFriday, Status.COMPLETED))
                .thenReturn(0);
        
        // Act
        LocalDate result = availableDateService.calculateNextAvailableDate(detailsCount);
        
        // Assert
        assertNotNull(result);
        // The expected date should be the first available day of the next week (which is a Monday)
        assertEquals(nextMonday, result);
    }
    
    @Test
    void testGetCurrentWeekLoad() {
        // Arrange
        LocalDate date = LocalDate.of(2024, 7, 22); // Example date, assuming it's Monday
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.FRIDAY);
        int expectedLoad = 25;
        when(appointmentRepository.findTotalDetailsForWeek(startOfWeek, endOfWeek, Status.COMPLETED))
                .thenReturn(expectedLoad);
        
        // Act
        int load = availableDateService.getCurrentWeekLoad(date);
        
        // Assert
        assertEquals(expectedLoad, load);
    }
}
