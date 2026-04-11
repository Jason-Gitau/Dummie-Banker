package com.bank.main;

import java.util.Scanner;
import com.bank.service.BankService;
import com.bank.util.FileUtils;
import com.bank.dto.TransferResult;
import com.bank.model.Account;

public class Main {
    
    // Initialize Scanner and Service
    private static Scanner scanner = new Scanner(System.in);
    private static BankService bankService = new BankService();

    public static void main(String[] args) {
        System.out.println("Welcome to JKUAT Simple Banking System!");
        
        boolean running = true;

        while (running) {
            // 1. Display Main Menu
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccountFlow();
                    break;
                case 2:
                    loginFlow();
                    break;
                case 3:
                    running = false;
                    System.out.println("Thank you for banking with us!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
        
        scanner.close();
    }

    // Helper Method: Handle Account Creation
    private static void createAccountFlow() {
    	 System.out.print("Enter Account Number: ");
         String accNum = scanner.next();
         
         System.out.print("Enter Owner Name: ");
         String name = scanner.next();
         
         System.out.print("Enter PIN: ");
         String pin = scanner.next();
         
         // Choose account type
         System.out.println("\nSelect Account Type:");
         System.out.println("1. Normal Account (Has withdrawal fees, No interest)");
         System.out.println("2. Savings Account (No fees, Earns 5% interest)");
         System.out.print("Choose (1 or 2): ");
         
         int typeChoice = scanner.nextInt();
         String accountType = (typeChoice == 2) ? "SAVINGS" : "NORMAL";
         
         System.out.print("Enter Initial Deposit: ");
         double balance = scanner.nextDouble();
         
         bankService.createAccount(accNum, name, pin, balance, accountType);
    }

    // Helper Method: Handle Login and Session
    private static void loginFlow() {
        System.out.print("Enter Account Number: ");
        String accNum = scanner.next();
        
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        
        // Attempt login via Service
        Account loggedInAccount = bankService.login(accNum, pin);
        
        if (loggedInAccount != null) {
            System.out.println("Login Successful! Welcome, " + loggedInAccount.getOwnerName());
            handleUserSession(loggedInAccount);
        } else {
            System.out.println("Login Failed! Check credentials.");
        }
    }
    
    
    // Helper Method: Handle Fund Transfer
    private static void transferFundsFlow(Account senderAccount) {
        System.out.println("\n--- Transfer Funds ---");
        
        System.out.print("Enter Recipient Account Number: ");
        String recipientAccNum = scanner.next();
        
        System.out.print("Enter Amount to Transfer: ");
        
        // Validate amount input
        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid amount!");
            scanner.next(); // consume invalid input
            return;
        }
        
        double amount = scanner.nextDouble();
        
        // Call the service to perform transfer
        TransferResult result = bankService.transferFunds(
            senderAccount.getAccountNumber(), 
            recipientAccNum, 
            amount
        );
        
        // Display result
        System.out.println(result.getMessage());
        
        // Save if successful (service already saves, but this ensures consistency)
        if (result.isSuccessful()) {
            FileUtils.saveAccounts(bankService.getAllAccounts());
        }
    }

    // Helper Method: Handle Logged-In Operations
       private static void handleUserSession(Account account) {
        boolean sessionActive = true;
        
        while (sessionActive) {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View Transaction History");
            System.out.println("5. Apply Interest (Savings Only)");
            System.out.println("6. Transfer Funds");  // ✅ ADD THIS LINE
            System.out.println("7. Logout"); 
            System.out.print("Choose an option: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input!");
                scanner.next();
                continue;
            }
            
            int choice = scanner.nextInt();
            
            switch (choice) {
            case 1:
                System.out.println("Current Balance: " + account.getBalance());
                System.out.println("Account Type: " + account.getAccountType());
                break;
            case 2:
                System.out.print("Enter amount to deposit: ");
                double depositAmount = scanner.nextDouble();
                account.deposit(depositAmount);
                FileUtils.saveAccounts(bankService.getAllAccounts());
                break;
            case 3:
                System.out.print("Enter amount to withdraw: ");
                double withdrawAmount = scanner.nextDouble();
                account.withdraw(withdrawAmount);
                FileUtils.saveAccounts(bankService.getAllAccounts());
                break;
            case 4:
                account.printTransactionHistory(10);
                break;
            case 5: 
                account.applyInterest();
                FileUtils.saveAccounts(bankService.getAllAccounts());
                break;
            case 6:  
                transferFundsFlow(account);
                break;
            case 7:  
                sessionActive = false;
                System.out.println("Logged out.");
                break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}