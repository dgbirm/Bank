/**
 * 
 */
package com.dollarsbank;

import com.dollarsbank.localhostmysql.LocalhostMySQLDAOFactory;

/**
 * @author Dan Birmingham >> dgbirm@gmail.com
 * Start Date: Jun 18, 2020
 * Last Updated: 
 * Description:
 *
 */
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