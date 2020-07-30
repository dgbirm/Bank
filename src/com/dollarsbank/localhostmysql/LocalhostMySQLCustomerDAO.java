/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */
package com.dollarsbank.localhostmysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dollarsbank.CustomerDAO;
import com.dollarsbank.model.Customer;

public class LocalhostMySQLCustomerDAO implements CustomerDAO {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public LocalhostMySQLCustomerDAO() {
		conn = LocalhostMySQLDAOFactory.createConnection();
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Customer getCustomer(Integer id) {
		Customer c = null;
		try {
			rs= stmt.executeQuery("SELECT * FROM customer WHERE idCustomer=" + id);
			rs.first();
			c = new Customer(rs.getInt("idCustomer"),
					 rs.getString("custName"),
					 rs.getString("custAddress"),
					 rs.getString("custCity"),
					 rs.getString("custCountry"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	@Override
	public Customer getCustomer(String customerName) {
		Customer c = null;
		try {
			rs= stmt.executeQuery("SELECT * FROM customer WHERE custName=" + customerName);
			rs.first();
			c = new Customer(rs.getInt("idCustomer"),
					 rs.getString("custName"),
					 rs.getString("custAddress"),
					 rs.getString("custCity"),
					 rs.getString("custCountry"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public ResultSet getAllCustomers() {
		// TODO Auto-generated method stub
		try {
			rs = stmt.executeQuery("SELECT * FROM customer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	@Override
	public boolean addCustomer(Customer Customer) {
		String ps = "INSERT INTO customer (custName, custAddress, custCity, custCountry) VALUES (?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, Customer.getCustomerName());
			pstmt.setString(2, Customer.getAddress());
			pstmt.setString(3, Customer.getCity());
			pstmt.setString(4, Customer.getCountry());
			pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Issue adding customer");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCustomer(Customer Customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteCustomer(Customer Customer) {
		// TODO Auto-generated method stub

	}

}
