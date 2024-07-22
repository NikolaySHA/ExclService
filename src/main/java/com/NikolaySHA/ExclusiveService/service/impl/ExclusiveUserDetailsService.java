package com.NikolaySHA.ExclusiveService.service.impl;


import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.util.ExclusiveUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import com.NikolaySHA.ExclusiveService.repo.UserRepository;

@AllArgsConstructor
public class ExclusiveUserDetailsService implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        
        return userRepository
                .findByEmail(email)
                .map(ExclusiveUserDetailsService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }
    
    private static UserDetails map(User user) {
        
        return new ExclusiveUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(UserRole::getRole).map(ExclusiveUserDetailsService::map).toList(),
                user.getName()
        );
    }
    
    private static GrantedAuthority map(UserRolesEnum role) {
        return new SimpleGrantedAuthority(ROLE_PREFIX + role);
    }
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication;
    }
    
}