package org.bank.hcl.repository;

import java.util.Optional;

import org.bank.hcl.model.User;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User,Long> {
	
	 Optional<User> findByCustomerId(Long customerId);
}
