/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: July 29, 2020
 * @version jdk-14
 */

package com.dollarsbank;

import com.dollarsbank.localhostmysql.LocalhostMySQLDAOFactory;


//Abstract class DAO Factory
public abstract class DAOFactory {

	// List of DAO types supported by the factory
	public static final int LOCALHOST_MYSQL = 1;
	
	// There will be a method for each DAO that can be 
	// created. The concrete factories will have to 
	// implement these methods.
	public abstract CustomerDAO getCustomerDAO();
	public abstract AccountDAO getAccountDAO();
	public abstract TransactionDAO getTransactionDAO();

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case LOCALHOST_MYSQL: 
	        return new LocalhostMySQLDAOFactory();
	    default: 
	        return null;
		}
	}
}