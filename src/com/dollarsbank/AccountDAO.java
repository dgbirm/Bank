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
	public Account getAccount();
	public boolean updateAccount(Account Account);
	public void deleteAccount(Account Account);
}
