package org.bank.hcl.repository;

import org.bank.hcl.model.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Long> {
    List<FavoriteAccount> findByBankUserCustomerId(String customerId);

    Optional<FavoriteAccount> findByBankUserCustomerIdAndIban(String customerId, String iban);

    Optional<FavoriteAccount> findByIban(String iban);
}
