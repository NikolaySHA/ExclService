package com.NikolaySHA.ExclusiveService.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProtocolDTO {
    private long id;
    private LocalDate date;
    private String customerName;
    private String licensePlate;
    private String make;
    private String model;
    private boolean finished;
   
    
    
}
