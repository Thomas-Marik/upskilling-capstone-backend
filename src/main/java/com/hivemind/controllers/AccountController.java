package com.hivemind.controllers;


import com.hivemind.entities.Account;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.CustomerNotFoundException;
import com.hivemind.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    @Autowired
    AccountController(AccountService accountService) {
        this.AccountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountNumber) throws AccountNotFoundException {
        return new ResponseEntity<Account>(accountService.findAccountById(accountNumber), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<List<Account>>(accountService.findAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/bypan/{pan}")
    public ResponseEntity<List<Account>> getAllAccountsByPAN(@PathVariable Long pan) {
        return new ResponseEntity<List<Account>>(accountService.findAllAccountsByPan(pan), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) throws CustomerNotFoundException {
        return new ResponseEntity<Account>(accountService.saveAccount(account), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Account> editAccount(@RequestBody Account bankAccount) throws AccountNotFoundException {
        return new ResponseEntity<Account>(AccountService.editAccount(account), HttpStatus.OK);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountNumber) throws AccountNotFoundException{
        return new ResponseEntity<Account>(AccountService.deleteAccount(accountNumber), HttpStatus.OK);
    }
}
