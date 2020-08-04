/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */
package com.dollarsbank;

import java.util.List;

import com.dollarsbank.model.Transaction;


/**
 * The Interface TransactionDAO.
 */
public interface TransactionDAO {
	
	/**
	 * Gets the transaction history.
	 *
	 * @param numOfTrans the num of trans
	 * @param idAccount  the id account
	 * @return the transaction history
	 */
	public List<Transaction> getTransactionHistory(Integer numOfTrans, Integer idAccount);
	
	/**
	 * Adds the transaction.
	 *
	 * @param Transaction the transaction
	 * @return true, if successful
	 */
	public boolean addTransaction(Transaction Transaction);
	
}
