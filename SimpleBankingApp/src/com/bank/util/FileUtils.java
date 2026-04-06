package com.bank.util;

import java.io.*;
import java.util.Map;
import com.bank.model.Account;
import com.bank.model.Transaction;

public class FileUtils {
	
	private static final String File_Name= "bank_data.txt";
	
	
//	save all accounts to the file
	public static void saveAccounts(Map<String,Account>accounts) {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(File_Name)) ){
			for (Account acc: accounts.values()) {
				// Write account info
				writer.write(acc.toFileString());
				writer.newLine();
				
				// Write transaction history (indented with tab)
                for (Transaction t : acc.getTransactionHistory()) {
                    writer.write("\t" + t.toString());
                    writer.newLine();
                }
			}
//			System.out.println("[DEBUG] Saved " + accounts.size() + " accounts to file.");
			
		}catch(IOException e){
			System.out.println("[system] Error saving data: " + e.getMessage());
			
		}
	}

//	load all accounts from file
	public static Map<String, Account>loadAccounts(Map<String,Account> accounts){
		File file = new File(File_Name);
		if(!file.exists()) {
//			System.out.println("[DEBUG] No existing data file found. Starting fresh.");
			return accounts; // if the file does not exist it returns an empty map
		}
		
//		System.out.println("[DEBUG] File found! Loading accounts...");
			
			try(BufferedReader reader = new BufferedReader(new FileReader(File_Name))){
				String line;
				
	            Account currentAccount = null;
				
				while((line = reader.readLine()) != null) {
					
					
					// Skip empty lines
	                if (line.trim().isEmpty()) {
//	                    System.out.println("[DEBUG] Line " + lineCount + " is empty, skipping.");
	                    continue;
	                }
	                
	             // Check if this is a transaction line (starts with tab)
	                if (line.startsWith("\t")) {
	                    if (currentAccount != null) {
	                        Transaction t = Transaction.fromFileString(line.trim());
	                        if (t != null) {
	                            currentAccount.getTransactionHistory().add(t);
	                        }
	                    }
	                } else {
	                    // This is an account line
	                    Account acc = Account.fromFileString(line);
	                    if (acc != null) {
	                        accounts.put(acc.getAccountNumber(), acc);
	                        currentAccount = acc;
//	                        System.out.println("[DEBUG] Loaded account: " + acc.getAccountNumber());
	                    }
	                }
	                
//	                System.out.println("[DEBUG] Reading line " + lineCount + ": " + line);
					
					
					Account acc =Account.fromFileString(line);
					if(acc != null) {
						accounts.put(acc.getAccountNumber(), acc);
						
//						System.out.println("[DEBUG] Loaded account: " + acc.getAccountNumber());
					}else {
//	                    System.out.println("[DEBUG] Failed to parse line " + lineCount);
	                }
				}
//				System.out.println("[DEBUG] Loaded " + successCount + " accounts from " + lineCount + " lines.");
			}catch(IOException e) {
				System.out.println("[system] error loading data: "+ e.getMessage());
				e.printStackTrace();
			}
			
		
		return accounts;
	}
}
