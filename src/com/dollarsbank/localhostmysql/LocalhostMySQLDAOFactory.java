/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: July 30, 2020
 * @version jdk-14
 */
package com.dollarsbank.localhostmysql;
// AwsMySQL concrete DAO Factory implementation

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.dollarsbank.AccountDAO;
import com.dollarsbank.CustomerDAO;
import com.dollarsbank.DAOFactory;
import com.dollarsbank.TransactionDAO;
import com.mysql.cj.jdbc.Driver;

public class LocalhostMySQLDAOFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/bankapp";
	public static final String USER = "root";
	public static final String PASS = "root";

	// method to create AwsMySQL connections
	public static Connection createConnection() {
		try {
			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			throw new RuntimeException("Error connecting to the database", e);
		}
	}

	public CustomerDAO getCustomerDAO() {
		// LocalhostMySQLCustomerDAO implements CustomerDAO
		return new LocalhostMySQLCustomerDAO();
	}

	@Override
	public AccountDAO getAccountDAO() {
		return new LocalhostMySQLAccountDAO();
	}

	@Override
	public TransactionDAO getTransactionDAO() {
		return new LocalhostMySQLTransactionDAO();
	}
}