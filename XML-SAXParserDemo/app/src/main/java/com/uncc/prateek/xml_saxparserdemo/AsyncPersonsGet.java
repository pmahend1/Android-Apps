package com.uncc.prateek.xml_saxparserdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncPersonsGet extends AsyncTask<String, Void, ArrayList<Person>>{
	@Override
	protected ArrayList<Person> doInBackground(String... params) {
		String urlString = params[0];
		try {
			URL url = new URL(urlString);			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();			
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream in = con.getInputStream();
				ArrayList<Person> persons = PersonUtil.PersonsSAXParser.parsePersons(in);
				return persons;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Person> result) {
		Log.d("AsyncPersonsGet", "Persons retrieved:" + result.toString());
	}	
}