package com.bank.service;

import java.util.HashMap;
import java.util.Map;
import com.bank.model.Account;
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
    public void createAccount(String accNumber, String ownerName, String pin, double initialBalance) {
        // Check if account number already exists
        if (accounts.containsKey(accNumber)) {
            System.out.println("Account number already exists!");
            return;
        }

        // Create new Account object
        Account newAccount = new Account(accNumber, ownerName, pin, initialBalance);
        
        
        // Store it in the Map (Key = accNumber, Value = newAccount)
        accounts.put(accNumber, newAccount);
        
        //save to file after creation
        FileUtils.saveAccounts(accounts);
        System.out.println("Account created successfully for " + ownerName);
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