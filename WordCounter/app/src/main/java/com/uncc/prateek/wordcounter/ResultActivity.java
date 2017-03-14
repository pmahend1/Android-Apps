/*      a. Assignment #. Homework 3
        b. File Name.
        c. Full name of all students in your group  Prateek Mahendrakar
                                                    Siva Ram Praneeth Vemulapalli
*/
package com.uncc.prateek.wordcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_result);
        ListView lv1 = (ListView) findViewById(R.id.listView_result);
        HashMap<String,Integer> resultSet = (HashMap<String, Integer>) getIntent().getSerializableExtra(MainActivity.RESULT_SET);
        //ArrayAdapter adapter = new ArrayAdapter<String>(ResultActivity.this,android.R.layout.simple_list_item_1, resultSet);
        //lv1.setAdapter(adapter);
        ArrayList<String> resultList = new ArrayList<String>();
        for(String key : resultSet.keySet()){
            resultList.add(key + ": "+ resultSet.get(key));
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(ResultActivity.this,android.R.layout.simple_list_item_1, resultList);
        lv1.setAdapter(adapter);

        findViewById(R.id.button_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
}
