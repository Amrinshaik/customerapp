package com.capgemini.customerapp.service;

import java.util.List;

import com.capgemini.customerapp.entity.Customer;
import com.capgemini.customerapp.service.exception.AuthenticationFailedException;
import com.capgemini.customerapp.service.exception.CustomerNotFoundException;

public interface CustomerService {

	public Customer addCustomer(Customer customer);

	public Customer authentication(Customer customer) throws AuthenticationFailedException, CustomerNotFoundException;

	public Customer editCustomer(Customer customer);

	public Customer getCustomerById(int customerId) throws CustomerNotFoundException;

	public void deleteCustomer(Customer customer);

	public List<Customer> getAllCustomers();

}