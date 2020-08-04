package com.dollarsbank.localhostmysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dollarsbank.model.Transaction;
import com.dollarsbank.TransactionDAO;

public class LocalhostMySQLTransactionDAO implements TransactionDAO {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	public LocalhostMySQLTransactionDAO() {
		conn = LocalhostMySQLDAOFactory.createConnection();
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Transaction> getTransactionHistory(Integer numOfTrans, Integer idAccount) {
		List<Transaction> th = new ArrayList<>();
		Integer counter = 0;
		try {
			rs = stmt.executeQuery(String.format("SELECT * FROM transaction WHERE idAccount=%d "
					+ "ORDER BY idTransaction DESC", idAccount));
			rs.beforeFirst();
			System.out.println("hi im here");
			while(rs.next() && counter < numOfTrans) {
				System.out.println("inside while");
				th.add(new Transaction(rs.getInt("idTransaction"),
									   rs.getDouble("amountTransferred"),
									   rs.getString("destination"),
									   idAccount,
									   rs.getTimestamp("transactionTimestamp")));
				counter++;
			}
			return th;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addTransaction(Transaction Transaction) {
		// TODO Auto-generated method stub
		String ps = "INSERT INTO transaction "
				+ "(transactionTimestamp, amountTransferred, destination, idAccount) VALUES (?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, Transaction.getTs().toString());
			pstmt.setString(2, Transaction.getAmmountTransfered().toString());
			pstmt.setString(3, Transaction.getDestination());
			pstmt.setString(4, Transaction.getAcctID().toString());
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return false;
	}
}
