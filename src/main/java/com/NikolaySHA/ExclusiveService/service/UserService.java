package com.NikolaySHA.ExclusiveService.service;

import com.NikolaySHA.ExclusiveService.model.dto.EditUserDTO;
import com.NikolaySHA.ExclusiveService.model.dto.RegisterDTO;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;

import java.util.List;
import java.util.Optional;


public interface UserService {
   
    
    boolean register(RegisterDTO data);
    
    User findLoggedUser();
    
    boolean loggedUserHasRole(String role);
    
    
    List<User> findAllUsersWithRoles();
    
    List<User> searchUsers(String name, String email, UserRolesEnum role);
    
    Optional<User> findById(Long id);
    boolean updateUser(Long id, EditUserDTO user);
    
    void addAdmin(Long userId);
    
    void removeAdmin(Long userId);
    
    boolean isAdmin(List<UserRole> roles);
}