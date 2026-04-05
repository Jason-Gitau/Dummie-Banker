package com.bank.model;

public class StudentAccount extends Account {
//	constructor
	public StudentAccount(String accNumber, String ownerName, String pin, double initialBalance) {
		super(accNumber, ownerName, pin, initialBalance);
		

	}
//	method Overwriting
	
	@Override
	public boolean withdraw(double amount) {
		  // No fee check! Just direct withdrawal.
        // We can still use parent logic if we wanted, but here we simplify.
        if (amount > 0 && amount <= this.getBalance()) {
            // Note: Since balance is private in parent, we rely on parent methods 
            // or protected access. For now, imagine we have a protected setter 
            // or we call super.withdraw() but skip the fee logic.
            // To make this work perfectly, 'balance' in Account might need to be 'protected' 
            // or we add a protected method in Account called 'addBalance()'.
            // This is a great design challenge!
            return super.withdraw(amount); 
        }
        return false;
	}
	

}
