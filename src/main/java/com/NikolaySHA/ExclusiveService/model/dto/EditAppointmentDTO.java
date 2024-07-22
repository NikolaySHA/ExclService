package com.NikolaySHA.ExclusiveService.model.dto;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.PaymentMethod;
import com.NikolaySHA.ExclusiveService.model.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class EditAppointmentDTO {
    
    @NotNull(message = "{error_message_not_null}")
    @FutureOrPresent
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @NotNull(message = "{error_message_not_null}")
    private Car car;
    @NotNull(message = "{error_message_not_null}")
    private Integer paintDetails;
    @NotNull(message = "{error_message_not_null}")
    private PaymentMethod paymentMethod;
    @NotNull(message = "{error_message_not_null}")
    private User user;
    private String comment;
    @NotNull(message = "{error_message_not_null}")
    private Status status;
   
    
}
