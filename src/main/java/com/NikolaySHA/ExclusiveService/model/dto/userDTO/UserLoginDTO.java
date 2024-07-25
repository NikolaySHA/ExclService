package com.NikolaySHA.ExclusiveService.model.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginDTO {
    
    private String email;
    private String password;
    
}
