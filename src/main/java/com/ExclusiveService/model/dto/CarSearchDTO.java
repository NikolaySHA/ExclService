package com.ExclusiveService.model.dto;


import com.ExclusiveService.model.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarSearchDTO {
    private String licensePlate;
    private String make;
    private String customer;
    

}