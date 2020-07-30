/**
 * 
 */
package com.dollarsbank.localhostmysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dollarsbank.CustomerDAO;
import com.dollarsbank.model.Customer;

/**
 * @author Dan Birmingham >> dgbirm@gmail.com
 * Start Date: Jun 18, 2020
 * Last Updated: 
 * Description:
 *
 */
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
	public Customer getCustomer(int id) {
		// TODO Auto-generated method stub
		Customer c = new Customer(-1);
		try {
			rs= stmt.executeQuery("SELECT * FROM customerTable WHERE customerNumber=" + id);
			rs.first();
			c = new Customer(rs.getInt("customerNumber"),
					 rs.getString("customerName"),
					 rs.getString("address"),
					 rs.getString("city"),
					 rs.getString("country"));
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
			rs = stmt.executeQuery("SELECT * FROM customerTable");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
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
