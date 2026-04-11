package com.bank.dto;

/**
 * Data Transfer Object (DTO) for fund transfer operations.
 * Encapsulates the result of a transfer including success status,
 * messages, fees, and balance information.
 */
public class TransferResult {
    
    // ===== FIELDS =====
    
    private boolean success;
    private String message;
    private double amount;
    private double fee;
    private double senderNewBalance;
    private String senderAccountNumber;
    private String recipientAccountNumber;
    
    // ===== CONSTRUCTORS =====
    
    // Constructor for SUCCESSFUL transfer
    public TransferResult success(String senderAcc, String recipientAcc, 
                                   double amount, double fee, double newBalance) {
        this.success = true;
        this.senderAccountNumber = senderAcc;
        this.recipientAccountNumber = recipientAcc;
        this.amount = amount;
        this.fee = fee;
        this.senderNewBalance = newBalance;
        this.message = String.format("✅ Transfer Successful! KSh %.2f sent to Account %s. Fee: KSh %.2f. New Balance: KSh %.2f",
            amount, recipientAcc, fee, newBalance);
        return this; // Return this for method chaining
    }
    
    // Constructor for FAILED transfer
    public TransferResult failure(String message) {
        this.success = false;
        this.message = "❌ Transfer Failed: " + message;
        this.amount = 0;
        this.fee = 0;
        this.senderNewBalance = 0;
        this.senderAccountNumber = "";
        this.recipientAccountNumber = "";
        return this; // Return this for method chaining
    }
    
    // ===== GETTERS =====
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public double getFee() {
        return fee;
    }
    
    public double getSenderNewBalance() {
        return senderNewBalance;
    }
    
    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }
    
    public String getRecipientAccountNumber() {
        return recipientAccountNumber;
    }
    
    // ===== HELPER METHODS =====
    
    /**
     * Check if transfer was successful
     */
    public boolean isSuccessful() {
        return this.success;
    }
    
    /**
     * Get formatted result for console display
     */
    @Override
    public String toString() {
        return message;
    }
}