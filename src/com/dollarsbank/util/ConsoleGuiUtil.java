package com.dollarsbank.util;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.dollarsbank.AccountDAO;
import com.dollarsbank.CustomerDAO;
import com.dollarsbank.DAOFactory;
import com.dollarsbank.DollarsBankApplication;
import com.dollarsbank.TransactionDAO;
import com.dollarsbank.exceptions.OverdraftException;
import com.dollarsbank.exceptions.WeekPasswordException;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.AccountType;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;

public class ConsoleGuiUtil {

	//Instantiate DAOs
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
					System.out.println(e.getMessage());
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
				DollarsBankApplication.addLoginCredentials(c.getCustomerID(), pw);
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
			try {
				customerID = Integer.parseInt(input.nextLine());
				System.out.println("Please enter your password");
				pw = input.nextLine();
				if(pw.hashCode() == DollarsBankApplication.getHashedPw(customerID)) {
					return customerID;
				}
				System.out.println("Your ID or password is incorrect. Please try again");
			} catch (Exception e) {
				System.out.println("Your ID or password is incorrect. Please try again");
			}
		}
		System.out.println("Too many incorrect login attempts. Returning to main menu");
		return null;
	}
	
	private static boolean isValidPassword(String pw) {
		// length
		if (pw.length() < 8) {
			System.out.println("your password is not long enough");
			return false;
		}
		// use Regex to check other conditions
		String pattern = "^(?=.*[@#$%^&+=])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
		if (!pw.matches(pattern)) {
			System.out.println("This is not a valid password. Please try again");
			return false;
		}
		return true;

	}
	
	private static void loggedInRunner(Scanner input, Integer customerID) throws InterruptedException {
		String loggedInput = "";
		while (true) {
			if (loggedInput.equals("b")) {
				break;
			}
			System.out.println("\n----- Welcome Back -----");
			System.out.println("What would you like to do today?"
					+ "\n i -> open a new bank account"
					+ "\n ii -> make a monitary transaction"
					+ "\n iii -> transfer funds between accounts"
					+ "\n iv -> view transaction history"
					+ "\n v -> display customer information"
					+ "\n vi -> change password"
					+ "\n b -> sign out");
			loggedInput = input.nextLine();
			switch (loggedInput) {
			case "i":
				newAccount(input, customerID);
				break;
			case "ii":
				makeTransaction(input, customerID);
				break;
			case "iii":
				System.out.println("This functionality has not yet been implemented \n"
						+ "Please try something else");
				break;
			case "iv":
				getTransactionHistory(input, customerID);
				break;
			case "v":
				System.out.println("Your customer information is as follows:");
				System.out.println(cDAO.getCustomer(customerID).toString());
				break;
			case "vi":
				updatePassword(input, customerID);
				break;
			case "b":
				System.out.println("Logging out...");
				break;
			default:
				System.out.println("That is not a valid input, silly! Try again pls...");
				break;
			}
		}
	}
	
	private static void newAccount(Scanner input, Integer customerID) {//logic could be a little cleaner here
		//initial deposit amount
		String accountType;
		Double initialDeposit=0.00;
		Set<Integer> usrSet = new HashSet<>();
		usrSet.add(customerID);
		Enum<AccountType> accntType=null;
		Account a;
		
		System.out.println("Please enter the amount you \n"
				+ "will initially deposit into your new account:");
		
		try {
			initialDeposit = Double.parseDouble(input.nextLine());
			//accnt type
			for (int i = 0; i < 3; i++) {
				System.out.println("What kind of account do you want?"
						+ "\n c -> checking"
						+ "\n s -> savings"
						+ "\n b -> brokerage");
				accountType = input.nextLine();
				if (accountType.equals("c")) {
					accntType = AccountType.CHECKING;
					break;
				}
				else if (accountType.equals("s")) {
					accntType = AccountType.SAVINGS;
					break;
				}
				else if (accountType.equals("b")) {
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
		} catch (NumberFormatException e) {
			System.out.println("That is not a valid amount");
		} catch (Exception e ) {
			System.out.println("You have encountered an unknown error");
		}
	}
	
	private static void makeTransaction(Scanner input, Integer customerID) {
		String transTypeInput;
		String transType;
		Integer acct;
		Double depAmount;
		Transaction t;
		Double currentBalance;
		
		System.out.println("Ok! would you like to make a deposit or withdrawal? Type:"
				+ "\n d for deposit"
				+ "\n w for withrawal");
		transTypeInput = input.nextLine().toLowerCase();
		switch (transTypeInput) {
		case "w": {
			transType = "withdraw";
			break;
		}
		case "d": {
			transType = "deposit";
			break;
		}
		default:
			System.out.println("That was not a valid input. Please try again");
			return;
		}
		System.out.println(String.format("Transaction type %s selected %n", transType));
		
		List<Integer> acctList = cDAO.getCustomerAccounts(customerID);
		if(null==acctList) {
			System.out.println("You have no accounts to deposit into at this time. \n"
					+ "Please make an account before you make a transaction");
			return;
		}
		System.out.println("Please select the account to deposit to or withdaw from \n");
		for (Integer acctID : acctList) {
			System.out.println(String.format("%d for account with id %d",
					acctList.indexOf(acctID), acctID));
		}
		try {
			acct = acctList.get(Integer.parseInt(input.nextLine()));
			System.out.println(String.format("Ok %1$sing with account id %2$d %n"
					+ "How much would you like to %1$s?", transType, acct));
			depAmount = Double.parseDouble(input.nextLine());
			
			System.out.println(String.format("Are you sure you would like to %s %.2f %n"
					+ "for account with id %d? [y/n]", transType, depAmount, acct));
			if("y".equalsIgnoreCase(input.nextLine())) {
				if ("w".equals(transTypeInput)) {
					depAmount= 0.0 - depAmount; //make negative for withdrawal
					currentBalance = aDAO.getAccount(acct).getAcctBalance();
					if(currentBalance + depAmount < 0.00) {
						throw new OverdraftException(acct, currentBalance);
					}
				}
				t = new Transaction((Integer) null, depAmount, acct);
				if (tDAO.addTransaction(t)) {
					if (aDAO.updateAccountBalance(acct, depAmount)) {
						System.out.println("Your transaction has been completed successfully!");
						System.out.println(String.format("Your account information for account %d is now: %n"
								+ "%s", acct, aDAO.getAccount(acct).toString()));
						return;
					}
				}
				System.out.println("Your transaction could not be completed successfully :( .\n "
						+ "Please check your connection and try again");

			}
			else {
				System.out.println("No transaction was executed");
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("You have not given a valid input. Please try again");
		} catch (OverdraftException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void getTransactionHistory(Scanner input, Integer customerID) {
		Integer acct;
		Integer numOfTrans;
		
		List<Integer> acctList = cDAO.getCustomerAccounts(customerID);
		if(null==acctList) {
			System.out.println("You have no accounts at this time. \n"
					+ "You must have an account before you can have a transaction history...");
			return;
		}
		System.out.println("Please select the account of which you'd like a history:\n");
		try {
			for (Integer acctID : acctList) {
				System.out.println(String.format("%d for account with id %d",
						acctList.indexOf(acctID), acctID));
			}
			acct = acctList.get(Integer.parseInt(input.nextLine()));
			System.out.println(String.format("Ok account id %d selected. %n"
					+ "How many past transactions would you like to see", acct));
			numOfTrans = Integer.parseInt(input.nextLine());
			System.out.println(numOfTrans);
			System.out.println("---TRANSACTION HISTORY---");
			List<Transaction> th = tDAO.getTransactionHistory(numOfTrans, acct);
			th.forEach(t -> System.out.println(t));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("You have not given a valid input. Please try again");
		}
	}
	
	private static void updatePassword(Scanner input, Integer customerID) {
		boolean pwBad = true;
		String pw = null;
		String pw2;
		// make a password
		System.out.println("\nPlease chose a new password to log in with.\n" + "Your password must:\n"
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
					System.out.println(e.getMessage());
				}
			}

			System.out.println("Please re-enter your new password");
			pw2 = input.nextLine();

			if (pw.equals(pw2)) {
				break;
			} else {
				System.out.println("Your passwords dont match. Please try again"
						+ "\n-----");
				pwBad = true;
			}
		}
		DollarsBankApplication.addLoginCredentials(customerID, pw);
		System.out.println("Your password has been updated successfully!");
	}
}
