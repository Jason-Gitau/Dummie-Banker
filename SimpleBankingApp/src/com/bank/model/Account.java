package com.bank.model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


	public abstract class Account{
		
		private String accountNumber;
		private String ownerName;
		private String pin;
		private double balance;
		protected List<Transaction> transactionHistory;
		
			public Account(String accountNumber,String ownerName,String pin,double initialBalance) {
				this.accountNumber = accountNumber;
				this.ownerName= ownerName;
				this.pin =pin;
				this.balance = initialBalance;
				this.transactionHistory=new ArrayList<>();
				
				
				if (initialBalance>0) {
					transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT,initialBalance,"Initial Deposit"));
					
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
	 // Protected method for subclasses to add balance (e.g., for interest)
    protected void addBalance(double amount) {
        this.balance += amount;
    }
	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}
	   
//	
	

//	4. methods( business logic)
//	abstract
	public abstract double calculateFee();
	
	public abstract void applyInterest();
	
	public abstract String getAccountType();
	
//	common methods
	
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
		double fee=calculateFee();
		double totalDeduction = amount + fee;
		
		if(amount>0 && totalDeduction<=this.balance) {
			this.balance-=totalDeduction;
			transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Withdrawal"));
			// Record fee as separate transaction if fee > 0
            if (fee > 0) {
                transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, fee, "Transaction Fee"));
            }
            
            System.out.println("Successfully withdrew: " + amount);
            if (fee > 0) {
                System.out.println("Transaction fee charged: " + fee);
            }
            return true;
			
		}else {
			System.out.println("Invalid withdrawal amount or insufficient funds.");
            return false; // Transaction failed
		}
	}
	
//	method to verify the pin
	public boolean verifyPin(String inputPin) {
		return this.pin.equals(inputPin);
	}
	
    // Print transaction history
    public void printTransactionHistory(int limit) {
        System.out.println("\n--- Transaction History ---");
        System.out.println("Account Type: " + getAccountType());
        int start = Math.max(0, transactionHistory.size() - limit);
        for (int i = start; i < transactionHistory.size(); i++) {
            System.out.println(transactionHistory.get(i));
        }
        System.out.println("---------------------------");
    }
		
	
	
	
//    saving to the file
    
	
//	convert account objects into a single line of text
	public String toFileString() {
		return this.accountNumber+ "|" + this.ownerName + "|" + this.pin + "|" + this.balance;
	}
	
//	create an Account object from a single line of text
	public static Account fromFileString(String line) {
        String[]parts = line.split("\\|");
        
        if (parts.length <5) {
        	return null;
        }
        
        String accountType =parts[0];
        String accNum = parts[1];
        String name =parts[2];
        String pin = parts[3];
        double balance =Double.parseDouble(parts[4]);
        
//        factory patters
        if(accountType.equals("SAVINGS")){
        	double interestRate =(parts.length>5)? Double.parseDouble(parts[5]):0.05;
        	return new SavingAccount(accNum,name,pin,balance,interestRate);
        }else {
        	return new NormalAccount(accNum,name,pin,balance);
        }
	}

    
}
