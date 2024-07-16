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
    
    @NotBlank(message = "{error_message_not_null}")
    @Size(min = 5, max = 12, message = "{error_message_size_number_plate}")
    private String licensePlate;
    @NotBlank(message = "{error_message_not_null}")
    @Size(min = 2, max = 30, message = "{error_message_size_2_30}")
    private String make;
    @NotBlank(message = "{error_message_not_null}")
    @Size(min = 2, max = 50, message = "{error_message_size_2_50}")
    private String model;
    @Pattern(regexp = "[A-Z0-9]{17}|", message = "{error_message_vin}")
    private String vin;
    private String color;
    
}
