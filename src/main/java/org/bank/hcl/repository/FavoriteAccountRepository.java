package org.bank.hcl.repository;

import org.bank.hcl.model.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Long> {
}
