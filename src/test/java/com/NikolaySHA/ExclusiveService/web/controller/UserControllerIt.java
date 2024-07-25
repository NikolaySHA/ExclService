package com.NikolaySHA.ExclusiveService.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIt {
    @Autowired
    private MockMvc mockMvc;
    
    
//    @Autowired
//    private UserRepository userRepository;
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

//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }

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
}
