package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserEditDTO;
import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserRegisterDTO;
import com.NikolaySHA.ExclusiveService.model.dto.userDTO.UserViewDTO;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest

public class UserControllerIT {
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private UserService userService;
    
    @Mock
    private ModelMapper modelMapper;
    
    @Mock
    private Model model;
    
    @Mock
    private BindingResult bindingResult;
    
    @Mock
    private RedirectAttributes redirectAttributes;
    
    @Mock
    private Authentication authentication;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
   
    @Test
    void testViewLoginWhenNoUserIsLoggedIn() {
        when(userService.findLoggedUser()).thenReturn(null);
        
        String viewName = userController.viewLogin();
        
        assertEquals("login", viewName);
    }
    
    @Test
    void testViewLoginError() {
        String viewName = userController.viewLoginError(model);
        
        assertEquals("login", viewName);
        verify(model).addAttribute("showErrorMessage", true);
    }
    
    @Test
    void testShowRegistrationForm() {
        String viewName = userController.showRegistrationForm(model);
        
        assertEquals("form-user", viewName);
        verify(model).addAttribute("isEdit", false);
    }
    
    @Test
    void testDoRegisterUserWhenPasswordsMismatch() {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setPassword("password");
        userDTO.setConfirmPassword("different");
        
        String viewName = userController.doRegisterUser(userDTO, bindingResult, redirectAttributes);
        
        assertEquals("redirect:/users/register", viewName);
        verify(redirectAttributes).addFlashAttribute("passwordMismatch", true);
    }
    
    @Test
    void testDoRegisterUserWhenHasErrors() {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setEmail("test@tes.tes");
        userDTO.setName("");
        userDTO.setPassword("111");
        userDTO.setConfirmPassword("111");
        userDTO.setPhoneNumber("1293809301983103");
        when(bindingResult.hasErrors()).thenReturn(true);
        
        String viewName = userController.doRegisterUser(userDTO, bindingResult, redirectAttributes);
        
        assertEquals("redirect:/users/register", viewName);
        verify(redirectAttributes).addFlashAttribute("userData", userDTO);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userData", bindingResult);
    }
    @Test
    void testViewUserWhenUserNotFound() {
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());
        
        String viewName = userController.viewUser(userId, redirectAttributes, model);
        
        assertEquals("redirect:/error/contact-admin", viewName);
        verify(redirectAttributes).addFlashAttribute("notFoundErrorMessage", true);
    }
    
    @Test
    void testShowEditFormWhenUserNotFound() {
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());
        
        String viewName = userController.showEditForm(userId, model, redirectAttributes);
        
        assertEquals("redirect:/error/contact-admin", viewName);
        verify(redirectAttributes).addFlashAttribute("notFoundErrorMessage", true);
    }

    @Test
    void testAddAdminWhenUserNotFound() {
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());
        
        String viewName = userController.addAdmin(userId, redirectAttributes);
        
        assertEquals("redirect:/error/contact-admin", viewName);
        verify(redirectAttributes).addFlashAttribute("notFoundErrorMessage", true);
    }
    
    @Test
    void testRemoveAdminWhenUserNotFound() {
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(Optional.empty());
        
        String viewName = userController.removeAdmin(userId, redirectAttributes);
        
        assertEquals("redirect:/error/contact-admin", viewName);
        verify(redirectAttributes).addFlashAttribute("notFoundErrorMessage", true);
    }
    

}
