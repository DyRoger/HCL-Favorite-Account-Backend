package org.bank.hcl.repository;

import java.util.Optional;

import org.bank.hcl.model.FavoriteAccount;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount,Long> {
	
	 boolean existsByFavoriteAccount_CustomerIdAndIban(Long customerId, String iban);
	 Optional<FavoriteAccount> findByIdAndFavoriteAccount_CustomerId(Long id, Long customerId);
}
