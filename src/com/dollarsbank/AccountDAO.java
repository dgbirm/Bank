/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */
package com.dollarsbank;

import com.dollarsbank.model.Account;

/**
 * The Interface AccountDAO.
 */
public interface AccountDAO {
	
	/**
	 * Gets the account.
	 *
	 * @param idAccount the id account
	 * @return the account
	 */
	public Account getAccount(Integer idAccount);
	
	/**
	 * Adds the account.
	 *
	 * @param Account the account
	 * @return true, if successful
	 */
	public boolean addAccount(Account Account);
	
	/**
	 * Adds the customer account.
	 *
	 * @param customerID the customer ID
	 * @param acctID     the account ID
	 * @return true, if successful
	 */
	public boolean addCustomer_Account(Integer customerID, Integer acctID);
	
	/**
	 * Gets the most recent account.
	 *
	 * @return the most recent account
	 */
	public Account getMostRecentAccount();
	
	/**
	 * Update account.
	 *
	 * @param Account the account
	 * @return true, if successful
	 */
	public boolean updateAccount(Account Account);
	
	/**
	 * Update account balance.
	 *
	 * @param idAccount     the id account
	 * @param depositAmount the deposit amount
	 * @return true, if successful
	 */
	public boolean updateAccountBalance(Integer idAccount, Double depositAmount);
	
	/**
	 * Delete account.
	 *
	 * @param acctID the account ID
	 */
	public void deleteAccount(Integer acctID);
}
