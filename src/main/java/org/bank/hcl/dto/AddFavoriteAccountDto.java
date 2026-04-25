package org.bank.hcl.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddFavoriteAccountDto {

    @NotBlank(message = "Account name is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z0-9'\\- ]+$",
            message = "Account name can contain letters, numbers, space, ' and - only"
    )
    @Size(max = 100, message = "Account name must be at most 100 characters")
    private String accountName;

    @NotBlank(message = "IBAN is mandatory")
    @Pattern(
            regexp = "^[a-zA-Z0-9]+$",
            message = "IBAN must contain only letters and numbers"
    )
    @Size(max = 20, message = "IBAN must be at most 20 characters")
    private String iban;
}
