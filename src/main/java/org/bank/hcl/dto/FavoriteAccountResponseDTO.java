package org.bank.hcl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteAccountResponseDTO {

    private String accountName;

    private String iban;

    private String bankName;
}
