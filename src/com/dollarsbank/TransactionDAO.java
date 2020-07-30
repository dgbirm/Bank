/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */
package com.dollarsbank;

import java.sql.ResultSet;

import com.dollarsbank.model.Transaction;

/**
 * The Interface TransactionDAO.
 */
public interface TransactionDAO {
	public ResultSet getTransactionHistory(int numOfTrans);
	public boolean addTransaction(Transaction Transaction);
}
