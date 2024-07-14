package com.ExclusiveService.service.impl;

import com.ExclusiveService.model.entity.Role;
import com.ExclusiveService.model.entity.User;
import com.ExclusiveService.model.enums.UserRoles;
import com.ExclusiveService.repo.UserRepository;
import com.ExclusiveService.util.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
    private static final String TEST_EMAIL = "nik@nik";
    private static final String NOT_EXISTING_EMAIL = "aaa@aaa";
    private UserDetailsServiceImpl service;
    
    @Mock
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        service = new UserDetailsServiceImpl(userRepository);
        
    }
    @Test
    void testLoadUserByUsername_UserFound(){
        
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);
        testUser.setPassword("111");
        testUser.setName("Nikolay Shatov");
        testUser.setRoles(List.of(
                new Role(UserRoles.ADMIN),
                new Role(UserRoles.CUSTOMER)
        ));
        
        when(userRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));
        
        UserDetails userDetails = service.loadUserByUsername(TEST_EMAIL);
        
        Assertions.assertEquals(userDetails.getUsername(), TEST_EMAIL);
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(userDetails.getName(), testUser.getName());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertTrue(userDetails.getAuthorities().stream().anyMatch(a-> a.getAuthority().equals("ROLE_ADMIN")));
        List<String> expectedAuthorities = testUser.getRoles().stream().map(Role::getName).map(r -> "ROLE_" + r).toList();
        List<String> actualAuthorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        Assertions.assertEquals(expectedAuthorities, actualAuthorities);
    }
    @Test
    void testLoadUserByUsername_UserNotFound(){
        Optional<User> byEmail = userRepository.findByEmail(NOT_EXISTING_EMAIL);
        Assertions.assertTrue(byEmail.isEmpty());
    }
    
}
