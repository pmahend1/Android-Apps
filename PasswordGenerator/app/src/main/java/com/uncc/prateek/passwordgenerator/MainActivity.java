/*
a. Assignment #. In Class 03
b. File Name. MainActivity.java
c. Full name of all students in your group. : 	Prateek Mahendrakar
												Rohan Patel
*/
package com.uncc.prateek.passwordgenerator;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private GoogleApiClient client;
    private String logLevel = "";
    private int passWordCount1=0;
    private int passWordCount2=0;
    private int passWordLength1=0;
    private int passWordLength2=0;
    private int pwdLength=0;
    ExecutorService threadPool;
    private ArrayList<String> passwordList= new ArrayList<String>();
    private ArrayList<String> passwordList1;
    private ArrayList<String> passwordList2;
    final static int GENERATE= 100;
    final static String PASSWORD_LIST1="PASSWORD_LIST1";
    final static String PASSWORD_LIST2="PASSWORD_LIST2";
    static final int STEP=1;
    static final int START=0;
    static final int STOP=2;
    Handler handler;
    ProgressDialog progressDialog1;
    ProgressDialog progressDialog2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logLevel = "Main";
        SeekBar sbPasswordCount1 = (SeekBar) findViewById(R.id.seekBar_passwordCount1);
        SeekBar sbPasswordCount2 = (SeekBar) findViewById(R.id.seekBar_passwordCount2);
        SeekBar sbPasswordLenght1 = (SeekBar) findViewById(R.id.seekBar_passwordLength1);
        SeekBar spPasswordLenght2 = (SeekBar) findViewById(R.id.seekBar_passwordLength2);
        TextView seekbarPassword1Count = (TextView) findViewById(R.id.textView_seekbarProgressPassWordCount1);
        TextView seekbarPassword2Count = (TextView) findViewById(R.id.textView_seekbarProgressPassWordCount2);
        seekbarPassword1Count.setText(""+(sbPasswordCount1.getProgress()+1));
        seekbarPassword2Count.setText(""+(sbPasswordCount2.getProgress()+1));
        TextView seekbarPasswordLength1 = (TextView) findViewById(R.id.textView_seekbarProgressPasswordLength1);
        TextView seekbarPasswordLength2 = (TextView) findViewById(R.id.textView_seekbarProgressPasswordLength2);
        seekbarPasswordLength1.setText(""+(sbPasswordLenght1.getProgress()+7));
        seekbarPasswordLength2.setText(""+(spPasswordLenght2.getProgress()+7));
        Button buttonGenerate = (Button) findViewById(R.id.button_generate);
        Button buttonFinish = (Button) findViewById(R.id.button_finish);
        Log.d(logLevel,"setOnClickListener");
        buttonGenerate.setOnClickListener(this);

        progressDialog1 = new ProgressDialog(MainActivity.this);
        progressDialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog1.setMessage("Generating Passwords");
        progressDialog1.setMax(passWordCount1+1);
        progressDialog1.setCancelable(false);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what){
                    case START:{
                        Log.d(logLevel,"Handler for START");
                        progressDialog1.show();
                        Log.d(logLevel,"progressDialog1.show()");
                        Log.d(logLevel,String.valueOf(progressDialog1.isShowing()));

                        break;
                    }
                    case STEP:{
                        Log.d(logLevel,"Handler for STEP");
                        progressDialog1.setProgress((Integer) msg.obj);
                        Log.d(logLevel,String.valueOf(msg.obj));
                        break;
                    }
                    case STOP:{
                        Log.d(logLevel,"Handler for STOP");
                        progressDialog1.dismiss();
                        Log.d(logLevel,"Dismissing progress dialog");
                        break;
                    }
                }
                return false;
            }
        });

        sbPasswordCount1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_seekbarProgressPassWordCount1);
                tvRatingVal.setText(String.valueOf(progress+1));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        sbPasswordCount2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_seekbarProgressPassWordCount2);
                tvRatingVal.setText(String.valueOf(progress+1));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        sbPasswordLenght1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_seekbarProgressPasswordLength1);
                tvRatingVal.setText(String.valueOf(progress+7));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        spPasswordLenght2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                TextView tvRatingVal = (TextView) findViewById(R.id.textView_seekbarProgressPasswordLength2);
                tvRatingVal.setText(String.valueOf(progress+7));
                Log.d(logLevel,"Seekbar value changed");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_generate){
            Log.d(logLevel,"Inside onClick for button_generate");
            SeekBar sbPasswordCount1 = (SeekBar) findViewById(R.id.seekBar_passwordCount1);
            SeekBar sbPasswordCount2 = (SeekBar) findViewById(R.id.seekBar_passwordCount2);
            SeekBar sbPasswordLenght1 = (SeekBar) findViewById(R.id.seekBar_passwordLength1);
            SeekBar spPasswordLenght2 = (SeekBar) findViewById(R.id.seekBar_passwordLength2);
            final TextView seekbarPassword1Count = (TextView) findViewById(R.id.textView_seekbarProgressPassWordCount1);
            /*handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what){
                        case START:{
                            Log.d(logLevel,"Handler for START");
                            progressDialog1.show();
                            break;
                        }
                        case STEP:{
                            Log.d(logLevel,"Handler for STEP");
                            progressDialog1.setProgress((Integer) msg.obj);
                            break;
                        }
                        case STOP:{
                            Log.d(logLevel,"Handler for STOP");
                            progressDialog1.dismiss();
                            break;
                        }
                    }
                    return false;
                }
            });*/
            passWordCount1 = sbPasswordCount1.getProgress()+1;
            passWordCount2 = sbPasswordCount2.getProgress()+1;
            passWordLength1 = sbPasswordLenght1.getProgress()+7;
            passWordLength2 = spPasswordLenght2.getProgress()+7;
            Log.d(logLevel,"ThreadPool1 started ");
             threadPool= Executors.newFixedThreadPool(2);
                pwdLength=passWordLength1;

           threadPool.execute(new PasswordGeneratorThread());
            try{
                threadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
            }catch(Exception e){
                Log.d(logLevel,e.getMessage());
            }

            passwordList1 = new ArrayList<String>(passwordList);
            passwordList.clear();
            pwdLength=passWordLength2;

            Log.d(logLevel,"ThreadPool2 started ");
            try{
                new PasswordGeneratorAsync().execute().get();
            }catch (Exception e){
                Log.d(logLevel,e.getMessage());
            }

            passwordList2=new ArrayList<String>(passwordList);


            Log.d(logLevel,"Password list 1 count "+passwordList1.size());
            Log.d(logLevel,"Password list 2 count "+passwordList2.size());

            Log.d(logLevel,"Intent started ");

            Intent intent = new Intent(MainActivity.this,GeneratedPasswordsActivity.class);
            intent.putExtra(PASSWORD_LIST1,passwordList1);
            intent.putExtra(PASSWORD_LIST2,passwordList2);
            startActivityForResult(intent,GENERATE);


        }
        else if(v.getId()==R.id.button_finish){
            Log.d(logLevel,"Finish clicked");
        }
        else{
            Log.d(logLevel,"Nothing");
        }

    }

    class PasswordGeneratorThread implements Runnable{

        @Override
        public void run() {

            Log.d(logLevel,"PasswordGeneratorThread Running");
            Message msg=new Message();
            msg.what=START;
            handler.sendMessage(msg);

            for(int i=0;i<passWordCount1;i++){
                Log.d(logLevel,"PasswordGeneratorThread Thread "+ (i+1) + " running");
                passwordList.add( Util.getPassword(pwdLength));
                msg = new Message();
                msg.what = STEP;
                msg.obj=i+1;
                handler.sendMessage(msg);

            }
            msg = new Message();
            msg.what=STOP;
            handler.sendMessage(msg);

            Log.d(logLevel,"Password generator running");
            threadPool.shutdown();
        }
    }

    class PasswordGeneratorAsync extends AsyncTask<Void,Integer,Void>{


        @Override
        protected Void doInBackground(Void... params) {
            Log.d(logLevel,"PasswordGeneratorAsync Running");
            for (int i=0;i<passWordCount2;i++){
                Log.d(logLevel,"PasswordGeneratorAsync Thread"+ (i+1) + "running");
                publishProgress(i+1);
                passwordList.add(Util.getPassword(passWordLength2));

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("test","entered on pre execute in async");
            progressDialog2=  new ProgressDialog(MainActivity.this);
            progressDialog2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog2.setMax(passWordCount2+1);
            progressDialog2.setCancelable(false);
            progressDialog2.setMessage("Generating Passwords");
            Log.d("test","show progress");
            progressDialog2.show();
            Log.d("test" , String.valueOf(progressDialog2.isShowing()));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("test","end progress");
            Log.d("test" , String.valueOf(progressDialog2.isShowing()));
            progressDialog2.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog2.setProgress(values[0]);
            Log.d(logLevel,"Progress");
            Log.d(logLevel,"Async:"+"Progress dialog value :"+values[0]);
        }
    }

}