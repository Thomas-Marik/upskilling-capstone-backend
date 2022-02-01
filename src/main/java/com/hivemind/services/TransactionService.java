package com.hivemind.services;

import com.hivemind.entities.Account;
import com.hivemind.entities.Transaction;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.TransactionNotFoundException;
import com.hivemind.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public Transaction findTransactionById(Long id) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (!transactionOptional.isPresent()) {
            throw new TransactionNotFoundException("Transaction not found with id: "+id);
        }
        return transactionOptional.get();
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction saveTransaction(Transaction transaction) throws AccountNotFoundException {
        Account initiatorAccount = accountService.findAccountById(transaction.getInitiatorAccount().getAccountNumber());
        return (Transaction) transactionRepository.save(transaction);
    }

    public Transaction editTransaction(Transaction transaction) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transaction.getId());
        if (!transactionOptional.isPresent()) {
            throw new TransactionNotFoundException("Transaction not found with id: "+transaction.getId());
        }
        return (Transaction) transactionRepository.save(transaction);
    }

    public Transaction deleteTransaction(Long id) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (!transactionOptional.isPresent()) {
            throw new TransactionNotFoundException("Transaction not found with id: "+id);
        }
        Transaction transaction = transactionOptional.get();
        transactionRepository.deleteById(id);
        return transaction;
    }
}