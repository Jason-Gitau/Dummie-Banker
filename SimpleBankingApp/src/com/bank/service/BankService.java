package com.bank.service;

import java.util.HashMap;

import java.util.Map;
import com.bank.model.Account;
import com.bank.model.NormalAccount;
import com.bank.model.SavingAccount;

import com.bank.util.FileUtils;

public class BankService {
    
    // 1. The Collection (Data Storage)
    // We use the Interface 'Map' and Implementation 'HashMap'
    // This is Polymorphism!
    private Map<String, Account> accounts = new HashMap<>();
    
    //constructor: Load data immediately when Service starts
    public BankService() {
//    	System.out.println("[DEBUG] BankService initializing...");
    	FileUtils.loadAccounts(accounts);
//    	System.out.println("[DEBUG] BankService loaded " + accounts.size() + " accounts.");
    }
    // Add this to BankService.java
    public Map<String, Account> getAllAccounts() {
        return accounts;
    }

    // 2. Method to Create an Account
    public void createAccount(String accNumber, String ownerName, String pin, double initialBalance,String accountType) {
    	if (accounts.containsKey(accNumber)) {
            System.out.println("Account number already exists!");
            return;
        }

        Account newAccount;
        
        // Factory pattern: Create correct account type
        if (accountType.equalsIgnoreCase("SAVINGS")) {
            newAccount = new SavingAccount(accNumber, ownerName, pin, initialBalance);
        } else {
            newAccount = new NormalAccount(accNumber, ownerName, pin, initialBalance);
        }
        
        accounts.put(accNumber, newAccount);
        FileUtils.saveAccounts(accounts);
        System.out.println(accountType + " account created successfully for " + ownerName);  
    }

    // 3. Method to Find an Account
    public Account getAccount(String accNumber) {
        return accounts.get(accNumber);
    }

    // 4. Method to Authenticate (Login)
    public Account login(String accNumber, String pin) {
        Account account = accounts.get(accNumber);
        
        // Check if account exists AND pin matches
        if (account != null && account.verifyPin(pin)) {
            return account;
        } else {
            return null; // Login failed
        }
    }
    
//    this ensures transaction are saved when they happen
    public void deposit(String accNumber, double amount) {
    	Account acc = accounts.get(accNumber);
    	if(acc !=null) {
    		acc.deposit(amount);
    		FileUtils.saveAccounts(accounts);
    	}
    }
    
    public void withdraw(String accNumber, double amount) {
    	Account acc = accounts.get(accNumber);
    	if(acc !=null) {
    		acc.withdraw(amount);
    		FileUtils.saveAccounts(accounts);
    	}
    }
    
    
    // Helper to check total accounts (for debugging)
    public int getTotalAccounts() {
        return accounts.size();
    }
}