package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.entity.UserRole;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.UserRolesEnum;
import com.ExclusiveService.repo.RoleRepository;
import com.ExclusiveService.repo.UserRepository;
import com.ExclusiveService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExclusiveUserDetailsService exclusiveUserDetailsService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ExclusiveUserDetailsService exclusiveUserDetailsService, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.exclusiveUserDetailsService = exclusiveUserDetailsService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public boolean register(RegisterDTO data) {
        Optional<User> optionalUser = userRepository.findByEmail(data.getEmail());
        if (optionalUser.isPresent()){
            return false;
        }
        User user = modelMapper.map(data, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        List<UserRole> userRoles = new ArrayList<>();
        if (userRepository.count() == 0){
            UserRole adminUserRole = roleRepository.findByName(UserRolesEnum.ADMIN);
            userRoles.add(adminUserRole);
        }
        UserRole customerUserRole = roleRepository.findByName(UserRolesEnum.CUSTOMER);
        userRoles.add(customerUserRole);
        user.setRoles(userRoles);
        
        userRepository.save(user);
        return true;
    }
    
    @Override
    public User findLoggedUser() {
        String email = exclusiveUserDetailsService.getAuthentication().getName();
        return userRepository.findByEmail(email).orElse(null);
    }
    
    @Override
    public boolean hasRole(String role) {
        UserDetails userDetails = (UserDetails) exclusiveUserDetailsService.getAuthentication().getPrincipal();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
}
