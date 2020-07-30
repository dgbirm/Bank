package com.dollarsbank.model;

public enum AccountType {
	CHECKING("Checking") , SAVINGS("Savings");

	private String readable;
	
	AccountType(String rdble) {
		this.readable = rdble;
	}
	public String getReadable() {
		return readable;
	}
}
