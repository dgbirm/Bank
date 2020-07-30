/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */

package com.dollarsbank;

import java.sql.ResultSet;

import com.dollarsbank.model.Customer;

/**
 * The Interface CustomerDAO.
 */
public interface CustomerDAO {
	
	/**
	 * Gets the customer.
	 *
	 * @param id the id
	 * @return the customer
	 */
	public Customer getCustomer(int id);
	
	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	public ResultSet getAllCustomers();
	
	/**
	 * Update customer.
	 *
	 * @param Customer the customer
	 * @return true, if successful
	 */
	public boolean updateCustomer(Customer Customer);
	
	/**
	 * Delete customer.
	 *
	 * @param Customer the customer
	 */
	public void deleteCustomer(Customer Customer);
}
