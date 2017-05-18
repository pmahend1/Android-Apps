/*
Assignment : 01 
File Name : InClassOne.java
Full name of the student : Prateek Mahendrakar
*/
package assignment01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class InClassOne {

	private static List<String> userList = new ArrayList<String>();

	public static void main(String[] args) {
		readFileAtPath("userList1.txt");
	}

	public static void readDuplicates(List<String> userList) {
		HashSet<String> set = new HashSet<String>();
		System.out.println(
				"*******************Duplicate users Unsorted (Key + Entrire Line format)*********************");
		Map<String, String> map = new HashMap<String, String>();

		for (String userString : userList) {

			if (!set.add(userString)) {

				User user = new User(userString);
				map.put(user.getLastName(), userString);

			}
		}

		Set keys = map.keySet();
		List<String> sortedDuplicateUsers = new ArrayList<String>();

		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			String value = (String) map.get(key);

			System.out.println(key + " +  " + value);
		}
		System.out.println("******************************************************");
		
		/* Sorting using TreeMaps 
		 * converting map into treemap helps in sorting*/
		Map<String, String> treeMap = new TreeMap<String, String>(map);
		System.out.println();
		
		//Printing key values which are sorted
		System.out.println("***********************Sorted Duplicate Users( Only Last name)*********************");
		for (String str : treeMap.keySet()) {
			System.out.println(str);
			sortedDuplicateUsers.add(str);
		}
		System.out.println("******************************************************");
		System.out.println();
		
		//printing whole line of user
		System.out.println(
				"***********************Duplicate user values sorted per LastName in comma separated list (as in file)*********************");
		for (String str : sortedDuplicateUsers) {
			System.out.println(map.get(str));
		}

	}

	public static void readFileAtPath(String filename) {
		// Lets make sure the file path is not empty or null
		System.out.println(filename);
		if (filename == null || filename.isEmpty()) {
			System.out.println("Invalid File Path");
			return;
		}
		String filePath = System.getProperty("user.dir") + "/" + filename;
		System.out.println("File path :" + filePath);
		BufferedReader inputStream = null;
		// We need a try catch block so we can handle any potential IO errors
		try {
			try {

				inputStream = new BufferedReader(new FileReader(filePath));
				String lineContent = null;
				// Loop will iterate over each line within the file.
				// It will stop when no new lines are found.
				while ((lineContent = inputStream.readLine()) != null) {
					// System.out.println("Found the line: " + lineContent);

					userList.add(lineContent);
					User user = new User(lineContent);
				}
				// for(String line : userList){
				// System.out.println(line);
				// }

				readDuplicates(userList);
			}
			// Make sure we close the buffered reader.
			finally {
				if (inputStream != null)
					inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
