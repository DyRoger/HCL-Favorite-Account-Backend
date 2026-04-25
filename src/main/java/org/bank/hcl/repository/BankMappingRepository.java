package org.bank.hcl.repository;

import org.bank.hcl.model.BankMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankMappingRepository extends JpaRepository<BankMapping,String> {

    Optional<BankMapping> findByCode(String code);
}
