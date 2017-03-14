package com.uncc.prateek.xmlpulldemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
/*
	String personUrl = "http://liisp.uncc.edu/~mshehab/api/xml/person.xml";
	String personsUrl = "http://liisp.uncc.edu/~mshehab/api/xml/persons.xml";*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*new AsyncPersonsGet()
				.execute("http://liisp.uncc.edu/~mshehab/api/json/persons.json");*/
        new AsyncPersonsGet().execute("http://liisp.uncc.edu/~mshehab/api/xml/persons.xml");

	}

/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

}
