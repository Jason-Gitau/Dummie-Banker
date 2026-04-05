package com.bank.model;

import java.util.ArrayList;
import java.util.List;

public class Account { 
	
    private List<Transaction> transactionHistory;
	
//	1. field (state)
//	we use private for encapsulation.only this class can touch them directly
	private String accountNumber;
	private String ownerName;
	private String pin;
	private double balance;
	
	
//	2. constructor
//	this runs when you create a new account object ( new Account(..)
	public Account(String accountNumber,String ownerName,String pin,double initialBalance) {
		this.accountNumber =accountNumber;
		this.ownerName=ownerName;
		this.pin=pin;
		this.balance = initialBalance;
		this.transactionHistory = new ArrayList<>(); // Initialize the list
		
		// Record initial deposit as a transaction
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, initialBalance, "Initial Deposit"));
        }
	}
	
//	3. getters(accessors)
//	allows other classes to read the data safely
	public String getAccountNumber() {
		return accountNumber;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public double getBalance() {
		return balance;
	}

//	4. methods( business logic)
	public void deposit(double amount) {
		if(amount>0) {
			this.balance+=amount;
			transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, amount, "Deposit"));
			System.out.println("Successfully deposited: " + amount);
			
		}else {
			System.out.println("Invalid deposit amount.");
		}
	}
	public boolean withdraw(double amount) {
		if(amount>0 && amount<=this.balance) {
			this.balance-=amount;
			transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Withdrawal"));
			System.out.println("Successfully withdrew: " + amount);
			return true ;
			
		}else {
			System.out.println("Invalid withdrawal amount or insufficient funds.");
            return false; // Transaction failed
		}
	}
	
//	method to verify the pin
	public boolean verifyPin(String inputPin) {
		return this.pin.equals(inputPin);
	}
	
//	convert account objects into a single line of text
	public String toFileString() {
		return this.accountNumber+ "|" + this.ownerName + "|" + this.pin + "|" + this.balance;
	}
	
//	create an Account object from a single line of text
	public static Account fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = line.trim().split("\\|");
        
//        System.out.println("[DEBUG] Parsing: " + line);
//        System.out.println("[DEBUG] Parts count: " + parts.length);
        for (int i = 0; i < parts.length; i++) {
//            System.out.println("[DEBUG] Part " + i + ": '" + parts[i] + "'");
        }
        
        if (parts.length == 4) {
            try {
                String accNum = parts[0].trim();
                String name = parts[1].trim();
                String pin = parts[2].trim();
                double balance = Double.parseDouble(parts[3].trim());
                
                Account acc = new Account(accNum, name, pin, balance);
//                System.out.println("[DEBUG] Successfully created account: " + accNum);
                return acc;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Invalid balance format: " + parts[3]);
                return null;
            }
        } else {
            System.out.println("[ERROR] Expected 4 parts, got " + parts.length);
            return null;
        }
	}
	
    // Get transaction history
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    
    // Print last N transactions
    public void printTransactionHistory(int limit) {
        System.out.println("\n--- Transaction History ---");
        int start = Math.max(0, transactionHistory.size() - limit);
        for (int i = start; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
        System.out.println("---------------------------");
    }
		
}
