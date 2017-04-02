package com.uncc.prateek.jsonparserdemo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonUtil {	
	static public class PersonsJSONParser{		
		static ArrayList<Person> parsePersons(String jsonString) throws JSONException{
			ArrayList<Person> persons = new ArrayList<Person>();	
			JSONArray personsJSONArray = new JSONArray(jsonString);
			for(int i=0; i<personsJSONArray.length(); i++){
				JSONObject personJSONObject = personsJSONArray.getJSONObject(i);
				Person person = new Person(personJSONObject);	
				persons.add(person);
			}
			return persons;
		}
	}
}