/*
a. Assignment #. In Class 03
b. File Name. MainActivity.java
c. Full name of all students in your group. : 	Prateek Mahendrakar
												Rohan Patel
*/
package com.uncc.prateek.passwordgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GeneratedPasswordsActivity extends AppCompatActivity {
private String logLevel="GeneratedPasswordsAct";
    private String[] lv_arr = {};
    private String[] lv_arr2 = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_generated_passwords);

        ArrayList<String> passwordList1 = (ArrayList<String>) getIntent().getSerializableExtra(MainActivity.PASSWORD_LIST1);
        ArrayList<String> passwordList2 = (ArrayList<String>) getIntent().getSerializableExtra(MainActivity.PASSWORD_LIST2);

        Log.d(logLevel,"passwordList1"+passwordList1.size());
        Log.d(logLevel,"passwordList2"+passwordList2.size());

        ListView lv1 = (ListView) findViewById(R.id.listView_threadPasswords);

        ArrayAdapter adapter = new ArrayAdapter<String>(GeneratedPasswordsActivity.this,android.R.layout.simple_list_item_1, passwordList1);
        lv1.setAdapter(adapter);

        ListView lv2 = (ListView) findViewById(R.id.listView_asyncPasswords);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(GeneratedPasswordsActivity.this,android.R.layout.simple_list_item_1, passwordList2);
        lv2.setAdapter(adapter2);

        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
