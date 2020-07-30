package com.dollarsbank.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dollarsbank.AccountDAO;
import com.dollarsbank.CustomerDAO;
import com.dollarsbank.DAOFactory;
import com.dollarsbank.DollarsBankApplication;
import com.dollarsbank.TransactionDAO;
import com.dollarsbank.exceptions.WeekPasswordException;
import com.dollarsbank.model.Customer;

public class ConsoleGuiUtil {

	//Istantiate DAOs
	private static CustomerDAO cDAO = DAOFactory
			.getDAOFactory(DAOFactory.LOCALHOST_MYSQL).getCustomerDAO();
	private static AccountDAO aDAO = DAOFactory
			.getDAOFactory(DAOFactory.LOCALHOST_MYSQL).getAccountDAO();
	private static TransactionDAO tDAO = DAOFactory
			.getDAOFactory(DAOFactory.LOCALHOST_MYSQL).getTransactionDAO();
	

	public static void mainRunner() throws InterruptedException {
		Scanner input = new Scanner(System.in);
		String userValue = "";

		while (true) {
			if (userValue.equals("q")) {
				break;
			}
			System.out.println(" Welcome to THE DollarsBankApp!" + "\n Press i to create a new customer account"
					+ "\n Press ii Login" + "\n Press q to quit");
			System.out.println("Enter your input here:");
			userValue = input.nextLine();
			switch (userValue) {

			case "i":
				newCustomer(input);
				break;

			case "q":
				System.out.println("Bye now!");
				break;
			default:
				System.out.println("That is not a valid input, silly! " + "Try again pls...");
				break;
			}
			System.out.println("\n*******************\n");
			TimeUnit.SECONDS.sleep(3);
		}
		input.close();
	}

	private static void newCustomer(Scanner input) {
		String customerName;
		String address;
		String city;
		String country;
		Customer c;
		String pw = "";
		String pw2;

		boolean pwBad = true;

		// get vars from user
		System.out.println("Enter the details for your new account below");
		System.out.println("Full Name:");
		customerName = input.nextLine();

		System.out.println("Current Residential Address:");
		address = input.nextLine();

		System.out.println("City of Residence:");
		city = input.nextLine();

		System.out.println("Country of Residence");
		country = input.nextLine();

		// create Customer to add to db with null as id (its auto-generated in sql)
		c = new Customer((Integer) null, customerName, address, city, country);
		
		//make a password
		System.out.println("\nPlease enter a password for your new account.\n" + "Your password must:\n"
				+ "(a) use at least eight characters \n" + "(b) use at least one capital letter \n"
				+ "(c) use at least 1 special character of the following: \n" + "\t @#$%^&+=");
		while (true) {
			while (pwBad) {
				System.out.println("Enter your new password");
				pw = input.nextLine();
				pwBad = !isValidPassword(pw);
				try {
					if (pwBad) {
						throw new WeekPasswordException();
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}

			System.out.println("Please re-enter your new password");
			pw2 = input.nextLine();

			if (pw.equals(pw2)) {
				break;
			} else {
				System.out.println("Your passwords dont match. Please try again");
				pwBad = true;
			}
		}
		
		//send data to db using DAO
		if(cDAO.addCustomer(c)) {
			c = cDAO.getCustomer(customerName);
			
			//add login credentials
			DollarsBankApplication.addLoginCredenitals(c.getCustomerID(), pw);
			System.out.println(String.format("Congradulations! Your new Customer ID is %d %n"
					+ "please take note as us will use this ID to login in the future", c.getCustomerID()));
		}
		else {
			System.out.println("Something went wrong connecting to the database. \n"
					+ "No new customer data was created.");
		}
		
	}
	
	private static void newAccount(Scanner input) {
		//initial deposit amount
		Double initialDeposit = 0.00;
		System.out.println("Your almost done! Please enter the amount you"
				+ "will initially deposit into your new account:");
		initialDeposit = Double.parseDouble(input.nextLine());
	}

	private static boolean isValidPassword(String pw) {
		// length
		if (pw.length() < 8) {
			return false;
		}
		// use Regex to check other conditions
		String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
		Pattern rgx = Pattern.compile(pattern);
		if (null == rgx.matcher(pw)) {
			return false;
		}
		return true;

	}
}
