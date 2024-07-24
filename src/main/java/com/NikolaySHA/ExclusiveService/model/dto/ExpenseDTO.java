package com.NikolaySHA.ExclusiveService.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDTO {
    
    private String name;
    private double price;
    private long appointmentId;
    
}
