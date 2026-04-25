package org.bank.hcl.serviceimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.hcl.dto.AddFavoriteAccountDto;
import org.bank.hcl.dto.FavoriteAccountResponseDTO;
import org.bank.hcl.exceptionhandler.ResourceNotFoundException;
import org.bank.hcl.mapper.FavoriteAccountMapper;
import org.bank.hcl.model.AuditLog;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.model.FavoriteAccount;
import org.bank.hcl.model.User;
import org.bank.hcl.repository.AuditLogRepository;
import org.bank.hcl.repository.BankMappingRepository;
import org.bank.hcl.repository.FavoriteAccountRepository;
import org.bank.hcl.repository.UserRepository;
import org.bank.hcl.service.FavoriteAccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteAccountServiceImpl implements FavoriteAccountService {

    private final FavoriteAccountRepository favoriteAccountRepository;
    private final BankMappingRepository bankMappingRepository;
    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    public List<FavoriteAccountResponseDTO> fetchAllFavoriteAccount(String customerId) {
        List<FavoriteAccountResponseDTO> result = FavoriteAccountMapper.toResponseDTOList(
                favoriteAccountRepository.findByBankUserCustomerId(customerId));

        log.info("Fetched {} favorite account(s) | customerId: {}", result.size(), customerId);

        auditLogRepository.save(AuditLog.builder()
                .customerId(customerId)
                .action("FETCH_FAVORITE_ACCOUNTS")
                .resource("FAVORITE_ACCOUNT")
                .status("SUCCESS")
                .message("Fetched " + result.size() + " account(s)")
                .createdAt(LocalDateTime.now())
                .build());

        return result;
    }

    @Override
    public void addFavoriteAccount(String customerId, AddFavoriteAccountDto addFavoriteAccount) {
        User user = userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("User not found with customerId: " + customerId));

        BankMapping bankMapping = bankMappingRepository
                .findByCode(addFavoriteAccount.getIban().substring(4, 8))
                .orElseThrow(() -> new RuntimeException("Bank not found"));

        favoriteAccountRepository.findByIban(addFavoriteAccount.getIban()).
                ifPresent(acc -> {
                    throw new IllegalArgumentException("Account already exists");
                });

        FavoriteAccount favoriteAccount = FavoriteAccount.builder()
                .bankMapping(bankMapping)
                .accountName(addFavoriteAccount.getAccountName())
                .iban(addFavoriteAccount.getIban())
                .bankUser(user)
                .createdAt(LocalDateTime.now())
                .build();

        FavoriteAccount saved = favoriteAccountRepository.save(favoriteAccount);

        log.info("Favorite account added | customerId: {} | accountName: {}",
                customerId, addFavoriteAccount.getAccountName());

        auditLogRepository.save(AuditLog.builder()
                .customerId(customerId)
                .action("ADD_FAVORITE")
                .resource("FAVORITE_ACCOUNT")
                .resourceId(saved.getId())
                .status("SUCCESS")
                .message("Added account: " + addFavoriteAccount.getAccountName())
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public void deleteFavouriteAccount(String customerId, String iban) {
        User user= userRepository.findByCustomerId(customerId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with customerId: " + customerId));

        FavoriteAccount account = favoriteAccountRepository
                .findByBankUserCustomerIdAndIban(customerId, iban)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Favorite account not found with IBAN: " + iban)
                );

        favoriteAccountRepository.delete(account);
    }
}
