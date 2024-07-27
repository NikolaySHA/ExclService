package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserEditDTO;
import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserRegisterDTO;
import com.NikolaySHA.ExclusiveService.model.entity.UserRole;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.model.enums.UserRolesEnum;
import com.NikolaySHA.ExclusiveService.repo.RoleRepository;
import com.NikolaySHA.ExclusiveService.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private ExclusiveUserDetailsService exclusiveUserDetailsService;
    
    @Mock
    private RoleRepository roleRepository;
    
    @Mock
    private ModelMapper modelMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @BeforeEach
    void create() {
        // Initialize if needed
    }
    
    @Test
    void testRegisterUserSuccess() {
        // Arrange
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");
        registerDTO.setConfirmPassword("password");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setName("Test User");
        
        User user = new User();
        when(userRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(registerDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("encodedPassword");
        when(roleRepository.findByRole(UserRolesEnum.CUSTOMER)).thenReturn(new UserRole(UserRolesEnum.CUSTOMER));
        when(roleRepository.findByRole(UserRolesEnum.ADMIN)).thenReturn(new UserRole(UserRolesEnum.ADMIN)); // Add this line if ADMIN role is used
        when(userRepository.count()).thenReturn(0L);
        
        // Act
        boolean result = userService.register(registerDTO);
        
        // Assert
        assertTrue(result);
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }
    
    @Test
    void testRegisterUserEmailExists() {
        // Arrange
        UserRegisterDTO registerDTO = new UserRegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setPassword("password");
        registerDTO.setConfirmPassword("password");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setName("Test User");
        
        when(userRepository.findByEmail(registerDTO.getEmail())).thenReturn(Optional.of(new User()));
        
        // Act
        boolean result = userService.register(registerDTO);
        
        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void testFindLoggedUser() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(email);
        when(exclusiveUserDetailsService.getAuthentication()).thenReturn(authentication);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        
        // Act
        User result = userService.findLoggedUser();
        
        // Assert
        assertNotNull(result);
        assertEquals(user, result);
    }
    
    @Test
    void testUpdateUserSuccess() {
        // Arrange
        Long userId = 1L;
        UserEditDTO userEditDTO = new UserEditDTO();
        userEditDTO.setName("Updated Name");
        userEditDTO.setPhoneNumber("1234567890");
        userEditDTO.setEmail("updated@example.com"); // Fixed email
        User user = new User();
        user.setEmail("existing@example.com"); // Fixed initial email
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userEditDTO.getEmail())).thenReturn(Optional.empty());
        
        // Act
        boolean result = userService.updateUser(userId, userEditDTO);
        
        // Assert
        assertTrue(result);
        verify(userRepository).save(user);
        assertEquals("Updated Name", user.getName());
        assertEquals("updated@example.com", user.getEmail()); // Fixed email
        assertEquals("1234567890", user.getPhoneNumber());
    }
    
    @Test
    void testUpdateUserEmailExists() {
        // Arrange
        Long userId = 1L;
        UserEditDTO userEditDTO = new UserEditDTO();
        userEditDTO.setName("Updated Name");
        userEditDTO.setPhoneNumber("1234567890");
        userEditDTO.setEmail("existing@example.com");
        User user = new User();
        user.setEmail("original@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(userEditDTO.getEmail())).thenReturn(Optional.of(new User()));
        
        // Act
        boolean result = userService.updateUser(userId, userEditDTO);
        
        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void testAddAdmin() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        UserRole adminRole = new UserRole(UserRolesEnum.ADMIN);
        List<UserRole> roles = new ArrayList<>();
        user.setRoles(roles);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(UserRolesEnum.ADMIN)).thenReturn(adminRole);
        
        // Act
        userService.addAdmin(userId);
        
        // Assert
        assertTrue(user.getRoles().contains(adminRole));
        verify(userRepository).save(user);
    }
    
    @Test
    void testIsAdmin() {
        // Arrange
        UserRole adminRole = new UserRole(UserRolesEnum.ADMIN);
        UserRole userRole = new UserRole(UserRolesEnum.CUSTOMER);
        User user = new User();
        user.setRoles(List.of(adminRole, userRole));
        
        // Act
        boolean isAdmin = userService.isAdmin(user.getRoles());
        
        // Assert
        assertTrue(isAdmin);
    }
    
    @Test
    void testFindAll() {
        // Arrange
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        
        // Act
        List<User> result = userService.findAll();
        
        // Assert
        assertEquals(users, result);
    }
}
