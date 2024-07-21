package com.ExclusiveService.model.dto;

import com.ExclusiveService.model.entity.Car;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.PaymentMethod;
import com.ExclusiveService.model.enums.Status;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EditAppointmentDTO {
    
    @NotNull(message = "{error_message_not_null}")
    @FutureOrPresent
    private LocalDate date;
    @NotNull(message = "{error_message_not_null}")
    private Car car;
    @NotNull(message = "{error_message_not_null}")
    private Integer paintDetails;
    @NotNull(message = "{error_message_not_null}")
    private PaymentMethod paymentMethod;
    private User user;
    private String comment;
    private Status status;
    
}