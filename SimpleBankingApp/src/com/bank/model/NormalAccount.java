package com.bank.model;

public class NormalAccount extends Account {
	
	private static final double WITHDRAWAL_FEE=50.0;
	
	 public NormalAccount(String accountNumber, String ownerName, String pin, double initialBalance) {
	        super(accountNumber, ownerName, pin, initialBalance);
	    }
	 
	 @Override
	    public double calculateFee() {
	        return WITHDRAWAL_FEE;
	    }
	    
    @Override
	    public void applyInterest() {
	        // Normal accounts don't earn interest
	        System.out.println("Normal accounts do not earn interest.");
	    }
	    
    @Override
	    public String getAccountType() {
	        return "NORMAL";
	    }
	    

}
