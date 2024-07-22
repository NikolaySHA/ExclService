package com.NikolaySHA.ExclusiveService.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class ExclusiveUserDetails extends User {
    private final String name;
    
    
    public ExclusiveUserDetails(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String name
    ) {
        super(username, password, authorities);
        this.name = name;
    }
    
}
