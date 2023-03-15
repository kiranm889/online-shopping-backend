package com.rest.webservices.onlineshoppingbackend.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.rest.webservices.onlineshoppingbackend.entity.Customer;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByLoginId(String loginId);

	Optional<Customer> findByEmail(String email);

	Customer findByFirstName(String name);

	Optional<Customer> findByLoginIdAndEmail(String loginId, String email);

}
