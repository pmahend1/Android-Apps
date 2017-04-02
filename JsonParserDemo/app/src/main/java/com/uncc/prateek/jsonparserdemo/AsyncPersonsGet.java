package com.uncc.prateek.jsonparserdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncPersonsGet extends AsyncTask<String, Void, ArrayList<Person>> {
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				// Log.d("demo", sb.toString());
				ArrayList<Person> persons = PersonUtil.PersonsJSONParser
						.parsePersons(sb.toString());
				return persons;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // catch (JSONException e) {
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Person> result) {
		if (result != null) {
			Log.d("demo", result.toString());
		} else {
			Log.d("demo", "null result");
		}

	}
}