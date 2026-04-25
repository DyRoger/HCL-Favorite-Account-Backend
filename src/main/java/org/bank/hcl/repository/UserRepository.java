package org.bank.hcl.repository;

import org.bank.hcl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByCustomerId(String customerId);
}
