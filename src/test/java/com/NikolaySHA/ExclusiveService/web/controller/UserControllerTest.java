//package com.NikolaySHA.ExclusiveService.web.controller;
//
//import com.NikolaySHA.ExclusiveService.model.dto.EditUserDTO;
//import com.NikolaySHA.ExclusiveService.model.dto.RegisterDTO;
//import com.NikolaySHA.ExclusiveService.model.dto.ShowUserDTO;
//import com.NikolaySHA.ExclusiveService.model.entity.User;
//import com.NikolaySHA.ExclusiveService.service.AppointmentService;
//import com.NikolaySHA.ExclusiveService.service.CarService;
//import com.NikolaySHA.ExclusiveService.service.UserService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.validation.BindingResult;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private AppointmentService appointmentService;
//
//    @Mock
//    private CarService carService;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    void testRegisterPage() throws Exception {
//        mockMvc.perform(get("/register"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("register"));
//    }
//
//    @Test
//    void testDoRegister_Success() throws Exception {
//        RegisterDTO registerDTO = new RegisterDTO();
//        // Set fields on registerDTO as needed
//
//        when(userService.register(any(RegisterDTO.class))).thenReturn(true);
//
//        mockMvc.perform(post("/register")
//                        .flashAttr("registerData", registerDTO))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/login"));
//    }
//
//    @Test
//    void testDoRegister_Failure() throws Exception {
//        RegisterDTO registerDTO = new RegisterDTO();
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        mockMvc.perform(post("/register")
//                        .flashAttr("registerData", registerDTO)
//                        .flashAttr("org.springframework.validation.BindingResult.registerData", bindingResult))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/register"));
//    }
//
//    @Test
//    void testViewLogin() throws Exception {
//        when(userService.findLoggedUser()).thenReturn(null);
//
//        mockMvc.perform(get("/users/login"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("login"));
//    }
//
//    @Test
//    void testViewUser_Success() throws Exception {
//        Long userId = 1L;
//        User user = new User();
//        ShowUserDTO showUserDTO = new ShowUserDTO();
//        when(userService.findById(userId)).thenReturn(Optional.of(user));
//        when(carService.findCarsByUser(userId)).thenReturn(null);
//        when(appointmentService.getAppointmentsByUserEmail(anyString())).thenReturn(null);
//
//        mockMvc.perform(get("/users/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("user"));
//    }
//
//    @Test
//    void testEditUserForm_Success() throws Exception {
//        Long userId = 1L;
//        User user = new User();
//        EditUserDTO editUserDTO = new EditUserDTO();
//        when(userService.findById(userId)).thenReturn(Optional.of(user));
//        when(modelMapper.map(user, EditUserDTO.class)).thenReturn(editUserDTO);
//
//        mockMvc.perform(get("/users/edit/{id}", userId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("edit-user"));
//    }
//
//    @Test
//    void testUpdateUser_Success() throws Exception {
//        Long userId = 1L;
//        EditUserDTO editUserDTO = new EditUserDTO();
//        when(userService.updateUser(anyLong(), any(EditUserDTO.class))).thenReturn(true);
//
//        mockMvc.perform(post("/users/edit/{id}", userId)
//                        .flashAttr("editUserData", editUserDTO))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }
//
//    @Test
//    void testAddAdmin_Success() throws Exception {
//        Long userId = 1L;
//        User user = new User();
//        when(userService.findById(userId)).thenReturn(Optional.of(user));
//        when(userService.isAdmin(user.getRoles())).thenReturn(false);
//
//        mockMvc.perform(post("/users/addAdmin")
//                        .param("id", userId.toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/" + userId));
//    }
//
//    @Test
//    void testRemoveAdmin_Success() throws Exception {
//        Long userId = 1L;
//        User user = new User();
//        when(userService.findById(userId)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(post("/users/removeAdmin")
//                        .param("id", userId.toString()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/" + userId));
//    }
//}
