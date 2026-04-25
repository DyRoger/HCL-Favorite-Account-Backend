package org.bank.hcl.service;

import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.model.FavoriteAccount;

import java.util.List;

public interface FavoriteAccountService {

    public List<FavoriteAccountResponseDTO> fetchAllFavoriteAccount(String customerId);

    public void addFavoriteAccount(String customerId, AddFavoriteAccountDto addFavoriteAccount);
}
