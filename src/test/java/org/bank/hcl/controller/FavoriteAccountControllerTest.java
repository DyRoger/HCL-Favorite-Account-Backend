package org.bank.hcl.controller;

import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.dto.UpdateFavoriteAccountDto;
import org.bank.hcl.service.FavoriteAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FavoriteAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private FavoriteAccountService favoriteAccountService;

    private AddFavoriteAccountDto addFavoriteAccountDto;
    private UpdateFavoriteAccountDto updateFavoriteAccountDto;
    private FavoriteAccountResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();

        addFavoriteAccountDto = new AddFavoriteAccountDto();
        addFavoriteAccountDto.setAccountName("My Savings Account");
        addFavoriteAccountDto.setIban("DE89370400440532013000");

        updateFavoriteAccountDto = new UpdateFavoriteAccountDto();
        updateFavoriteAccountDto.setAccountName("Updated Account Name");
        updateFavoriteAccountDto.setIban("GB82WEST12345698765432");

        responseDTO = new FavoriteAccountResponseDTO();
        responseDTO.setAccountName("My Account");
        responseDTO.setIban("DE89370400440532013000");
        responseDTO.setBankName("Deutsche Bank");
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchFavouriteAccount_Success() throws Exception {
        List<FavoriteAccountResponseDTO> accounts = Arrays.asList(responseDTO);
        when(favoriteAccountService.fetchAllFavoriteAccount("CUST001")).thenReturn(accounts);

        mockMvc.perform(get("/api/v1/customers/CUST001/favorite-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountName").value("My Account"))
                .andExpect(jsonPath("$[0].iban").value("DE89370400440532013000"));
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testFetchFavouriteAccount_EmptyList() throws Exception {
        when(favoriteAccountService.fetchAllFavoriteAccount("CUST001")).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/customers/CUST001/favorite-accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testAddFavoriteAccount_Success() throws Exception {
        mockMvc.perform(post("/api/v1/customers/CUST001/favorite-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountName\": \"My Savings Account\", \"iban\": \"DE89370400440532013000\", \"bankCode\": \"37040044\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testAddFavoriteAccount_InvalidIban() throws Exception {
        doThrow(new RuntimeException("Bank not found")).when(favoriteAccountService)
                .addFavoriteAccount(anyString(), any());

        mockMvc.perform(post("/api/v1/customers/CUST001/favorite-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountName\": \"My Savings Account\", \"iban\": \"DE89370400440532013000\", \"bankCode\": \"37040044\"}"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testUpdateFavoriteAccount_Success() throws Exception {
        mockMvc.perform(put("/api/v1/customers/CUST001/favorite-accounts/DE89370400440532013000")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountName\": \"Updated Account Name\", \"iban\": \"GB82WEST12345698765432\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testUpdateFavoriteAccount_NotFound() throws Exception {
        doThrow(new org.bank.hcl.exceptionhandler.ResourceNotFoundException(
                "Favorite account not found for IBAN: DE89370400440532013000"))
                .when(favoriteAccountService)
                .updateFavoriteAccount(anyString(), anyString(), any());

        mockMvc.perform(put("/api/v1/customers/CUST001/favorite-accounts/DE89370400440532013000")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountName\": \"Updated Account Name\", \"iban\": \"GB82WEST12345698765432\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testDeleteFavoriteAccount_Success() throws Exception {
        mockMvc.perform(delete("/api/v1/customers/CUST001/favorite-accounts/DE89370400440532013000"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "CUST001")
    void testDeleteFavoriteAccount_NotFound() throws Exception {
        doThrow(new org.bank.hcl.exceptionhandler.ResourceNotFoundException(
                "Favorite account not found for IBAN: DE89370400440532013000"))
                .when(favoriteAccountService)
                .deleteFavouriteAccount(anyString(), anyString());

        mockMvc.perform(delete("/api/v1/customers/CUST001/favorite-accounts/DE89370400440532013000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFetchFavouriteAccount_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/customers/CUST001/favorite-accounts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAddFavoriteAccount_Unauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/customers/CUST001/favorite-accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountName\": \"My Savings Account\", \"iban\": \"DE89370400440532013000\", \"bankCode\": \"37040044\"}"))
                .andExpect(status().isUnauthorized());
    }
}









