package com.example.databasenotesdemo;

import java.util.List;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;

public class MainActivity extends Activity {
	private static DataManager dm;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dm = new DataManager(this);
        
        Note note = new Note();
        note.setSubject("Note 1");
        note.setText("This is the text for note 1");
        dm.saveNote(note);
        
        note = new Note();
        note.setSubject("Note 2");
        note.setText("This is the text for note 2");
        dm.saveNote(note);
        
        note = new Note();
        note.setSubject("Note 3");
        note.setText("This is the text for note 3");
        dm.saveNote(note);

        List<Note> notes = dm.getAllNotes();
        
        ListView myListView = (ListView) findViewById(R.id.ListView1);        
        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this, 
        		android.R.layout.simple_list_item_1, android.R.id.text1, notes);        
        myListView.setAdapter(adapter);
    }

	@Override
	protected void onDestroy() {
		dm.close();
		super.onDestroy();
	}
}