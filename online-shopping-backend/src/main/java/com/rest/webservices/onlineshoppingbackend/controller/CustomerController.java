package com.rest.webservices.onlineshoppingbackend.controller;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.webservices.onlineshoppingbackend.entity.Customer;
import com.rest.webservices.onlineshoppingbackend.entity.Product;
import com.rest.webservices.onlineshoppingbackend.serviceImpl.CustomerService;
import com.rest.webservices.onlineshoppingbackend.serviceImpl.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1.0/shopping")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@PostMapping("/register")
	public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
		Customer newCustomer = customerService.registerCustomer(customer);
		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String loginId, @RequestParam String password) {
		Customer customer = customerService.login(loginId, password);
		System.out.println(customer);
		if (customer == null) {
			return new ResponseEntity<>("Invalid loginId or password", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@GetMapping("/{customerName}/forgot")
	public ResponseEntity<?> forgotPassword(@PathVariable String customerName) {
		Customer customer = customerService.getCustomerByName(customerName);
		if (customer == null) {
			return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
		}
		// code to send forgot password email
		return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/products/search/{productName}")
	public ResponseEntity<?> searchProductByName(@PathVariable String productName) {
		List<Product> products = productService.searchProductByName(productName);
//		List<Product> products1 = productRepository.findByNameContaining(productName);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PostMapping("/{productName}/add")
	public ResponseEntity<?> addProduct(@PathVariable String productName, @RequestBody Product product) {
		Product newProduct = productService.addProduct(productName, product);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}

	@PutMapping("/{productName}/update/{id}")
	public ResponseEntity<?> updateProductStatus(@PathVariable String productName, @PathVariable Long id,
			@RequestBody String status) {
		Product product = productService.updateProductStatus(productName, id, status);
		if (product == null) {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/{productName}/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable String productName, @PathVariable Long id) {
		productService.deleteProduct(productName, id);
		return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
	}

	// forgotPassword
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestParam String loginId, @RequestParam String email) {
		Customer customer = customerService.getCustomerByLoginIdAndEmail(loginId, email);
		if (customer == null) {
			return new ResponseEntity<>("Invalid loginId or email", HttpStatus.NOT_FOUND);
		}
		// Generate a new password and send it to the customer's email
		String newPassword = generateNewPassword();
		customer.setPassword(newPassword);
		customerService.updateCustomer(loginId, customer);

		// Send email with the new password
		String emailBody = "Your new password is: " + newPassword;
		sendEmail(customer.getEmail(), "Password reset", emailBody);

		return new ResponseEntity<>("Password reset successful. Check your email for the new password.", HttpStatus.OK);
	}

	private String generateNewPassword() {
		// Generate a random string of characters
		// This is just an example, you can use a more secure method to generate
		// passwords
		int length = 8;
		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=";
		Random random = new Random();
		return random.ints(length, 0, allowedChars.length()).mapToObj(allowedChars::charAt).map(Object::toString)
				.collect(Collectors.joining());
	}

	private void sendEmail(String to, String subject, String body) {
		// Code to send email using a third-party service or SMTP
	}

}
