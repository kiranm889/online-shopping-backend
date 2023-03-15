package com.rest.webservices.onlineshoppingbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.webservices.onlineshoppingbackend.entity.Product;
import com.rest.webservices.onlineshoppingbackend.serviceImpl.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1.0/shopping")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		productService.addProduct(product);
		return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
	}

}
