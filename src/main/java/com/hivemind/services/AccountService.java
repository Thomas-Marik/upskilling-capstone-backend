package com.hivemind.services;

import com.hivemind.entities.Account;
import com.hivemind.entities.Customer;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account findAccountById(Long accountNumber) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("BankAccount not found with accountNumber: "+accountNumber);
        }
        return accountOptional.get();
    }

    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account) throws CustomerNotFoundException {
        Customer customer = customerService.findCustomerById(account.getCustomer().getPermanentAccountNumber());
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public Account editAccount(Account account) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(account.getAccountNumber());
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("Account not found : "+account.getAccountNumber());
        }
        return accountRepository.save(account);
    }

    public Account deleteAccount(Long accountNumber) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("Account not found : "+accountNumber);
        }
        Account account = accountOptional.get();
        accountRepository.deleteById(accountNumber);
        return account;
    }
}