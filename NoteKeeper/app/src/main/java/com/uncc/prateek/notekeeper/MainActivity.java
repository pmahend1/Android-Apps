package com.uncc.prateek.notekeeper;
/*
        a. Assignment : InClass 7
        b. File Name. : MainActivity.java
        c. Group Name : Group 06
        d. Students in  group : Prateek Mahendrakar , Siva Ram Praneeth Vemulapalli
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvNoteText;
    Spinner spinner;
    Button addButton;
    //private static DatabaseDataManager dm;
    private static DataManager dm;

    private ArrayList<Note> noteList  = new ArrayList<Note>();
    final String LOG_TEXT = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        tvNoteText = (TextView) findViewById(R.id.note_text);
        spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setPrompt("Priority");

        addButton = (Button) findViewById(R.id.button_add);

        addButton.setOnClickListener(this);

        dm = new DataManager(this);

/*        Note note = new Note();
        note.setPriority("high");
        note.setNote("Note");
        note.setTime("dd");
        note.setStatus("dd");
        //note.set
        dm.saveNote(note);*/
        List<Note> dbNotes = dm.getAllNotes();
        noteList = new ArrayList<Note>(dbNotes);
        if(dbNotes!=null){
            NoteListAdapter adapter = new NoteListAdapter(this,R.layout.itemlayout,new ArrayList<>(dbNotes),dm);
            adapter.setNotifyOnChange(true);
            ListView lv = (ListView) findViewById(R.id.listView_notes);
            lv.setAdapter(adapter);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.opt_show_all: {
                Log.d(LOG_TEXT, "Show all clicked");

                return true;
            }
            case R.id.opt_show_completed:{
                Log.d(LOG_TEXT,"opt_show_completed clicked");
                ArrayList<Note> completedList = new ArrayList<Note>() ;
                for(Note note : noteList){
                    if(note.getStatus().equals("completed")){
                        completedList.add(note);
                    }
                }
                NoteListAdapter adapter = new NoteListAdapter(this,R.layout.itemlayout,new ArrayList<>(completedList),dm);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_notes);
                lv.setAdapter(adapter);
                return true;

            }

            case R.id.opt_show_pending: {
                Log.d(LOG_TEXT, "opt_show_pending clicked");
                ArrayList<Note> pendingList = new ArrayList<Note>() ;
                for(Note note : noteList){
                    if(note.getStatus().equals("pending")){
                        pendingList.add(note);
                    }
                }
                NoteListAdapter adapter = new NoteListAdapter(this,R.layout.itemlayout,new ArrayList<>(pendingList),dm);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_notes);
                lv.setAdapter(adapter);
                return true;
            }
            case R.id.opt_sort_by_priority:
                Log.d(LOG_TEXT,"opt_sort_by_priority clicked");
                ArrayList<Note> sortPriorityList = new ArrayList<Note>() ;
                Collections.sort(sortPriorityList, new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        String p1 ,p2;
                        int i1,i2;
                         p1 = o1.getPriority();
                         p2 = o2.getPriority();
                        if(p1.equals("High")){
                            i1=2;
                        }else if(p1.equals("Low")){
                            i1=0;
                        }else{
                            i1=1;
                        }
                        if(p2.equals("High")){
                            i2=2;
                        }else if(p1.equals("Low")){
                            i2=0;
                        }else{
                            i2=1;
                        }
                        return i1-i2;
                    }
                });
                NoteListAdapter adapter = new NoteListAdapter(this,R.layout.itemlayout,new ArrayList<>(sortPriorityList),dm);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_notes);
                lv.setAdapter(adapter);
                return true;
            case R.id.opt_sort_by_time: {
                Log.d(LOG_TEXT, "opt_sort_by_time clicked");
                ArrayList<Note> sortTimeList = new ArrayList<Note>();
                Collections.sort(sortTimeList, new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        String p1, p2;
                        int i1, i2;
                        p1 = o1.getPriority();
                        p2 = o2.getPriority();
                        if (p1.equals("High")) {
                            i1 = 2;
                        } else if (p1.equals("Low")) {
                            i1 = 0;
                        } else {
                            i1 = 1;
                        }
                        if (p2.equals("High")) {
                            i2 = 2;
                        } else if (p1.equals("Low")) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        return i1 - i2;
                    }
                });
                //NoteListAdapter adapter = new NoteListAdapter(this, R.layout.itemlayout, new ArrayList<>(sortTimeList));
                //adapter.setNotifyOnChange(true);
                //ListView lv = (ListView) findViewById(R.id.listView_notes);
                //lv.setAdapter(adapter);
                return true;
            }
            default:
                Log.d(LOG_TEXT,"default clicked");
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        int id  = v.getId();

        if(id==R.id.button_add){

            String  noteTextStr = tvNoteText.getText().toString();
            String priority = String.valueOf(spinner.getSelectedItem());

            if(noteTextStr==null || noteTextStr.isEmpty()){
                Toast.makeText(this, "Note cant be blank", Toast.LENGTH_SHORT).show();
            }
            else if(priority.equals("Priority")){
                Toast.makeText(this, "Please select a priority", Toast.LENGTH_SHORT).show();
            }else{
                Note newNote = new Note();
                newNote.setNote(noteTextStr);
                newNote.setPriority(priority);
                newNote.setStatus("pending");
                Date date = new Date();
                newNote.setTime(date.toString());

                Log.d(LOG_TEXT,newNote.toString());

                dm.saveNote(newNote);
                List<Note> dbNotes = dm.getAllNotes();
                noteList = new ArrayList<Note>(dbNotes);

                NoteListAdapter adapter = new NoteListAdapter(this,R.layout.itemlayout,new ArrayList<>(dbNotes),dm);
                adapter.setNotifyOnChange(true);
                ListView lv = (ListView) findViewById(R.id.listView_notes);
                lv.setAdapter(adapter);
            }

        }

    }
}
