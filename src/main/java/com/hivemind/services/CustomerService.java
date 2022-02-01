package com.hivemind.services;


import com.hivemind.entities.Customer;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(Long personalAccountNumber) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(personalAccountNumber);
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+personalAccountNumber);
        }
        return customerOptional.get();
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer editCustomer(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(customer.getPermanentAccountNumber());
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+customer.getPermanentAccountNumber());
        }
        return customerRepository.save(customer);
    }

    public Customer deleteCustomer(Long personalAccountNumber) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(personalAccountNumber);
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+personalAccountNumber);
        }
        Customer customer = customerOptional.get();
        customerRepository.deleteById(personalAccountNumber);
        return customer;
    }
}