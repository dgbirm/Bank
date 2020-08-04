/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 30, 2020
 * @version jdk-14
 */
package com.dollarsbank.exceptions;

public class OverdraftException extends Exception {

	private static final long serialVersionUID = -123617544819940871L;

	private int accountID;
	private double currentBalance;
	
	/**
	 * Format message.
	 *
	 * @param accountID      the account ID
	 * @param currentBalance the current balance
	 * @return the formatted error message
	 */
	private static String formatMessage(int accountID, double currentBalance) {
		return String.format("Your transaction is unable to be completed %n"
				+ "because you are not allowed to credit your this account. %n"
				+ "Your current balance for account %d is %.2f", accountID, currentBalance);	
	}
	
	//Constructors
	public OverdraftException(int accountID, double currentBalance) {
		super(formatMessage(accountID, currentBalance));
		this.accountID = accountID;
		this.currentBalance = currentBalance;
	}

	public OverdraftException(int accountID, double currentBalance, Throwable cause) {
		super(formatMessage(accountID, currentBalance), cause);
		this.accountID = accountID;
		this.currentBalance = currentBalance;
	}

	public OverdraftException(int accountID, double currentBalance, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(formatMessage(accountID, currentBalance), cause, enableSuppression, writableStackTrace);
		this.accountID = accountID;
		this.currentBalance = currentBalance;
	}
	
	/**
	 * Gets the account ID.
	 *
	 * @return the account ID
	 */
	public int getAccountID() {
		return this.accountID;
	}
	
	/**
	 * Gets the current balance.
	 *
	 * @return the current balance
	 */
	public double getCurrentBalance() {
		return this.currentBalance;
	}

}
