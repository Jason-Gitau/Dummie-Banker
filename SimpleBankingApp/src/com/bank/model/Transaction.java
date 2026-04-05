package com.bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
	
//	Enum for transaction ( for type safety)
	public enum Type{
		DEPOSIT,WITHDRAWAL
	}
	
	private Type type;
	private double amount;
	private LocalDateTime date;
	private String description;
	
//	constructor
	public Transaction(Type type, double amount ,String description) {
		this.type =type;
		this.amount= amount;
		this.date =LocalDateTime.now();
		this.description =description;
	}

//	getters
	public Type getType() {
		return type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
//	convert to string for file saving
	// Create Transaction from a file line
    public static Transaction fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length >= 4) {
            try {
                Type type = Type.valueOf(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                java.time.LocalDateTime date = java.time.LocalDateTime.parse(
                    parts[2], 
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                );
                String description = parts[3];
                
                Transaction t = new Transaction(type, amount, description);
                t.date = date; // Override the "now" date with saved date
                return t;
            } catch (Exception e) {
                System.out.println("[ERROR] Failed to parse transaction: " + line);
                return null;
            }
        }
        return null;
    }
	
//	Display format for console
@Override
public String toString() {
    java.time.format.DateTimeFormatter formatter = 
        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    String sign = (type == Type.DEPOSIT) ? "+" : "-";
    
    return String.format("[%s] %s: %s%.2f - %s", 
        date.format(formatter), 
        type, 
        sign, 
        amount, 
        description);
}


	
}
