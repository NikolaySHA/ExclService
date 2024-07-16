package com.ExclusiveService.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {
    
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "{not_email_message}")
    @NotBlank(message = "{not_blank_message}")
    private String email;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 3 , max = 20, message = "{size_3_20_message}")
    private String password;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 3 , max = 20, message = "{size_3_20_message}")
    private String confirmPassword;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 3 , max = 20, message = "{size_3_20_message}")
    private String name;
    @NotBlank(message = "{not_blank_message}")
    @Size(min = 8 , max = 20, message = "{size_8_20_message}")
    private String viberNumber;
    
}
