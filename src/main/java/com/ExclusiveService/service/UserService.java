package com.ExclusiveService.service;

import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.UserRolesEnum;

import java.util.List;


public interface UserService {
   
    
    boolean register(RegisterDTO data);
    
    User findLoggedUser();
    
    boolean hasRole(String admin);
    
    
    List<User> findAllUsers();
    
    List<User> searchUsers(String name, String email, UserRolesEnum role);
    
    User getUserById(Long id);
    void updateUser(Long id, RegisterDTO updatedUser);
    
}
