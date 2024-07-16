package com.ExclusiveService.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDTO {
    
    @Email
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
    
    
    public RegisterDTO() {
    }
    
    public String getViberNumber() {
        return viberNumber;
    }
    
    public void setViberNumber(String viberNumber) {
        this.viberNumber = viberNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
