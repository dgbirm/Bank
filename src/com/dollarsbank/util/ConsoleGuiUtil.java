package com.dollarsbank.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.dollarsbank.AccountDAO;
import com.dollarsbank.CustomerDAO;
import com.dollarsbank.DAOFactory;
import com.dollarsbank.DollarsBankApplication;
import com.dollarsbank.TransactionDAO;
import com.dollarsbank.exceptions.WeekPasswordException;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.AccountType;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;

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
		Integer customerID;

		while (true) {
			if (userValue.equals("q")) {
				break;
			}
			System.out.println(" Welcome to THE DollarsBankApp!" + "\n Press i to create a new customer account"
					+ "\n Press ii to log in" + "\n Press q to quit");
			System.out.println("Enter your input here:");
			userValue = input.nextLine();
			switch (userValue) {

			case "i":
				newCustomer(input);
				break;
			
			case "ii":
				customerID = login(input);
				if(null == customerID) {
					break;
				}else {
					loggedInRunner(input, customerID);
				}
				
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
		//not sure sync is done correctly/not really necessary at this point
		synchronized(cDAO) {
			if(cDAO.addCustomer(c)) {
				// not safe for multiple access to db
				c = cDAO.getMostRecentCustomer();
				
				//add login credentials
				DollarsBankApplication.addLoginCredenitals(c.getCustomerID(), pw);
				System.out.println(String.format("Congradulations! Your new Customer ID is %d %n"
						+ "please take note as you will use this ID to login in the future", c.getCustomerID()));
			}
			else {
				System.out.println("Something went wrong connecting to the database. \n"
						+ "No new customer data was created.");
			}
		}	
	}
	
	private static Integer login(Scanner input) {
		Integer customerID;
		String pw;
		
		for (int i = 0; i < 3; i++) {
			System.out.println("Please enter your Customer ID");
			customerID = Integer.parseInt(input.nextLine());
			System.out.println("Please enter your password");
			pw = input.nextLine();
			if(pw.hashCode() == DollarsBankApplication.getHashedPw(customerID)) {
				return customerID;
			}
			System.out.println("Your ID or password is incorrect. Please try again");
		}
		System.out.println("Too many incorrect login attempts. Returning to main menu");
		return null;
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
	
	private static void loggedInRunner(Scanner input, Integer customerID) throws InterruptedException {
		String loggedInput = "";
		while (true) {
			if (loggedInput.equals("q")) {
				break;
			}
			System.out.println("\n----- Welcome Back -----");
			System.out.println("What would you like to do today?"
					+ "\n i -> open a new bank account"
					+ "\n ii -> make a deposit"
					+ "\n iii -> make a withdrawl"
					+ "\n iv -> transfer funds between accounts"
					+ "\n v -> view recent transactions"
					+ "\n vi -> display customer information"
					+ "\n vii -> change password"
					+ "\n q -> sign out");
			loggedInput = input.nextLine();
			switch (loggedInput) {
			case "i":
				newAccount(input, customerID);
				break;
			case "ii":
				
			case "q":
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("That is not a valid input, silly! Try again pls...");
				break;
			}
		}
	}
	
	private static void newAccount(Scanner input, Integer customerID) {
		//initial deposit amount
		String accountType;
		Double initialDeposit;
		Set<Integer> usrSet = new HashSet<>();
		usrSet.add(customerID);
		Enum<AccountType> accntType=null;
		Account a;
		Transaction t;
		
		System.out.println("Please enter the amount you \n"
				+ "will initially deposit into your new account:");
		initialDeposit = Double.parseDouble(input.nextLine());
		//accnt type
		for (int i = 0; i < 3; i++) {
			System.out.println("What kind of account do you want?"
					+ "\n c -> checking"
					+ "\n s -> savings"
					+ "\n b -> brokerage");
			
			accountType = input.nextLine();
			if (accountType == "c") {
				accntType = AccountType.CHECKING;
				break;
			}
			else if (accountType == "s") {
				accntType = AccountType.SAVINGS;
				break;
			}
			else if (accountType == "b") {
				accntType = AccountType.BROKERAGE;
				break;
			}
			else {
				System.out.println("Please read the prompt and try again");
			}
		}
		if(accntType == null) {
			System.out.println("Too many bad inputs. Exiting ...");
			return;
		}
		//send to db using DAO
		a = new Account((Integer) null, usrSet, accntType, initialDeposit);
		synchronized (aDAO) {
			if(aDAO.addAccount(a)) {
				// not safe for multiple access to db
				a = aDAO.getMostRecentAccount();
				if (aDAO.addCustomer_Account(customerID, a.getAcctID())) {
					a = aDAO.getMostRecentAccount();
					System.out.println(String.format("Congradulations! Your new Account ID is %d %n"
							+ "please take note as you may need this in the future", a.getAcctID()));
				}
				else {
					aDAO.deleteAccount(a.getAcctID());
					System.out.println("Something went wrong connecting to the database. \n"
							+ "No new account was created.");
				}
				
			}
			else {
				System.out.println("Something went wrong connecting to the database. \n"
						+ "No new account was created.");
			}
		}
	}
}
