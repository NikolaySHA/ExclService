package com.NikolaySHA.ExclusiveService.model.dto.userDTO;

import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchDTO {
    private String name;
    private String email;
    private UserRolesEnum role;
}
