package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.enums.Status;
import com.NikolaySHA.ExclusiveService.repo.AppointmentRepository;
import com.NikolaySHA.ExclusiveService.service.AvailableDateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@AllArgsConstructor
public class AvailableDateServiceImpl implements AvailableDateService {
    private final AppointmentRepository appointmentRepository;
    
    @Override
    public LocalDate calculateNextAvailableDate(int detailsCount) {
        LocalDate tomorowDate = LocalDate.now().plusDays(1);
        int weeklyLimit = 30;
        
        while (true) {
            if (tomorowDate.getDayOfWeek() == DayOfWeek.SATURDAY || tomorowDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                tomorowDate = tomorowDate.plusDays(1);
                continue;
            }
            
            int currentWeekLoad = getCurrentWeekLoad(tomorowDate);
            
            if (currentWeekLoad + detailsCount <= weeklyLimit) {
                return tomorowDate;
            } else {
                tomorowDate = tomorowDate.plusDays(1);
            }
        }
    }
    
    @Override
    public int getCurrentWeekLoad(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        
        return appointmentRepository.findTotalDetailsForWeek(startOfWeek, endOfWeek, Status.COMPLETED);
    }
}
