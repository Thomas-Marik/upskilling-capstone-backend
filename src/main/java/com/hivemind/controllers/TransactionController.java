package com.hivemind.controllers;


import com.hivemind.entities.Transaction;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.InsufficientFundsException;
import com.hivemind.exceptions.TransactionNotFoundException;
import com.hivemind.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) throws TransactionNotFoundException {
        return new ResponseEntity<Transaction>(transactionService.findTransactionById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<List<Transaction>>(transactionService.findAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/byaccount/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAllTransactionsByAccountNumber(@PathVariable Long accountNumber) {
        return new ResponseEntity<List<Transaction>>(transactionService.findAllTransactionsByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @GetMapping("/window/{timestampA}/{timestampB}")
    public ResponseEntity<List<Transaction>> getAllTransactionsWithinWindow(@PathVariable Long timestampA, @PathVariable Long timestampB) {
        return new ResponseEntity<List<Transaction>>(transactionService.findAllTransactionsWithinWindow(timestampA,timestampB), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) throws AccountNotFoundException, InsufficientFundsException {
        return new ResponseEntity<Transaction>(transactionService.saveTransaction(transaction), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Transaction> editTransaction(@RequestBody Transaction transaction) throws TransactionNotFoundException {
        return new ResponseEntity<Transaction>(transactionService.editTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) throws TransactionNotFoundException {
        return new ResponseEntity<Transaction>(transactionService.deleteTransaction(id), HttpStatus.OK);
    }
}
