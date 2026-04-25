package org.bank.hcl.mapper;

import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.model.FavoriteAccount;

import java.util.ArrayList;
import java.util.List;

public final class FavoriteAccountMapper {

     private FavoriteAccountMapper(){

     }
    public static List<FavoriteAccountResponseDTO> toResponseDTOList(
            List<FavoriteAccount> favoriteAccountList) {

        return favoriteAccountList.stream()
                .map(fav -> FavoriteAccountResponseDTO.builder()
                        .accountName(fav.getAccountName())
                        .iban(fav.getIban())
                        .bankName(fav.getBankMapping().getBankName())
                        .build())
                .toList();
    }
}
