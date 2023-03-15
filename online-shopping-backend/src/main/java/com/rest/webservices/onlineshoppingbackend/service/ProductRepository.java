package com.rest.webservices.onlineshoppingbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.webservices.onlineshoppingbackend.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String productName);


	Optional<Product> findByIdAndName(Long id, String productName);


	List<Product> findAllByName(String productName);


	List<Product> findByNameContaining(String productName);

//	List<Product> getList(String productName);
	
	

}
