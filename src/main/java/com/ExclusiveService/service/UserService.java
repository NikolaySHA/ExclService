package com.ExclusiveService.service;

import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.entity.User;


public interface UserService {
   
    
    boolean register(RegisterDTO data);
    
    User findLoggedUser();
    
    boolean hasRole(String admin);
    
   
}
