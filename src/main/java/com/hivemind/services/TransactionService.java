package com.hivemind.services;



import com.hivemind.entities.Account;
import com.hivemind.entities.Transaction;
import com.hivemind.exceptions.AccountNotFoundException;
import com.hivemind.exceptions.InsufficientFundsException;
import com.hivemind.exceptions.TransactionNotFoundException;
import com.hivemind.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    public Transaction saveTransaction(Transaction transaction) throws AccountNotFoundException, InsufficientFundsException {
//        Account initiatorAccount = accountService.findAccountById(transaction.getInitiatorAccount().getAccountNumber());
//        transaction.setInitiatorAccount(initiatorAccount);
//
//        if (transaction.getTransactionType().equals("WITHDRAWAL")) {
//            if (initiatorAccount.getBalance() < transaction.getAmount()) {
//                throw new InsufficientFundsException("Tried to withdraw "+transaction.getAmount()+" from account with only "+initiatorAccount.getBalance());
//            } else {
//                initiatorAccount.setBalance(initiatorAccount.getBalance()-transaction.getAmount());
//                accountService.editAccount(initiatorAccount);
//            }
//        } else if (transaction.getTransactionType().equals("DEPOSIT")) {
//            initiatorAccount.setBalance(initiatorAccount.getBalance()+transaction.getAmount());
//            accountService.editAccount(initiatorAccount);
//        }  else if (transaction.getTransactionType().equals("TRANSFER")) {
//            if (initiatorAccount.getBalance() < transaction.getAmount()) {
//                throw new InsufficientFundsException("Tried to withdraw "+transaction.getAmount()+" from account with only "+initiatorAccount.getBalance());
//            } else {
//                Account recipientAccount = accountService.findAccountById(transaction.getRecipientAccountNumber());
//                initiatorAccount.setBalance(initiatorAccount.getBalance()-transaction.getAmount());
//                recipientAccount.setBalance(recipientAccount.getBalance()+transaction.getAmount());
//                accountService.editAccount(initiatorAccount);
//                accountService.editAccount(recipientAccount);
//                transactionRepository.save(transaction);
//            }
//        }
//        return (Transaction) transactionRepository.save(transaction);
//    }

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

    public List<Transaction> findAllTransactionsByAccountNumber(Long accountNumber) {
        List<Transaction> transactionList = findAllTransactions();
        List<Transaction> transactionListFiltered = transactionList
                .stream()
                .filter( transaction ->
                        Objects.equals(transaction.getInitiatorAccount().getAccountNumber(), accountNumber) ||
                                Objects.equals(transaction.getRecipientAccountNumber(), accountNumber)
                )
                .collect(Collectors.toList());
        return transactionListFiltered;
    }

    public List<Transaction> findAllTransactionsWithinWindow(Long timeA, Long timeB) {
        List<Transaction> allTransactions = findAllTransactions();
        List<Transaction> allTransactionsFiltered = allTransactions
                .stream()
                .filter( transaction ->
                        transaction.getDateTimeOfTransaction() > timeA &&
                                transaction.getDateTimeOfTransaction() < timeB)
                .collect(Collectors.toList());
        return allTransactionsFiltered;
    }
}
