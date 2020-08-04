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
	public Account getAccount(Integer idAccount);
	public boolean addAccount(Account Account);
	public boolean addCustomer_Account(Integer customerID, Integer acctID);
	public Account getMostRecentAccount();
	public boolean updateAccount(Account Account);
	public boolean updateAccountBalance(Integer idAccount, Double depositAmount);
	public void deleteAccount(Integer acctID);
}
