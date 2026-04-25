package org.bank.hcl.service;

import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.dto.UpdateFavoriteAccountDto;

import java.util.List;

public interface FavoriteAccountService {

    List<FavoriteAccountResponseDTO> fetchAllFavoriteAccount(String customerId);


    void addFavoriteAccount(String customerId, AddFavoriteAccountDto addFavoriteAccount);

    void deleteFavouriteAccount(String customerId, String iban);

    void updateFavoriteAccount(String customerId, String iban, UpdateFavoriteAccountDto updateDto);
}
