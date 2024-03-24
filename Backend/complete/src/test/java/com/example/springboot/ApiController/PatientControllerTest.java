//package com.example.springboot.ApiController;
//
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.example.springboot.Entities.User;
//import com.example.springboot.Services.PatientService;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(PatientController.class)
//public class PatientControllerTest {
//
//        @Autowired
//        private MockMvc mockMvc;
//
//        @MockBean
//        private PatientService patientService;
//
//        @Test
//        public void testCreatePatient() throws Exception {
//            // Mock data
//            User user = new User();
//            user.setName("Test Name");
//            user.setEmail("test@example.com");
//
//            // Mock service response
//            when(patientService.savePatient(any(User.class))).thenReturn(user);
//
//            // Perform POST request
//            mockMvc.perform(post("/patients/create")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(asJsonString(user)))
//                    .andExpect(status().isCreated())
//                    .andExpect(content().string(user.getId().toString()));
//        }
//
//        @Test
//        public void testGetAllPatients() throws Exception {
//            // Mock data
//            List<User> users = new ArrayList<>();
//            // Add mock users to the list
//
//            // Mock service response
//            when(patientService.getAllPatients()).thenReturn(users);
//
//            // Perform GET request
//            mockMvc.perform(get("/patients/all"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(jsonPath("$", hasSize(users.size())));
//        }
//
//        @Test
//        public void testUpdateUser() throws Exception {
//            // Mock service response
//            List<User> users = new ArrayList<>();
//            // Add mock users to the list
//
//            // Mock service response
//            when(patientService.getAllPatients()).thenReturn(users);
//
//            // Perform POST request
//            mockMvc.perform(post("/patients/update"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(jsonPath("$", hasSize(users.size())));
//        }
//
////        @Test
////        public void testAuthenticateUser() throws Exception {
////            // Mock data
////            User user = new User();
////            user.setUsername("testuser");
////            user.setPassword("password");
////
////            // Mock service response
////            List<User> users = new ArrayList<>();
////            // Add mock user to the list
////
////            // Mock service response
////            when(patientService.authenticateUser(anyString(), anyString())).thenReturn(users);
////
////            // Perform POST request
////            mockMvc.perform(post("/users/authenticate")
////                            .contentType(MediaType.APPLICATION_JSON)
////                            .content(asJsonString(user)))
////                    .andExpect(status().isFound())
////                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
////                    .andExpect(jsonPath("$.username", is(user.getUsername())));
////        }
////
////        // Utility method to convert objects to JSON string
////        private static String asJsonString(final Object obj) {
////            try {
////                return new ObjectMapper().writeValueAsString(obj);
////            } catch (Exception e) {
////                throw new RuntimeException(e);
////            }
////        }
//    }