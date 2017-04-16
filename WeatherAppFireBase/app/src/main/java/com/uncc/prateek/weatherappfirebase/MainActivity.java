package com.uncc.prateek.weatherappfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    TextView textViewCondition;
    Button buttonSunny;
    Button buttonFoggy;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mConditionRef = mRootRef.child("condition");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCondition = (TextView) findViewById(R.id.textViewCondition);
        buttonSunny = (Button) findViewById(R.id.buttonSunny);
        buttonFoggy = (Button) findViewById(R.id.buttonFoggy);

        buttonSunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textViewCondition.setText("Sunny");
                mConditionRef.setValue("Sunny");
            }
        });

        buttonFoggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textViewCondition.setText("Foggy");
                mConditionRef.setValue("Foggy");
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference conditionRef = mRootRef.child("condition");
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textViewCondition.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
