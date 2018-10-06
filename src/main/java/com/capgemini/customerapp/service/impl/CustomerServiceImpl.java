package com.capgemini.customerapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.customerapp.entity.Customer;
import com.capgemini.customerapp.repository.CustomerRepository;
import com.capgemini.customerapp.service.CustomerService;
import com.capgemini.customerapp.service.exception.AuthenticationFailedException;
import com.capgemini.customerapp.service.exception.CustomerNotFoundException;


@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer authentication(Customer customer) throws AuthenticationFailedException, CustomerNotFoundException {
		Optional<Customer> customerFromDb = customerRepository.findById(customer.getCustomerId());
		if (customerFromDb.isPresent()) {
			if (customerFromDb.get().getPassword().equals(customer.getPassword())) {
				return customerFromDb.get();
			}
			throw new AuthenticationFailedException("Invalid Username or password");
		}
		throw new CustomerNotFoundException("Invalid User");
	}

	@Override
	public Customer editCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
		Optional<Customer> customerFromDb = customerRepository.findById(customerId);
		if (customerFromDb.isPresent()) {
			return customerFromDb.get();
		}
		throw new CustomerNotFoundException("Customer Not Found");
		
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
		
	}

}
