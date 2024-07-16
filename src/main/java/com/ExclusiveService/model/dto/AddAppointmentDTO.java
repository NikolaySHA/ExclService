package com.ExclusiveService.model.dto;

import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.PaymentMethod;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class AddAppointmentDTO {
    
    @NotNull
    @FutureOrPresent
    private LocalDate date;
    @NotNull
    private Car car;
    @NotNull
    private Integer paintDetails;
    @NotNull
    private PaymentMethod paymentMethod;
    @ManyToOne
    private User user;
    private String comment;
    
}
