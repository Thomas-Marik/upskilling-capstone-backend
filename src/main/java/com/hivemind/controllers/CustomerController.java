package com.hivemind.controllers;


import com.hivemind.entities.Account;
import com.hivemind.entities.Credentials;
import com.hivemind.entities.Customer;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{pan}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long pan) throws CustomerNotFoundException {
        return new ResponseEntity<Customer>(customerService.findCustomerById(pan), HttpStatus.OK);
    }
    @GetMapping("/{pan}/linked")
    public ResponseEntity<List<Account>> getAllLinkedBankAccounts(@PathVariable Long pan) {
        return new ResponseEntity<List<Account>>(customerService.findAllLinkedAccountsByCustomerPan(pan), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<Customer>(customerService.saveCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Customer> editCustomer(@RequestBody Customer customer) throws CustomerNotFoundException, AccountNotFoundException {
        return new ResponseEntity<Customer>(customerService.editCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/{pan}/{accountNumber}")
    public ResponseEntity<Customer> addLinkedAccountToCustomer(@PathVariable Long pan, @PathVariable long accountNumber) throws CustomerNotFoundException, AccountNotFoundException {
        return new ResponseEntity<Customer>(customerService.addLinkedAccountToCustomer(pan,accountNumber),HttpStatus.OK);
    }

    @DeleteMapping("/{pan}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long pan) throws CustomerNotFoundException {
        return new ResponseEntity<Customer>(customerService.deleteCustomer(pan), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody Credentials credentials) throws CustomerNotFoundException {
        return new ResponseEntity<Customer>(customerService.findCustomerByCredentials(credentials), HttpStatus.OK);
    }
}
