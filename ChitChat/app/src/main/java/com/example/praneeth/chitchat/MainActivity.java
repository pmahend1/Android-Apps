package com.example.praneeth.chitchat;
/*a. Assignment #. In Class 09
        b. File Name. ___
        c. Full name of all students in your group. : Prateek , Praneeth*/
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnClickListener,AsyncSignUp.IContext , AsyncLogin.IContextLogin{

    private static String BASE_URL = "http://52.90.79.130:8080/Groups/";
    private static String LOGIN_URL = "http://52.90.79.130:8080/Groups/api/login";
    private static String SIGNUP_URL = "POST /api/signUp";
    private static String CHANNEL_URL = "http://52.90.79.130:8080/Groups/";


    private static String TOKEN="";

    /*Channels API: GET /api/get/channels
    • Get Messages API: GET /api/get/messages?channel_id=<CHANNEL_ID>
    • Post Messages API: POST /api/post/message
    • Subscribe Channel API: POST /api/subscribe/channel
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // String jsonString = FileUtility.read("ITIS5180 InClass 09.postman_collection.json");

        //Log.d("main",jsonString);

        //new ConnectionAsyncTask().execute(LOGIN_URL);

        EditText etUserName = (EditText) findViewById(R.id.editText_Email_L);
        EditText etPwd = (EditText) findViewById(R.id.editText_Password_L);

        Button btnLogin  = (Button) findViewById(R.id.button_Login);

        Button btnSignUp  = (Button) findViewById(R.id.button_SignUp);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.button_Login){
            EditText etUserName = (EditText) findViewById(R.id.editText_Email_L);
            EditText etPwd = (EditText) findViewById(R.id.editText_Password_L);
            Log.d("main","email="+etUserName.getText().toString());
            Log.d("main","pwd="+etPwd.getText().toString());

            /*final OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", etUserName.getText().toString().trim())
                    .add("password", etPwd.getText().toString().trim())
                    .build();

            Request request = new Request.Builder()
                    .url("http://52.90.79.130:8080/Groups/api/login")
                    .header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Content-Type","application/x-www-form-urlencoded")
                    .post(formBody)
                    .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
                Log.d("main",response.toString());
                Log.d("main",response.message());
                //Log.d("main",response);
                if (!response.isSuccessful()){
                    throw new Exception("Unexpected code " + response);
                }
                System.out.println("Server: " + response.header("Server"));
                System.out.println("Date: " + response.header("Date"));
                System.out.println("Vary: " + response.headers("Vary"));
                System.out.println(response.body().string());

                Intent intent = new Intent(this,SubscribedChannels.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.d("main", e.toString()+"");
            }*/
            String email = etUserName.getText().toString().trim();
            String password = etPwd.getText().toString().trim();

            String[] params = {email,password};
            new AsyncLogin(this).execute(params);
        }else{
            //sign up
            EditText etFirstName = (EditText) findViewById(R.id.editText_FN_S);
            EditText etLastName = (EditText) findViewById(R.id.editText_LN_S);
            EditText etEmail = (EditText) findViewById(R.id.editText_Email_S);
            EditText etLPwd = (EditText) findViewById(R.id.editText_Password_S);

            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etLPwd.getText().toString().trim();

            String[] params = {firstName,lastName,email,password};
            new AsyncSignUp(this).execute(params);


        }

    }

    @Override
    public void onSignUp(String token) {
            if(token!=null){
                TOKEN = token;
                Log.d("main","User Created  ");

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("token", TOKEN);
                editor.apply();
                editor.commit();

                Intent intent = new Intent(this,SubscribedChannels.class);
                startActivity(intent);
            }
    }


    @Override
    public void onLogin(String token) {
        TOKEN = token;
        if(!TOKEN.isEmpty()){
            Log.d("main","User logged in");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("token", TOKEN);
            editor.apply();
            editor.commit();

            Intent intent = new Intent(this,SubscribedChannels.class);
            startActivity(intent);
        }

    }
}
