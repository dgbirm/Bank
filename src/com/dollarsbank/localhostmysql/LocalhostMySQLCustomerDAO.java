package com.dollarsbank.localhostmysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			rs= stmt.executeQuery("SELECT * FROM customer WHERE idCustomer=" + id.toString());
			rs.first();
			c = new Customer(rs.getInt("idCustomer"),
					 rs.getString("custName"),
					 rs.getString("custAddress"),
					 rs.getString("custCity"),
					 rs.getString("custCountry"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Customer getMostRecentCustomer() {
		Customer c = null;
		try {
			rs= stmt.executeQuery("SELECT * FROM customer WHERE idCustomer="
					+ "(SELECT MAX(idCustomer) FROM customer)");
			rs.first();
			c = new Customer(rs.getInt("idCustomer"),
					 rs.getString("custName"),
					 rs.getString("custAddress"),
					 rs.getString("custCity"),
					 rs.getString("custCountry"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	@Override
	public List<Integer> getCustomerAccounts(Integer idCustomer){
		List<Integer> custAcctIDs = new ArrayList<>();
		//get the account owners
		try {
			rs = stmt.executeQuery(String.format("SELECT * FROM customer_account WHERE idCustomer=%d", idCustomer));
			rs.beforeFirst();
			while(rs.next()) {
				custAcctIDs.add(rs.getInt("idAccount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0==custAcctIDs.size() ? null : custAcctIDs;
	}

	@Override
	public ResultSet getAllCustomers() {
		try {
			rs = stmt.executeQuery("SELECT * FROM customer");
		} catch (SQLException e) {
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
			return true;
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
