package org.bank.hcl.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.mapper.FavoriteAccountMapper;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.model.FavoriteAccount;
import org.bank.hcl.model.User;
import org.bank.hcl.repository.BankMappingRepository;
import org.bank.hcl.repository.FavoriteAccountRepository;
import org.bank.hcl.repository.UserRepository;
import org.bank.hcl.service.FavoriteAccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FavoriteAccountServiceImpl implements FavoriteAccountService {

    private final FavoriteAccountRepository favoriteAccountRepository;
    private final BankMappingRepository bankMappingRepository;
    private final UserRepository userRepository;

    @Override
    public List<FavoriteAccountResponseDTO> fetchAllFavoriteAccount(String customerId) {
      return  FavoriteAccountMapper.toResponseDTOList(
              favoriteAccountRepository.findByBankUserCustomerId(customerId));

    }

    @Override
    public void addFavoriteAccount(String customerId, AddFavoriteAccountDto addFavoriteAccount) {
      User user= userRepository.findByCustomerId(customerId).
              orElseThrow(() -> new RuntimeException("User not found with customerId: " + customerId));

      BankMapping bankMapping = bankMappingRepository.findByCode(addFavoriteAccount.getIban().substring(4,8))
              .orElseThrow(() -> new RuntimeException("Bank not found"));

        FavoriteAccount favoriteAccount = FavoriteAccount.builder()
                .bankMapping(bankMapping)
                .accountName(addFavoriteAccount.getAccountName())
                .iban(addFavoriteAccount.getIban())
                .bankUser(user)
                .createdAt(LocalDateTime.now())
                .build();

        favoriteAccountRepository.save(favoriteAccount);

    }
}
