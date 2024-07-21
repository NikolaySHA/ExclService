package com.ExclusiveService.model.dto;


import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.PaymentMethod;
import com.ExclusiveService.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ShowAppointmentDTO {
    private LocalDate date;
    private User user;
    private Car car;
    private int paintDetails;
    private PaymentMethod paymentMethod;
    private Status status;
    private String comment;
    
}