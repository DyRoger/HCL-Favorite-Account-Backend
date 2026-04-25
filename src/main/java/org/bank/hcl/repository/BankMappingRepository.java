package org.bank.hcl.repository;

import java.util.Optional;

import org.apache.catalina.User;
import org.bank.hcl.model.BankMapping;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BankMappingRepository extends JpaRepository<BankMapping,Long> {
	
	  Optional<BankMapping> findByCode(String code);
}
