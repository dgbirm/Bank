/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 29, 2020
 * @version jdk-14
 */

package com.dollarsbank;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class DollarsBankApplication is the main "runner" class for this console-based
 * application.
 */
public class DollarsBankApplication {
	
	private static Map<Integer, Integer> loginCredenitals = new HashMap<>();

	public static synchronized Integer getHashedPw(Integer id) {
		return loginCredenitals.get(id);
	}

	public static synchronized void addLoginCredenitals(Integer id, String pw) {
		loginCredenitals.put(id, pw.hashCode());
	}
	
	
}
