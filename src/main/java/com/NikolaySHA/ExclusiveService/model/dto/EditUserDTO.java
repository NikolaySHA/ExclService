package com.NikolaySHA.ExclusiveService.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditUserDTO {
    
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "{error_message_not_email}")
    @NotBlank(message = "{error_message_not_null}")
    private String email;
    
    @NotBlank(message = "{error_message_not_null}")
    @Size(min = 3, max = 20, message = "{error_message_size_3_20}")
    private String name;
    
    @NotBlank(message = "{error_message_not_null}")
    @Size(min = 8, max = 20, message = "{error_message_size_8_20}")
    private String phoneNumber;
}
