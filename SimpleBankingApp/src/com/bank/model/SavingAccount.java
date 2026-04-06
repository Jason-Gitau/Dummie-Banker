package com.bank.model;

public class SavingAccount extends Account {
	private static final double DEFAULT_INTEREST_RATE = 0.05;
	private double interestRate;
	
	public SavingAccount(String accountNumber, String ownerName, String pin, double initialBalance, double interestRate) {
        super(accountNumber, ownerName, pin, initialBalance);
        this.interestRate = interestRate;
    }
	
	// Constructor with default interest rate
    public SavingAccount(String accountNumber, String ownerName, String pin, double initialBalance) {
        this(accountNumber, ownerName, pin, initialBalance, DEFAULT_INTEREST_RATE);
    }
    
    @Override
    public double calculateFee() {
        // Savings accounts have NO withdrawal fees
        return 0.0;
    }
    
    @Override
    public void applyInterest() {
        double balance = getBalance();
        double interest = balance * interestRate;
        
        // Use deposit to add interest (creates transaction record)
        // We need to access protected balance or use a special method
        // For now, we'll add directly via a protected method in Account
        addBalance(interest);
        transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, interest, 
            String.format("Monthly Interest (%.1f%%)", interestRate * 100)));
        
        System.out.println("Interest applied: " + interest);
        System.out.println("New balance: " + getBalance());
    }
    
    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
    
    public double getInterestRate() {
        return interestRate;
    }

}
