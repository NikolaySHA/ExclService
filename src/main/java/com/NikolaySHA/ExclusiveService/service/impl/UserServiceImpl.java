package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserEditDTO;
import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserRegisterDTO;
import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import com.NikolaySHA.ExclusiveService.repo.RoleRepository;
import com.NikolaySHA.ExclusiveService.repo.UserRepository;
import com.NikolaySHA.ExclusiveService.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExclusiveUserDetailsService exclusiveUserDetailsService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    
    @Override
    public boolean register(UserRegisterDTO data) {
        
        Optional<User> optionalUser = userRepository.findByEmail(data.getEmail());
        if (optionalUser.isPresent()){
            return false;
        }
        User user = modelMapper.map(data, User.class);
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        List<UserRole> userRoles = new ArrayList<>();
        if (userRepository.count() == 0){
            UserRole adminUserRole = roleRepository.findByRole(UserRolesEnum.ADMIN);
            userRoles.add(adminUserRole);
        }
        UserRole customerUserRole = roleRepository.findByRole(UserRolesEnum.CUSTOMER);
        userRoles.add(customerUserRole);
        user.setRoles(userRoles);
        
        userRepository.save(user);
        return true;
    }
    
    @Override
    @Transactional
    public User findLoggedUser() {
        String email = exclusiveUserDetailsService.getAuthentication().getName();
        return userRepository.findByEmail(email).orElse(null);
    }
    
    @Override
    @Transactional
    public boolean loggedUserHasRole(String role) {
        UserDetails userDetails = (UserDetails) exclusiveUserDetailsService.getAuthentication().getPrincipal();
        return userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
    }
    
    @Override
    public List<User> findAllUsersWithRoles() {
        return this.userRepository.findAllWithRoles();
    }
    
    @Override
    public List<User> searchUsers(String name, String email, UserRolesEnum role) {
        return this.userRepository.searchUsers(name, email, role);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public boolean updateUser(Long id, UserEditDTO updatedUser) {
        Optional<User> toEdit = userRepository.findById(id);
        if (toEdit.isEmpty()){
            return false;
        }
        User user = toEdit.get();
        user.setName(updatedUser.getName());
        String email = updatedUser.getEmail();
        if (!user.getEmail().equals(email)){
            if (userRepository.findByEmail(email).isPresent()){
                return false;
            }
        }
        user.setEmail(email);
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        userRepository.save(user);
        return true;
    }
    
    @Override
    public void addAdmin(Long userId) {
        User user = userRepository.findById(userId).get();
        List<UserRole> roles = user.getRoles();
        UserRole role = roleRepository.findByRole(UserRolesEnum.ADMIN);
        if (roles.contains(role)){
            return;
        }
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
    
    @Override
    public boolean removeAdmin(Long userId) {
        User user = userRepository.findById(userId).get();
        List<UserRole> roles = user.getRoles();
        UserRole role = roleRepository.findByRole(UserRolesEnum.ADMIN);
        
        if (!roles.contains(role)){
            return false;
        }
        long count = userRepository.findAll().stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(role1 -> role1.getRole().name().equals("ADMIN")))
                .count();
        if (count <2){
            return false;
        }
        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
        return true;
    }
    
    @Override
    public boolean isAdmin(List<UserRole> roles) {
        return roles.stream().anyMatch(role -> role.getRole().name().equals("ADMIN"));
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
