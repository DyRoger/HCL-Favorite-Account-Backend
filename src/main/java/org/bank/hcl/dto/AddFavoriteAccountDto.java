package org.bank.hcl.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFavoriteAccountDto {

    @NotEmpty(message = "AccountName cannot be empty")
    private String accountName;

    @NotEmpty(message = "AccountNumber cannot be empty")
    private String iban;
}
