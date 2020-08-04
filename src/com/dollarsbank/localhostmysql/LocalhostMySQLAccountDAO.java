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
	public Account getAccount(Integer idAccount) {
		Account a = null;
		try {
			rs= stmt.executeQuery("SELECT * FROM account WHERE idAccount=" + idAccount.toString());
			rs.first();
			a = new Account(rs.getInt("idAccount"),
					new HashSet<Integer>(),
					AccountType.valueOf(rs.getString("acctType")),
					rs.getDouble("acctBalance"));
			getAllCustomersOnAnAccount(a);
		} catch (SQLException e) {
			System.out.println("issue getting account from the db");
			e.printStackTrace();
		}
		return a;
	}

	@Override
	public boolean addAccount(Account Account) {
		String ps = "INSERT INTO account (acctBalance, acctType) VALUES (?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, Account.getAcctBalance().toString());
			pstmt.setString(2, Account.getAcctType().name());
			pstmt.execute();
			return true;
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
			pstmt.execute();
			return true;
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
			rs= stmt.executeQuery("SELECT * FROM account WHERE idAccount="
					+ "(SELECT MAX(idAccount) FROM account)");
			rs.first();
			a = new Account(rs.getInt("idAccount"),
					new HashSet<Integer>(),
					AccountType.valueOf(rs.getString("acctType")),
					rs.getDouble("acctBalance"));
			getAllCustomersOnAnAccount(a);
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
	public boolean updateAccountBalance(Integer idAccount, Double depositAmount) {
		String ps = "UPDATE account SET acctBalance=acctBalance+? where idAccount=?";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, depositAmount.toString());
			pstmt.setString(2, idAccount.toString());
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Issue updateing balance ");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void deleteAccount(Integer acctID) {
		// TODO Auto-generated method stub

	}
	private void getAllCustomersOnAnAccount(Account a) {
		//get the account owners
		try {
			rs = stmt.executeQuery(String.format("SELECT * FROM customer_account WHERE idAccount=%d", a.getAcctID()));
			rs.beforeFirst();
			while(rs.next()) {
				a.getAcctCustomerIDs().add(rs.getInt("idCustomer"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
