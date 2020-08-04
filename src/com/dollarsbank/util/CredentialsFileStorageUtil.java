/*
 * Copyright (c) 2020 as part of Bank, All rights reserved.
 * @author Dan Birmingham. Please reach out to dgbirm@gmail.com
 * Date generated: Jul 30, 2020
 * @version jdk-14
 */
package com.dollarsbank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CredentialsFileStorageUtil {

	/////////////////////////////////////////
	///////// File Streaming ////////////////
	/////////////////////////////////////////

	public static synchronized void writeStateToFile(Map<Integer, Integer> loginCredentials) {
		// Prepare output file
		File outFile = new File(Paths.get("loginCredentials.txt").toAbsolutePath().toString());

		try {
			// make streams
			FileOutputStream fos = new FileOutputStream(outFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// write map states
			oos.writeObject(loginCredentials);
			oos.flush();

			// close streams
			oos.close();
			fos.close();
		} catch (Exception e) {
			System.out.println("There was an error exporting the EMS state:");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Read state from file.
	 *
	 * @return the map of login credentials
	 */
	@SuppressWarnings("unchecked")
	public static Map<Integer, Integer> readStateFromFile() {

		Map<Integer,Integer> loginCredentials = new HashMap<>();
		File inFile = new File(Paths.get("loginCredentials.txt").toAbsolutePath().toString());
		if(inFile.exists()) {
			try {
				FileInputStream fis = new FileInputStream(inFile);
				ObjectInputStream ois = new ObjectInputStream(fis);

				loginCredentials = (HashMap<Integer, Integer>) ois.readObject();

				ois.close();
				fis.close();
			} catch (Exception e) {
				System.out.println("There was an error importing the EMS state:");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return loginCredentials;
	}
}
