package org.bank.hcl.controller;

import org.bank.hcl.dto.BankNameResponseDto;
import org.bank.hcl.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private BankingService bankingService;

    private BankNameResponseDto bankResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();

        bankResponse = new BankNameResponseDto();
        bankResponse.setBankName("Deutsche Bank");
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchBankName_Success() throws Exception {
        when(bankingService.fetchBankName("DE89370400440532013000")).thenReturn(bankResponse);

        mockMvc.perform(get("/api/v1/bank/DE89370400440532013000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName").value("Deutsche Bank"));
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchBankName_InvalidIbanLength() throws Exception {
        mockMvc.perform(get("/api/v1/bank/SHORT"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchBankName_BankNotFound() throws Exception {
        doThrow(new org.bank.hcl.exceptionhandler.ResourceNotFoundException(
                "Bank not found for IBAN code: 9999"))
                .when(bankingService)
                .fetchBankName(anyString());

        mockMvc.perform(get("/api/v1/bank/ES501234999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchBankName_ValidIban_DifferentBank() throws Exception {
        BankNameResponseDto gbBankResponse = new BankNameResponseDto();
        gbBankResponse.setBankName("HSBC Bank");

        when(bankingService.fetchBankName("GB82WEST12345698765432")).thenReturn(gbBankResponse);

        mockMvc.perform(get("/api/v1/bank/GB82WEST12345698765432"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankName").value("HSBC Bank"));
    }

    @Test
    void testFetchBankName_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/bank/DE89370400440532013000"))
                .andExpect(status().isUnauthorized());
    }
}









