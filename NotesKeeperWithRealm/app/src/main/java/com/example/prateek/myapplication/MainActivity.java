package com.example.prateek.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements ButtonClickCallBack {
    static String COMPLETTED_STATUS = "COMPLETE";
    static String PENDING_STATUS = "PENDING";
    final String PENDING = "pending";
    final String COMPLETED = "completed";
    final String TAG = "log";
    final String ID = "_id";
    RecyclerView lv1;
    NotesViewAdapter ia;
    Spinner sp;
    EditText ed;
    ArrayList<Note> completedNotes, pendingNotes = new ArrayList<Note>();
    Button bt;
    RecyclerView.Adapter adapter;
    ArrayList<Note> allNotes = new ArrayList<Note>();
    private Realm realm;
    //private RealmResults<NotesList> notes;
    private RealmResults<Note> notesRealm;
    private boolean logoutAfterClose;

    @Override
    public void onPlayButtonClick(int p) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete the task complete ");
        final String id = allNotes.get(p).get_id();
        System.out.println("id object to be delete" + id);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //myRef.child(id).removeValue();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Note> rows = realm.where(Note.class).equalTo(ID, id).findAll();
                        rows.deleteAllFromRealm();
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();


    }

    @Override
    public void onCheckedClickCallBack(int p, String s) {
        Note note = allNotes.get(p);
        final String id = note.get_id();
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Note> rows = realm.where(Note.class).equalTo(ID, id).findAll();
                rows.
            }
        });*/

        //Note realmNote = realm.where(Note.class).equalTo(ID, id).findFirst();
        final String s1= s;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note = realm.where(Note.class).equalTo(ID, id).findFirst();
                note.setStatus(s1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar_items, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        notesRealm = realm.where(Note.class).findAll();
        //realm.where(Note.class).findAll();
        notesRealm.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> results) {
                List<Note> copied = realm.copyFromRealm(results);
                allNotes = new ArrayList<Note>(copied);
                Log.d(TAG, "allNotes.size()" + allNotes.size());
                //updateNotesRecyclerView();//updateList(results);
                ia = new NotesViewAdapter(MainActivity.this, MainActivity.this, allNotes);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                lv1.setLayoutManager(mLayoutManager);

                // shoppingAdapter = new ShoppingAdapter(this,this,notesArrayList);
                lv1.setAdapter(ia);
                ia.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        Stetho.initializeWithDefaults(this);

        lv1 = (RecyclerView) findViewById(R.id.listview1);
        ed = (EditText) findViewById(R.id.editTextNotes);
        bt = (Button) findViewById(R.id.buttonAdd);
        sp = (Spinner) findViewById(R.id.spinner2);

        realm = Realm.getDefaultInstance();
        notesRealm = realm.where(Note.class).findAll();

        List<Note> copied = realm.copyFromRealm(notesRealm);
        allNotes = new ArrayList<Note>(copied);

        ia = new NotesViewAdapter(this, this, allNotes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        lv1.setLayoutManager(mLayoutManager);

        // shoppingAdapter = new ShoppingAdapter(this,this,notesArrayList);
        lv1.setAdapter(ia);
        ia.notifyDataSetChanged();


        Log.d(TAG, "Size " + allNotes.size());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteText = ed.getText().toString();
                String priority = sp.getSelectedItem().toString();
                String time = Long.toString(System.currentTimeMillis());
                String uniqueID = UUID.randomUUID().toString();
                if (noteText != null) {
                    if (!noteText.isEmpty()) {
                        realm.beginTransaction();
                        Note note = new Note(uniqueID, noteText, priority, time, PENDING);
                        Note noteRealm = realm.copyToRealm(note);
                        System.out.println("Note added" + noteRealm.toString());
                        allNotes.add(noteRealm);
                        pendingNotes.add(noteRealm);
                        realm.commitTransaction();
                        ia.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Note text can not be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private int getPri(String priority) {

        if (priority.equalsIgnoreCase("High")) {
            return 0;
        } else if (priority.equalsIgnoreCase("Medium")) {
            return 1;
        } else {
            return 2;
        }

    }

    public void updateNotesRecyclerView() {
        ia.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void showall(MenuItem item) {
        ia = new NotesViewAdapter(this, this, allNotes);
        ia.notifyDataSetChanged();
        lv1.setAdapter(ia);


    }

    public void showcomp(MenuItem item) {
        realm = Realm.getDefaultInstance();
        String status = "status";
        notesRealm = realm.where(Note.class).equalTo(status, "completed").findAll();

        List<Note> copied = realm.copyFromRealm(notesRealm);
        completedNotes = new ArrayList<Note>(copied);

        ia = new NotesViewAdapter(this, this, completedNotes);
        ia.notifyDataSetChanged();
        lv1.setAdapter(ia);


    }

    public void showt(MenuItem item) {

        ia.notifyDataSetChanged();


    }

    public void showpen(MenuItem item) {
        realm = Realm.getDefaultInstance();
        String status = "status";
        notesRealm = realm.where(Note.class).equalTo(status, PENDING).findAll();
        System.out.println("notesRealm = " + notesRealm.toString());

        List<Note> copied = realm.copyFromRealm(notesRealm);
        pendingNotes = new ArrayList<Note>(copied);

        ia = new NotesViewAdapter(this, this, pendingNotes);
        ia.notifyDataSetChanged();
        lv1.setAdapter(ia);

    }

    public void showpri(MenuItem item) {
        ia.notifyDataSetChanged();


    }

    private void updateList(RealmResults<NotesList> results) {

        if (results.size() > 0
                && adapter == null
                ) {

            realm.beginTransaction();
            Set<String> seen = new HashSet<>();
            Iterator<Note> it = results.first().getItems().iterator();
            while (it.hasNext()) {
                Note list = it.next();
            }
            realm.commitTransaction();
        }
    }

    enum Pro {
        h("High"),
        m("Medium"),
        l("Low");

        private final String text;

        private Pro(final String text) {
            this.text = text;
        }

    }

}
