/*
Assignment : 01 
File Name : MapComparator.java
Full name of the student : Prateek Mahendrakar
*/

package assignment01;

import java.util.Map;

public class MapComparator {
	 private final String key;

	    public MapComparator(String key)
	    {
	        this.key = key;
	    }

	    public int compare(Map<String, String> first,
	                       Map<String, String> second)
	    {
	        // TODO: Null checking, both for maps and values
	        String firstValue = first.get(key);
	        String secondValue = second.get(key);
	        return firstValue.compareTo(secondValue);
	    }
}
