package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.entity.Role;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.UserRoles;
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
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsServiceImpl, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
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
        List<Role> roles = new ArrayList<>();
        if (userRepository.count() == 0){
            Role adminRole = roleRepository.findByName(UserRoles.ADMIN);
            roles.add(adminRole);
        }
        Role customerRole = roleRepository.findByName(UserRoles.CUSTOMER);
        roles.add(customerRole);
        user.setRoles(roles);
        
        userRepository.save(user);
        return true;
    }
    
    @Override
    public User findLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                Optional<User> user = userRepository.findByEmail(((UserDetails) principal).getUsername());
                if (user.isPresent()){
                    return user.get();
                }
            }
        }
        return null;
    }
}
