package com.dollarsbank.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class Account implements Serializable {

	private static final long serialVersionUID = -776361010511187135L;
	
	private final int acctID;
	private Set<Integer> acctCustomerIDs;
	private String acctPass;
	private double acctBalance=0.0;
	private Enum<AccountType> acctType = AccountType.CHECKING;
	
	//Constructors
	/**
	 * @param acctID: Unique id of the account
	 * @param acctCustomerIDs: IDs of customers associated with the account
	 * @param acctPass: Password for the account
	 * @param acctBalance: Balance for the account. If not given, defaults to 0
	 * @param acctType: type of the account (checking or savings)
	 */
	public Account(int acctID, Set<Integer> acctCustomerIDs, String acctPass,
			Enum<AccountType> acctType, double acctBalance) {
		this.acctID = acctID;
		this.acctCustomerIDs = acctCustomerIDs;
		this.acctPass = acctPass;
		this.acctType = acctType;
		this.acctBalance = acctBalance;
	}
	
	public Account(int acctID, Set<Integer> acctCustomerIDs, String acctPass,
			Enum<AccountType> acctType) {
		this.acctID = acctID;
		this.acctCustomerIDs = acctCustomerIDs;
		this.acctPass = acctPass;
		this.acctType = acctType;
	}
	
	public Account(int acctID, Set<Integer> acctCustomerIDs, String acctPass) {
		this.acctID = acctID;
		this.acctCustomerIDs = acctCustomerIDs;
		this.acctPass = acctPass;
	}

	

	//toString
	@Override
	public String toString() {
		final int maxLen = 5;
		return "Account [acctID=" + acctID + ", acctCustomerIDs="
				+ (acctCustomerIDs != null ? toString(acctCustomerIDs, maxLen) : null) + ", acctPass=" + acctPass
				+ ", acctBalance=" + acctBalance + ", acctType=" + acctType + "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	//GetterSetters
	/**
	 * @return the acctCustomerIDs
	 */
	public synchronized Set<Integer> getAcctCustomerIDs() {
		return acctCustomerIDs;
	}

	/**
	 * @return the acctPass
	 */
	public synchronized String getAcctPass() {
		return acctPass;
	}

	/**
	 * @param acctPass the acctPass to set
	 */
	public synchronized void setAcctPass(String acctPass) {
		this.acctPass = acctPass;
	}

	/**
	 * @return the acctBalance
	 */
	public synchronized double getAcctBalance() {
		return acctBalance;
	}

	/**
	 * @param acctBalance the acctBalance to set
	 */
	public synchronized void setAcctBalance(double acctBalance) {
		this.acctBalance = acctBalance;
	}

	/**
	 * @return the acctType
	 */
	public synchronized Enum<AccountType> getAcctType() {
		return acctType;
	}

	/**
	 * @param acctType the acctType to set
	 */
	public synchronized void setAcctType(Enum<AccountType> acctType) {
		this.acctType = acctType;
	}

	/**
	 * @return the acctID
	 */
	public synchronized int getAcctID() {
		return acctID;
	}
	
}
