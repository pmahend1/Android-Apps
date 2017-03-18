package com.uncc.prateek.xml_saxparserdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.SAXException;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncPersonGet extends AsyncTask<String, Void, Person>{
	@Override
	protected Person doInBackground(String... params) {
		String urlString = params[0];
		try {
			URL url = new URL(urlString);			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();			
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream in = con.getInputStream();
				Person person = PersonUtil.PersonSAXParser.parsePerson(in);
				return person;
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
	protected void onPostExecute(Person result) {
		Log.d("AsyncPersonGet", "Person: "+result.toString());
	}	
}