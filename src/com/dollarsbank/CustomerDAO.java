/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Aug 3, 2020
 * @version jdk-14
 */

package com.dollarsbank;

import java.sql.ResultSet;
import java.util.List;

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
	public Customer getCustomer(Integer id);
	
	/**
	 * Gets the most recent customer.
	 *
	 * @return the most recent customer added to db
	 */
	public Customer getMostRecentCustomer();
	
	/**
	 * Gets the customer accounts.
	 *
	 * @param idCustomer the id customer
	 * @return the customer accounts
	 */
	List<Integer> getCustomerAccounts(Integer idCustomer);
	
	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	public ResultSet getAllCustomers();
	
	/**
	 * Adds the customer.
	 *
	 * @param Customer the customer
	 * @return true, if successful
	 */
	public boolean addCustomer(Customer Customer);
	
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
