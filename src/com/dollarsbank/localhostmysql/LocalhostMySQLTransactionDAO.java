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
		int counter = 0;
		try {
			rs = stmt.executeQuery(String.format("SELECT * FROM transaction WHERE idAccount=%d"
					+ "ORDER BY idTransaction DESC", idAccount));
			rs.beforeFirst();
			while(rs.next() && counter < numOfTrans) {
				th.add(new Transaction(rs.getInt("idTransaction"),
									   rs.getDouble("amountTransferred"),
									   rs.getString("destination"),
									   idAccount));
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
		String ps = "INSERT INTO transaction (amountTransferred, idAccount) VALUES (?,?)";
		try {
			pstmt = conn.prepareStatement(ps);
			pstmt.setString(1, Transaction.getAmmountTransfered().toString());
			pstmt.setString(2, Transaction.getAcctID().toString());
			return pstmt.execute();
		} catch (SQLException e) {
			System.out.println("Issue adding transaction to database");
			e.printStackTrace();
		}
		return false;
	}
}
