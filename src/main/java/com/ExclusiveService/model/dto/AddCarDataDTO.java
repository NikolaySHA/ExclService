package com.ExclusiveService.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCarDataDTO {
    
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 5, max = 12, message = "{size_number_plate_message}")
    private String licensePlate;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 2, max = 30, message = "{size_2_30_message}")
    private String make;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 2, max = 50, message = "{size_2_50_message}")
    private String model;
    @Pattern(regexp = "[A-Z0-9]{17}|", message = "{vin_message}")
    private String vin;
    private String color;
    
}
