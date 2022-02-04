package com.hivemind.services;

import com.hivemind.entities.Account;
import com.hivemind.entities.Credentials;
import com.hivemind.entities.Customer;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.repositories.AccountRepository;
import com.hivemind.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
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
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: " + customer.getPermanentAccountNumber());
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

    public Customer findCustomerByCredentials(Credentials credentials) throws CustomerNotFoundException {
        List<Customer> customerList = findAllCustomers();
        System.out.println("number of customers found: "+customerList.size());
        List<Customer> customerByCredentials = customerList
                .stream()
                .filter( customer ->
                        customer.getLoginId().equals(credentials.getLoginId()) &&
                                customer.getPassword().equals(credentials.getPassword())
                )
                .collect(Collectors.toList());
        if (customerByCredentials.size() == 0) {
            throw new CustomerNotFoundException("No Customer with username '"+credentials.getLoginId()+"' and password '"+credentials.getPassword()+"'");
        }
        return customerByCredentials.get(0);
    }

    public Customer addLinkedAccountToCustomer(Long pan, long accountNumber) throws CustomerNotFoundException, AccountNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(pan);
        if (!customerOptional.isPresent()) {
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+pan);
        }

        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if(!accountOptional.isPresent()) {
            throw new AccountNotFoundException("BankAccount not found with accountNumber: "+accountNumber);
        }

        Customer customer = customerOptional.get();
        Account account = accountOptional.get();

        List<Account> linkedAccounts = customer.getLinkedAccounts();
        linkedAccounts.add(account);

        List<Customer> linkedCustomer = account.getLinkedCustomer();
        linkedCustomer.add(customer);

        customer.setLinkedAccounts(linkedAccounts);
        account.setLinkedCustomer(linkedCustomer);

        customerRepository.save(customer);
        accountRepository.save(account);

        return customer;
    }

    public List<Account> findAllLinkedAccountsByCustomerPan(Long pan) {
        List<Account> accountList = accountRepository.findAll();
        List<Account> linkedAccounts = new ArrayList<>();

        accountList.forEach( account -> {
            if(account.getLinkedCustomer().stream().anyMatch(
                    customer -> customer.getPermanentAccountNumber().equals(pan))
            ) {
                linkedAccounts.add(account);
            }
        });

        return linkedAccounts;
    }
}
