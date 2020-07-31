package com.dollarsbank.localhostmysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import com.dollarsbank.AccountDAO;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.AccountType;

public class LocalhostMySQLAccountDAO implements AccountDAO {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public LocalhostMySQLAccountDAO() {
		conn = LocalhostMySQLDAOFactory.createConnection();
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Account getAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAccount(Account Account) {
		String ps = "INSERT INTO account (acctBalance, acctType) VALUES (?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, Account.getAcctBalance().toString());
			pstmt.setString(2, Account.getAcctType().name());
			return pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Issue adding account");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean addCustomer_Account(Integer customerID, Integer acctID) {
		String ps = "INSERT INTO customer_account (idCustomer, idAccount) VALUES (?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, customerID.toString());
			pstmt.setString(2, acctID.toString());
			return pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Issue connecting customer to account");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Account getMostRecentAccount() {
		Account a = null;
		
		try {
			rs= stmt.executeQuery("SELECT * FROM account WHERE idAccount=MAX(idAccount)");
			rs.first();
			a = new Account(rs.getInt("idAccount"),
					new HashSet<Integer>(),
					AccountType.valueOf(rs.getString("acctType")),
					rs.getDouble("acctBalance"));
			//get the account owners
			rs = stmt.executeQuery(String.format("SELECT * FROM customer_account WHERE idAccount=%d", a.getAcctID()));
			rs.beforeFirst();
			while(rs.next()) {
				a.getAcctCustomerIDs().add(rs.getInt("idCustomer"));
			}
			
		} catch (SQLException e) {
			System.out.println("issue getting most recent account added to db");
			e.printStackTrace();
		}
		return a;
		
	}

	@Override
	public boolean updateAccount(Account Account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAccount(Integer acctID) {
		// TODO Auto-generated method stub

	}	
}
