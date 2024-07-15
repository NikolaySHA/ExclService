package com.ExclusiveService.service;

import com.ExclusiveService.model.dto.RegisterDTO;
import com.ExclusiveService.model.entity.User;


public interface UserService {
   
    
    boolean register(RegisterDTO data);
    
    User findLoggedUser();
    
    boolean hasRole(String admin);
    
    //    @Transactional
//    public List<Appointment> findFavourites(Long id) {
//        return customerRepository.findById(id)
//                .map(Customer::getFavouriteRecipes)
//                .orElseGet(ArrayList::new);
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isEmpty()) {
//            return new ArrayList<>();
//        }
//        return optionalUser.get().getFavouriteRecipes();
   //  }
   
}
