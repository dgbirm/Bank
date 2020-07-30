/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 30, 2020
 * @version jdk-14
 */
package com.dollarsbank.exceptions;

/**
 * The Class WeekPasswordException. Thrown if the user tries to generate
 * a week password. A week password is one that does not:
 * 		(a) use at least eight characters
 * 		(b) use at least one capital letter
 * 		(c) use at least 1 special character of the following:
 * 			@#$%^&+=
 */
public class WeekPasswordException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3139860536614955213L;

	/**
	 * Format message.
	 *
	 * @return the string formatted error message
	 */
	private static String formatMessage() {
		return "The password you've entered does not meet the the necessary \n"
				+ "strength requirements. Please make sure to \n"
				+ "(a) use at least eight characters \n"
				+ "(b) use at least one capital letter \n"
				+ "(c) use at least 1 special character of the following: \n"
				+ "\t @#$%^&+=";
	}
	
	/**
	 * Instantiates a new week password exception.
	 */
	public WeekPasswordException() {
		super(formatMessage());
	}

	/**
	 * Instantiates a new week password exception.
	 *
	 * @param cause the cause
	 */
	public WeekPasswordException(Throwable cause) {
		super(formatMessage(),cause);
	}

	/**
	 * Instantiates a new week password exception.
	 *
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public WeekPasswordException(Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(formatMessage(), cause, enableSuppression, writableStackTrace);
	}

}
