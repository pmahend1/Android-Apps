package com.uncc.prateek.xml_saxparserdemo;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {
	String personUrl = "http://liisp.uncc.edu/~mshehab/api/xml/person.xml";
	String personsUrl = "http://liisp.uncc.edu/~mshehab/api/xml/persons.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new AsyncPersonGet().execute(personUrl);
		new AsyncPersonsGet().execute(personsUrl);
	}
}