package com.dollarsbank.model;

public enum AccountType {
	CHECKING("checking") , SAVINGS("savings"), BROKERAGE("brokerage");

	private String readable;
	
	AccountType(String rdble) {
		this.readable = rdble;
	}
	
	@Override
	public String toString() {
		return readable;
	}
}
