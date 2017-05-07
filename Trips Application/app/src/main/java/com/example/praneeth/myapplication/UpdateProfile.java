package com.example.praneeth.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener{

    ImageView ivProfPic;
    EditText evFirstName;
    EditText evLastName;
    EditText evStatus;
    Button button_Update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ivProfPic = (ImageView) findViewById(R.id.imageView);
        evFirstName= (EditText) findViewById(R.id.editText_editFirstName);
        evLastName = (EditText) findViewById(R.id.editText_editLastName);
        evStatus = (EditText) findViewById(R.id.editText_editStatus);
        button_Update  = (Button) findViewById(R.id.button_Update);

        button_Update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String firstname =  evFirstName.getText().toString();
        String lastName =  evLastName.getText().toString();
        String status =  evStatus.getText().toString();

        if(firstname!=null && lastName!=null){

        }

    }
}
