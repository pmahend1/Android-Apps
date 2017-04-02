package com.uncc.prateek.jsonparserdemo;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	String personsJSONUrl = "http://liisp.uncc.edu/~mshehab/api/json/persons.json";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new AsyncPersonsGet().execute(personsJSONUrl);
	}
}