package org.bank.hcl.controller;

import org.bank.hcl.model.LoginData;
import org.bank.hcl.repository.UserRepository;
import org.bank.hcl.service.AuditService;
import org.bank.hcl.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private AuditService auditService;

    private LoginData validLoginData;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();

        validLoginData = new LoginData();
        validLoginData.setCustomerId("CUST001");
        validLoginData.setHash("03ca1c3278cf01923ea5fbf6780b3dace51a548cf7829816be97d6621a66cc8e");
    }

    @Test
    void testLogin_Success() throws Exception {
        when(userRepository.findByCustomerId("CUST001")).thenReturn(Optional.of(new org.bank.hcl.model.User()));
        when(jwtService.generateToken("CUST001")).thenReturn("mocked-jwt-token");

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": \"CUST001\", \"hash\": \"03ca1c3278cf01923ea5fbf6780b3dace51a548cf7829816be97d6621a66cc8e\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void testLogin_CustomerNotFound() throws Exception {
        when(userRepository.findByCustomerId("CUST001")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": \"CUST001\", \"hash\": \"03ca1c3278cf01923ea5fbf6780b3dace51a548cf7829816be97d6621a66cc8e\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_InvalidHash() throws Exception {
        when(userRepository.findByCustomerId("CUST001")).thenReturn(Optional.of(new org.bank.hcl.model.User()));

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": \"CUST001\", \"hash\": \"wrong-hash\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_MissingCustomerId() throws Exception {
        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": null, \"hash\": \"03ca1c3278cf01923ea5fbf6780b3dace51a548cf7829816be97d6621a66cc8e\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_MissingHash() throws Exception {
        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\": \"CUST001\", \"hash\": null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testTestAuth_Success() throws Exception {
        mockMvc.perform(get("/api/v1/test-auth"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogout_Success() throws Exception {
        mockMvc.perform(post("/api/v1/logout"))
                .andExpect(status().isUnauthorized());
    }
}







