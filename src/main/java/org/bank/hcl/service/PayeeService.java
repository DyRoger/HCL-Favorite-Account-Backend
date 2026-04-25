package org.bank.hcl.service;

import java.time.LocalDateTime;


import org.bank.hcl.model.User;
import org.bank.hcl.model.BankMapping;
import org.bank.hcl.model.FavoriteAccount;
import org.bank.hcl.repository.BankMappingRepository;
import org.bank.hcl.repository.FavoriteAccountRepository;
import org.bank.hcl.repository.UserRepository;


@org.springframework.stereotype.Service
public class PayeeService {

    private final FavoriteAccountRepository favoriteAccountRepository;
    private final UserRepository userRepository;
    private final BankMappingRepository bankMappingRepository;

    public PayeeService(FavoriteAccountRepository favoriteAccountRepository,
                        UserRepository userRepository,
                        BankMappingRepository bankMappingRepository) {
        this.favoriteAccountRepository = favoriteAccountRepository;
        this.userRepository = userRepository;
        this.bankMappingRepository = bankMappingRepository;
    }

    
    /// add payee for a customer	
    public FavoriteAccount addPayee(Long customerId, FavoriteAccount request) {

        User user = (User) userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (favoriteAccountRepository.existsByFavoriteAccount_CustomerIdAndIban(
                customerId, request.getIban())) {
            throw new RuntimeException("This payee already exists for this customer");
        }

        BankMapping bankMapping = bankMappingRepository
                .findByCode(request.getBankMapping().getCode())
                .orElseThrow(() -> new RuntimeException("Invalid bank code"));

        FavoriteAccount favoriteAccount = new FavoriteAccount();

        favoriteAccount.setFavoriteAccount(user);
        favoriteAccount.setAccountName(request.getAccountName());
        favoriteAccount.setIban(request.getIban());
        favoriteAccount.setBankMapping(bankMapping);
        favoriteAccount.setCreatedAt(LocalDateTime.now());
        favoriteAccount.setUpdatedAt(LocalDateTime.now());

        return favoriteAccountRepository.save(favoriteAccount);
    }
    
    //update payee for a customer
    public FavoriteAccount updatePayee(Long customerId, Long payeeId, FavoriteAccount request) {

        User user = userRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        FavoriteAccount existingPayee = favoriteAccountRepository
                .findByIdAndFavoriteAccount_CustomerId(payeeId, customerId)
                .orElseThrow(() -> new RuntimeException("Payee not found"));

        BankMapping bankMapping = bankMappingRepository
                .findByCode(request.getBankMapping().getCode())
                .orElseThrow(() -> new RuntimeException("Invalid bank code"));

        existingPayee.setFavoriteAccount(user); // important
        existingPayee.setAccountName(request.getAccountName());
        existingPayee.setIban(request.getIban());
        existingPayee.setBankMapping(bankMapping);
        existingPayee.setUpdatedAt(LocalDateTime.now());

        return favoriteAccountRepository.save(existingPayee);
    }
}