package org.bank.hcl;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.bank.hcl.controller.Controller;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.model.FavoriteAccount;
import org.bank.hcl.service.PayeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PayeeService payeeService;

    @BeforeEach
    void setup() {
        Controller controller = new Controller(payeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void addPayee_ShouldReturnCreatedResponse() throws Exception {

        BankMapping bankMapping = new BankMapping();
        bankMapping.setCode("HDFC");

        FavoriteAccount response = new FavoriteAccount();
        response.setId(1L);
        response.setAccountName("Ravi Kumar");
        response.setIban("AE123456789");
        response.setBankMapping(bankMapping);

        when(payeeService.addPayee(eq(1L), any(FavoriteAccount.class)))
                .thenReturn(response);

        String requestBody = """
                {
                  "accountName": "Ravi Kumar",
                  "iban": "AE123456789",
                  "bankMapping": {
                    "code": "HDFC"
                  }
                }
                """;

        mockMvc.perform(post("/api/v1/customers/1/favorite-payees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Favorite payee added successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.accountName").value("Ravi Kumar"))
                .andExpect(jsonPath("$.data.iban").value("AE123456789"));
    }

    @Test
    void updatePayee_ShouldReturnOkResponse() throws Exception {

        BankMapping bankMapping = new BankMapping();
        bankMapping.setCode("ICICI");

        FavoriteAccount response = new FavoriteAccount();
        response.setId(5L);
        response.setAccountName("Updated Ravi");
        response.setIban("AE999999999");
        response.setBankMapping(bankMapping);

        when(payeeService.updatePayee(eq(1L), eq(5L), any(FavoriteAccount.class)))
                .thenReturn(response);

        String requestBody = """
                {
                  "accountName": "Updated Ravi",
                  "iban": "AE999999999",
                  "bankMapping": {
                    "code": "ICICI"
                  }
                }
                """;

        mockMvc.perform(put("/api/v1/customers/1/favorite-payees/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Favorite payee updated successfully"))
                .andExpect(jsonPath("$.data.id").value(5))
                .andExpect(jsonPath("$.data.accountName").value("Updated Ravi"))
                .andExpect(jsonPath("$.data.iban").value("AE999999999"));
    }
}