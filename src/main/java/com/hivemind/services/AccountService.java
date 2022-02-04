package com.hivemind.services;

import com.hivemind.entities.Account;
import com.hivemind.entities.Customer;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Customer customer = customerService.findCustomerById(Account.getCustomer().getPermanentAccountNumber());
        account.setCustomer(customer);
        return accountRepository.save(account);
    }

    public Account editAccount(Account account) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(Account.getAccount());
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("BankAccount not found with accountNumber: "+account.getAccountNumber());
        }
        return accountRepository.save(account);
    }

    public Account deleteAccount(Long accountNumber) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if (!accountOptional.isPresent()) {
            throw new AccountNotFoundException("BankAccount not found with accountNumber: "+accountNumber);
        }
        Account account = accountOptional.get();
        accountRepository.deleteById(Account.getAccountNumber());
        return account;
    }

    public List<Account> findAllAccountsByPan(Long pan) {
        List<Account> accountList = findAllAccounts();
        List<Account> accountListFiltered = accountList
                .stream()
                .filter( bankAccount -> Objects.equals(bankAccount.getCustomer().getPermanentAccountNumber(), pan))
                .collect(Collectors.toList());
        return accountListFiltered;
    }
}
