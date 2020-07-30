/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */
package com.dollarsbank.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The Class Transaction.
 */
public class Transaction {
	private final int transactionID;
	private double ammountTransfered;
	private String destination="";
	private int acctID;
	private Timestamp ts;
	/**
	 * @param transactionID
	 * @param ammountTransfered
	 * @param destination
	 * @param acctID
	 * @param ts Time the transaction object was constructed
	 */
	
	//Constructors
	public Transaction(int transactionID, double ammountTransfered, String destination, int acctID, Timestamp ts) {
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.destination = destination;
		this.acctID = acctID;
		this.ts = ts;
	}
	
	public Transaction(int transactionID, double ammountTransfered, String destination, int acctID) {
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.destination = destination;
		this.acctID = acctID;
		this.ts = Timestamp.valueOf(LocalDateTime.now());
	}
	

	public Transaction(int transactionID, double ammountTransfered, int acctID) {
		this.transactionID = transactionID;
		this.ammountTransfered = ammountTransfered;
		this.acctID = acctID;
		this.ts = Timestamp.valueOf(LocalDateTime.now());
	}

	//toString
	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", ammountTransfered=" + ammountTransfered
				+ ", destination=" + destination + ", acctID=" + acctID + ", ts=" + ts + "]";
	}
	
	
	//GetterSetters
	public synchronized double getAmmountTransfered() {
		return ammountTransfered;
	}

	public synchronized void setAmmountTransfered(double ammountTransfered) {
		this.ammountTransfered = ammountTransfered;
	}

	public synchronized String getDestination() {
		return destination;
	}

	public synchronized void setDestination(String destination) {
		this.destination = destination;
	}

	public synchronized Timestamp getTs() {
		return ts;
	}

	public synchronized void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public synchronized int getTransactionID() {
		return transactionID;
	}

	public synchronized int getAcctID() {
		return acctID;
	}
	
	
}
