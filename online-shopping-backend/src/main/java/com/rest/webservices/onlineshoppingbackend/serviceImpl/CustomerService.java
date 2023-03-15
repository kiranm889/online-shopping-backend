package com.rest.webservices.onlineshoppingbackend.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rest.webservices.onlineshoppingbackend.entity.Customer;
import com.rest.webservices.onlineshoppingbackend.exception.ResourceNotFoundException;
import com.rest.webservices.onlineshoppingbackend.service.CustomerRepository;

@Service("customer")
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public Customer registerCustomer(Customer customer) {
		Optional<Customer> existingCustomerWithEmail = customerRepository.findByEmail(customer.getEmail());
		Optional<Customer> existingCustomerWithLoginId = customerRepository.findByLoginId(customer.getLoginId());

		if (existingCustomerWithEmail.isPresent()) {
			throw new IllegalArgumentException("Email is already in use");
		}

		if (existingCustomerWithLoginId.isPresent()) {
			throw new IllegalArgumentException("Login ID is already in use");
		}

		if (!customer.getPassword().equals(customer.getConfirmPassword())) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		return customerRepository.save(customer);
	}

	public Customer login(String loginId, String password) {
		Optional<Customer> customer = customerRepository.findByLoginId(loginId);

//		List<Customer> customer1=customerRepository.findAll();

		if (customer.isPresent() && customer.get().getPassword().equals(password)) {
			return customer.get();
		}
//		System.out.println(customer+" length of customer details "+customer1.size());
		System.out.println("LogginId " + loginId + " password " + password);
		System.out.println("customer details " + customer.isPresent());
		throw new IllegalArgumentException("Invalid login credentials");
	}

	public void resetPassword(Long customerId, String password) {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (customer.isPresent()) {
			customer.get().setPassword(password);
			customerRepository.save(customer.get());
		} else {
			throw new IllegalArgumentException("Invalid customer ID");
		}
	}

//	 getCustomer By Name
	public Customer getCustomerByName(String name) {
		return customerRepository.findByFirstName(name);
	}

	public Customer getCustomerByLoginIdAndEmail(String loginId, String email) {
		Optional<Customer> optionalCustomer = customerRepository.findByLoginIdAndEmail(loginId, email);
		return optionalCustomer.orElse(null);
	}

	public Customer updateCustomer(String loginId, Customer customer) {
		Optional<Customer> optionalCustomer = Optional.empty();// customerRepository.findByLoginId(loginId);
		if (optionalCustomer.isPresent()) {
			Customer existingCustomer = optionalCustomer.get();
			existingCustomer.setFirstName(customer.getFirstName());
			existingCustomer.setLastName(customer.getLastName());
			existingCustomer.setEmail(customer.getEmail());
			existingCustomer.setPassword(customer.getPassword());
			existingCustomer.setContactNumber(customer.getContactNumber());
			return customerRepository.save(existingCustomer);
		} else {
			throw new ResourceNotFoundException("Customer not found with loginId " + loginId);
		}

	}

}
