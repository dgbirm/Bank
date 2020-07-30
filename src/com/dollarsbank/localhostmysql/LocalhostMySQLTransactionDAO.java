package com.dollarsbank.localhostmysql;

import java.sql.ResultSet;

import com.dollarsbank.model.Transaction;
import com.dollarsbank.TransactionDAO;

public class LocalhostMySQLTransactionDAO implements TransactionDAO {

	@Override
	public ResultSet getTransactionHistory(int numOfTrans) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addTransaction(Transaction Transaction) {
		// TODO Auto-generated method stub
		return false;
	}
}
