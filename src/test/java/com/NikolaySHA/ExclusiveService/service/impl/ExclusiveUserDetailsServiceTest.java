package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import com.NikolaySHA.ExclusiveService.repo.UserRepository;
import com.NikolaySHA.ExclusiveService.util.ExclusiveUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExclusiveUserDetailsServiceTest {
    private static final String TEST_EMAIL = "nik@nik";
    private static final String NOT_EXISTING_EMAIL = "aaa@aaa";
    
    @InjectMocks
    private ExclusiveUserDetailsService service;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    
    
    @Test
    void testLoadUserByUsername_UserFound() {
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);
        testUser.setPassword("111");
        testUser.setName("Nikolay Shatov");
        testUser.setRoles(List.of(
                new UserRole(UserRolesEnum.ADMIN),
                new UserRole(UserRolesEnum.CUSTOMER)
        ));
        
        when(userRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));
        
        UserDetails userDetails = service.loadUserByUsername(TEST_EMAIL);
        
        Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertInstanceOf(ExclusiveUserDetails.class, userDetails);
        ExclusiveUserDetails exclusiveUserDetails = (ExclusiveUserDetails) userDetails;
        Assertions.assertEquals(testUser.getName(), exclusiveUserDetails.getName());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        List<String> expectedAuthorities = testUser.getRoles().stream()
                .map(UserRole::getRole)
                .map(role -> "ROLE_" + role)
                .toList();
        List<String> actualAuthorities = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();
        Assertions.assertEquals(expectedAuthorities, actualAuthorities);
    }
    
    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByEmail(NOT_EXISTING_EMAIL)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(NOT_EXISTING_EMAIL);
        });
    }
    
//    @Test
//    void testGetAuthentication() {
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.isAuthenticated()).thenReturn(true);
//
//        Authentication auth = service.getAuthentication();
//        Assertions.assertNotNull(auth);
//    }
}
