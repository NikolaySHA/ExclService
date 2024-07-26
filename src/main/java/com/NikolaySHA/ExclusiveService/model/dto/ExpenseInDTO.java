package com.NikolaySHA.ExclusiveService.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseInDTO {
    private Long id;
    private String name;
    private double price;
    private long appointmentId;
    
}
