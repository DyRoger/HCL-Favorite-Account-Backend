package org.bank.hcl.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateFavoriteAccountDto {

    @NotEmpty(message = "Account name cannot be empty")
    private String accountName;

    @NotEmpty(message = "IBAN cannot be empty")
    private String iban;
}

