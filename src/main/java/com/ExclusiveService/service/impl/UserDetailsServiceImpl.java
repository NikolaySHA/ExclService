package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.repo.UserRepository;
import com.ExclusiveService.util.UserDetails;
import org.hibernate.Hibernate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found!"));
        
        Hibernate.initialize(user.getRoles());
        List<GrantedAuthority> authorities = mapToGrantedAuthority(user);
        
        return new UserDetails(
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getName()
        );
    }
    
    private List<GrantedAuthority> mapToGrantedAuthority(User user) {
        return user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toUnmodifiableList());
    }
    
    public boolean hasRole(String role){
        return getUserDetails()
                .getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_PREFIX + role));
    }
    public User getLoggedUser(){
        return userRepository.findByEmail(getUserDetails().getUsername())
                .orElse(null);
    }

    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public boolean isAuthenticated(){
        return !hasRole("ANONYMOUS");
    }
    
}