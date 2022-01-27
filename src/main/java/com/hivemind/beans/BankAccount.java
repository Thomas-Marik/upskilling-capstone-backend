package com.hivemind.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BankAccount {
    @Id
    int accountNum;
    int customerId;
    double balance;

    public BankAccount() {
        this.accountNum=100000001;
        this.balance=0;
        this.customerId=0;
    }

    public BankAccount(int accountNum, int customerId, double balance) {
        this.accountNum = accountNum;
        this.customerId = customerId;
        this.balance = balance;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
