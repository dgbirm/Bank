package com.dollarsbank.localhostmysql;
// AwsMySQL concrete DAO Factory implementation

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.dollarsbank.CustomerDAO;
import com.dollarsbank.DAOFactory;
import com.mysql.cj.jdbc.Driver;

public class LocalhostMySQLDAOFactory extends DAOFactory {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/bankApp";
	public static final String USER = "admin";
	public static final String PASS = "admin123";

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
}