package com.NikolaySHA.ExclusiveService.service;

import java.time.LocalDate;

public interface AvailableDateService {
    
    LocalDate calculateNextAvailableDate(int detailsCount);
    
    int getCurrentWeekLoad(LocalDate date);
}