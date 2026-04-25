package org.bank.hcl.repository;

import org.bank.hcl.model.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Long> {
    List<FavoriteAccount> findByBankUserCustomerId(String customerId);
}
