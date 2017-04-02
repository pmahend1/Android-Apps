package com.uncc.prateek.internetcheck;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_netWorkCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = isConnectionOnline();
                if(status==true){
                    Toast.makeText(MainActivity.this, "Internet works", Toast.LENGTH_LONG).show();
                    RequestParams params = new RequestParams("POST","http://dev.theappsdr.com/lectures/params.php");
                    params.addParam("key1","value1");
                    params.addParam("key2","value2");
                    params.addParam("key3","value3");
                    params.addParam("key4","value4");
                    new GetHttpParams().execute(params);

                }
                else{
                    Toast.makeText(MainActivity.this, "No Internet!", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private class GetData extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            if(s != null ){
                Log.d("onPostExecute",s);
            }else{
                Log.d("onPostExecute","NULL");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader bf=null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con =  (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = bf.readLine())!= null){
                    sb.append(line+"\n");
                }
                return sb.toString();


            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
            finally {
                if(bf!= null){
                    try {
                        bf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;


        }
    }

    private class GetImage extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected void onPostExecute(Bitmap s) {
            if(s != null ){
                Log.d("onPostExecute",s.toString());
                ImageView iw = (ImageView) findViewById(R.id.imageView);
                iw.setImageBitmap(s);
            }else{
                Log.d("onPostExecute","NULL");
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection con =  (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

                return image;
            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            return null;


        }
    }


    private class GetHttpParams extends AsyncTask<RequestParams,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            if(s != null ){
                Log.d("onPostExecute",s.toString());
            }else{
                Log.d("onPostExecute","NULL");
            }
        }

        @Override
        protected String doInBackground(RequestParams... params) {
            BufferedReader bf=null;
            try {
                URL url = new URL(params[0].baseUrl);
                HttpURLConnection con =  params[0].setConnection();
                con.setRequestMethod("GET");
                bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line = bf.readLine())!= null){
                    sb.append(line+"\n");
                }
                return sb.toString();


            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
            finally {
                if(bf!= null){
                    try {
                        bf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            return null;


        }
    }

    private boolean isConnectionOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();
        if(ni!=null && ni.isConnected()){
            return TRUE;
        }
        return false;
    }
}
