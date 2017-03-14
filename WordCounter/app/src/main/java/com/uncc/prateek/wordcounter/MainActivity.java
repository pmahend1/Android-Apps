/*      a. Assignment #. Homework 3
        b. File Name.
        c. Full name of all students in your group  Prateek Mahendrakar
                                                    Siva Ram Praneeth Vemulapalli
*/
package com.uncc.prateek.wordcounter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    public static String logLevel = "";
    public static String logText = "Main" + "/" + logLevel;
    View linearLayout1;
    View linearLayoutVertical;
    View editText_firstKeyword;
    ArrayList<Integer> searchEVKeys = new ArrayList<Integer>();
    HashMap<Integer, String> searchList = new HashMap<Integer, String>();
    HashMap<String, Integer> resultList = new HashMap<String, Integer>();
    HashMap<Integer, Integer> fabMap = new HashMap<Integer, Integer>();
    int runId = 0;
    int count = 0;
    boolean fab1FirstClick = false;
    boolean fab2FirstClick = false;
    boolean fab3FirstClick = false;
    boolean fab4FirstClick = false;
    boolean fab5FirstClick = false;
    boolean fab6FirstClick = false;
    boolean fab7FirstClick = false;
    boolean fab8FirstClick = false;
    boolean fab9FirstClick = false;
    boolean fab10FirstClick = false;
    boolean fab11FirstClick = false;
    boolean fab12FirstClick = false;
    boolean fab13FirstClick = false;
    boolean fab14FirstClick = false;
    boolean fab15FirstClick = false;
    boolean fab16FirstClick = false;
    boolean fab17FirstClick = false;
    boolean fab18FirstClick = false;
    boolean fab19FirstClick = false;
    boolean fab20FirstClick = false;


    public static String RESULT_SET = "RESULT_SET";
    public static int SHOW_RESULT_CODE = 100;
    boolean matchCase = false;


    int id = 1;

    private GoogleApiClient client;

    public String readTextFromFile(String fileName) {
        String text = "";

        try {
            //Scanner scanner = new Scanner(new FileReader(fileName));
            InputStream fileData = getAssets().open(fileName);
            int size = fileData.available();
            byte[] buffer = new byte[size];
            fileData.read(buffer);
            fileData.close();
            text = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(logText, "File read exception" + e.getMessage());
        } finally {
            return text;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout1 = findViewById(R.id.linearLayout_1);
        linearLayoutVertical = findViewById(R.id.linearLayoutVertical);
        editText_firstKeyword = findViewById(R.id.editText_firstKeyword);

        final View scrollView = findViewById(R.id.scrollView_field);
        final View checkBox = findViewById(R.id.checkBox_matchCase);
        findViewById(R.id.floatingActionButton).setOnClickListener(this);
        findViewById(R.id.button_search).setOnClickListener(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
       Log.d(logText,"v.getId()"+v.getId()+"");
        switch (v.getId()) {
            case R.id.floatingActionButton: {
                logLevel = "FAB Onclick";
                //searchEVKeys.add(editText_firstKeyword.getId());

                Log.d(logText, "floatingActionButton clicked");
               /* if (id < 20 && fab1FirstClick == false) {*/
                if (id < 20) {
                    id = id + 1;
                    fab1FirstClick = true;
                    EditText edKeyword = new EditText(MainActivity.this);
                    LinearLayout layoutNew = new LinearLayout(MainActivity.this);
                    Button fabNew = new Button(MainActivity.this);
                    //String layOutId = "R.id."+"layout_"+id;


                    int layoutID = layoutNew.generateViewId();

                    layoutNew.setLayoutParams(linearLayout1.getLayoutParams());


                    int editViewId = edKeyword.generateViewId();
                    //edKeyword.setId(R.id.editText_firstKeyword+id);
                    edKeyword.setId(editViewId);

                    View ed = edKeyword;

                    Log.d(logText, "editext id " + ed.getId());

                    if (edKeyword.getId() > 0) {
                        searchEVKeys.add(edKeyword.getId());
                        Log.d(logText, "edKeyword.getId()>0");
                    }


                    edKeyword.setLayoutParams(editText_firstKeyword.getLayoutParams());
                    ((LinearLayout) layoutNew).addView(edKeyword);

                    final Button fab = (Button) findViewById(R.id.floatingActionButton);
                    fabMap.put(1, fab.getId());

                    //int fabID = fabNew.generateViewId();
                    fabNew.setId(100+id);

                    Log.d(logText,"fab id set was : "+100+id);
                    //Log.d(logText,"fab id set was : "+fabID);


                    //fabMap.put(id, R.id.fabButton2);
                    fabMap.put(id, R.id.fabButton2);

                    if (fabMap != null) {
                        for (Integer n : fabMap.keySet()) {
                            //FloatingActionButton fabButton = (FloatingActionButton) findViewById(fabMap.get(n));
                            //fabButton.setImageResource(android.R.drawable.ic_input_delete);
                            Log.d(logText, n + ":" + fabMap.get(n));
                        }
                    }


                    Log.d(logText, "fabNew.generateViewId();");
                    //fabNew = fab;

                    fabNew.setLayoutParams(fab.getLayoutParams());
                    fabNew.setClickable(true);
                    fabNew.setBackgroundResource(android.R.color.white);
                    //fabNew.setImageResource(android.R.drawable.ic_input_add);
                    fabNew.setBackgroundResource(android.R.drawable.ic_input_add);

                    fabNew.setText("+");

                    fab.setBackgroundResource(android.R.drawable.ic_delete);

                    fabNew.setOnClickListener(MainActivity.this);
                    ((LinearLayout) layoutNew).addView(fabNew);

                    ((LinearLayout) linearLayoutVertical).addView(layoutNew);

                 /*   EditText ev = (EditText) findViewById(edKeyword.getId());
                    ev.setText(Integer.toString(edKeyword.getId()));*/

                    Log.d(logText, Integer.toString(id));

                    break;
                }

                break;
            }
            case R.id.button_search: {
                WordCount.clearCounts();
                logLevel = "button_search";
                Log.d(logText, "Started");

                Log.d(logText, "editText_firstKeyword.getId() " + editText_firstKeyword.getId());
                searchEVKeys.add(editText_firstKeyword.getId());

                count = searchEVKeys.size();

                CheckBox ch = (CheckBox) findViewById(R.id.checkBox_matchCase);
                matchCase = ch.isChecked();

                Log.d(logText, "Match case is cheked? " + matchCase);


                for (int evKey : searchEVKeys) {

                    Log.d(logText, "for searchEVKeys loop");
                    EditText editText = (EditText) findViewById(evKey);
                    if(editText!=null){
                        searchList.put(evKey, editText.getText().toString());
                        Log.d(logText, evKey + ":" + editText.getText().toString() + " Added into SearchList");
                    }

                }
                for (Integer x : searchList.keySet()) {
                    Log.d(logText, "Running async for " + x + "-->" + searchList.get(x));
                    int cnt = 0;
                    try {
                        cnt = new WordCountAsync().execute(searchList.get(x).toString()).get();
                    } catch (InterruptedException e) {
                       Log.d(logText,"here2"+e.getMessage());
                    } catch (ExecutionException e) {
                        Log.d(logText,"here2"+e.getMessage());
                    }
                    Log.d(logText, "Count X ");

                }
                while (resultList.size() < searchList.size()) {
                    for (int j = 0; j < 1000000; j++) {
                    }

                    //Log.d(logText, "Wait");
                }
                if (resultList != null && resultList.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra(RESULT_SET, resultList);
                    startActivity(intent);
                    Log.d(logText, "Back to main thread");


                }

                Log.d(logText, "before break");
                progressDialog.dismiss();
                //finish();
                break;

            }
            default:{
                Button btn = (Button) findViewById(v.getId());
                if(btn.getText().toString().equals("+")){
                    //new keyword field add
                    id = id + 1;
                    fab1FirstClick = true;
                    EditText edKeyword = new EditText(MainActivity.this);
                    LinearLayout layoutNew = new LinearLayout(MainActivity.this);
                    Button fabNew = new Button(MainActivity.this);
                    //String layOutId = "R.id."+"layout_"+id;
                    int layoutID = layoutNew.generateViewId();
                    layoutNew.setLayoutParams(linearLayout1.getLayoutParams());
                    int editViewId = edKeyword.generateViewId();
                    //edKeyword.setId(R.id.editText_firstKeyword+id);
                    edKeyword.setId(editViewId);
                    View ed = edKeyword;
                    Log.d(logText, "editext id " + ed.getId());
                    if (edKeyword.getId() > 0) {
                        searchEVKeys.add(edKeyword.getId());
                        Log.d(logText, "edKeyword.getId()>0");
                    }


                    edKeyword.setLayoutParams(editText_firstKeyword.getLayoutParams());
                    ((LinearLayout) layoutNew).addView(edKeyword);

                    final Button fab = (Button) findViewById(v.getId());
                    fabMap.put(id, fab.getId());

                    //int fabID = fabNew.generateViewId();
                    fabNew.setId(100+id);

                    Log.d(logText,"fab id set was : "+100+id);
                    //Log.d(logText,"fab id set was : "+fabID);


                    //fabMap.put(id, R.id.fabButton2);
                    fabMap.put(id, fabNew.getId());

                    if (fabMap != null) {
                        for (Integer n : fabMap.keySet()) {
                            //FloatingActionButton fabButton = (FloatingActionButton) findViewById(fabMap.get(n));
                            //fabButton.setImageResource(android.R.drawable.ic_input_delete);
                            Log.d(logText, n + ":" + fabMap.get(n));
                        }
                    }


                    Log.d(logText, "fabNew.generateViewId();");
                    //fabNew = fab;

                    fabNew.setLayoutParams(fab.getLayoutParams());
                    fabNew.setClickable(true);
                    fabNew.setBackgroundResource(android.R.color.white);
                    //fabNew.setImageResource(android.R.drawable.ic_input_add);
                    fabNew.setBackgroundResource(android.R.drawable.ic_input_add);

                    fabNew.setText("+");

                    fab.setBackgroundResource(android.R.drawable.ic_delete);
                    fab.setText("-");

                    fabNew.setOnClickListener(MainActivity.this);
                    ((LinearLayout) layoutNew).addView(fabNew);

                    ((LinearLayout) linearLayoutVertical).addView(layoutNew);

                    /*EditText ev = (EditText) findViewById(edKeyword.getId());
                    ev.setText(Integer.toString(edKeyword.getId()));*/

                    Log.d(logText, Integer.toString(id));

                    break;
                }
                else if(btn.getText().toString().equals("-")){

                    View view = findViewById(R.id.floatingActionButton);
                    ViewGroup vg = (ViewGroup) v.getParent();
                    //vg.removeView(view);
                    vg.removeAllViews();
                    fab1FirstClick=false;
                    id--;
                    fabMap.remove(id);
                    //searchEVKeys.remove(id);
                    break;
                }

                break;
            }

        }
    }





    class WordCountAsync extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            Log.d(logText, "onPreExecute starts");
            if (progressDialog == null) {
           /*     progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(true);

                //progressDialog.setMessage("Computing Progress");
                Log.d(logText, "Dialog box is created");*/
                //progressDialog=progressDialog.show(MainActivity.this, "Processing Word Count", "Please wait five seconds...");
                /*progressDialog = new ProgressDialog(MainActivity.this);*/
                progressDialog=ProgressDialog.show(MainActivity.this,
                        "Processing Word Count",
                        "Please wait five seconds...", false,
                        false);
            }


        }

        @Override
        protected Integer doInBackground(String... params) {
            logLevel = "doInBkg";
            Log.d(logText, "params[0]" + params[0]);
            try {
                logLevel = "try";
                String str = readTextFromFile("textfile.txt");
                Log.d(logText, "after reading the file");
                int wordcount = 0;

                wordcount = new WordCount().search(str, params[0], matchCase);
                Log.d(logText, "WordCount().search(inputStream,params[0]");

                runId++;

                Log.d(logText, "incrementing runId");
                publishProgress(runId);
                Log.d(logText, "publishing progress to dialog");
                resultList.put(params[0], wordcount);
                Log.d(logText, "Wordcount found is" + wordcount);


                return (Integer)wordcount;

            } catch (Exception ex) {
                Log.d(logText, "Here"+ex.toString());
                return 0;
            }


        }

/*
        @Override
        protected void onProgressUpdate(Integer... values) {
            logLevel="onProgressUpdate";
            //super.onProgressUpdate(values);
             Log.d(logText,"onProgressUpdate values[0]"+ values[0]);
            progressDialog.setProgress(values[0]);

        }
*/


        @Override
        protected void onPostExecute(Integer integer) {
            //super.onPostExecute(integer);
            logLevel = "onPostExecute";
            Log.d(logText, "onPostExecute");
            Log.d(logText, "" + integer);
            if (runId == count) {
                Log.d(logText, "dismissing progress dialog");
                progressDialog.dismiss();
                //progressDialog.cancel();
            }
        }

    }


}
