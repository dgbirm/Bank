/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */

package com.dollarsbank;

import java.util.HashMap;
import com.dollarsbank.util.*;

/**
 * The Class DollarsBankApplication is the main "runner" class for this console-based
 * application.
 */
public class DollarsBankApplication {
	
	private static HashMap<Integer, Integer> loginCredentials;;

	public static synchronized Integer getHashedPw(Integer id) {
		return loginCredentials.get(id);
	}

	public static synchronized void addLoginCredentials(Integer id, String pw) {
		loginCredentials.put(id, pw.hashCode());
	}
	public static void main(String[] args) {
		loginCredentials = CredentialsFileStorageUtil.readStateFromFile();
		try {
			ConsoleGuiUtil.mainRunner();
		} catch (InterruptedException e) {
			System.out.println("Application failed");
			e.printStackTrace();
		} finally {
			CredentialsFileStorageUtil.writeStateToFile(loginCredentials);
		}
	}
}
