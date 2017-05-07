package com.example.praneeth.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Praneeth on 4/20/2017.
 */

public class RegistrationActivity extends AppCompatActivity {
    private static String fullName;
    private TextView firstNameTxt, lastNameTxt, emailTxt, password1Txt, password2Txt, genderInput;
    private Button register, cancel;
    private ProgressDialog progressDialogue;
    private FirebaseAuth firebaseAuthentication;
    private static FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialogue = new ProgressDialog(this);
        firebaseAuthentication = FirebaseAuth.getInstance();
        String firstName;
        firstNameTxt = (TextView) findViewById(R.id.firstNameInput);
        lastNameTxt = (TextView) findViewById(R.id.lastNameInput);
        emailTxt = (TextView) findViewById(R.id.emailInput);
        password1Txt = (TextView) findViewById(R.id.password1Input);
        password2Txt = (TextView) findViewById(R.id.password2Input);
        genderInput = (TextView) findViewById(R.id.genderInput);


        register = (Button) findViewById(R.id.loginBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameTxt.getText().toString().trim();
                String lastName = lastNameTxt.getText().toString().trim();
                String email = emailTxt.getText().toString().trim();
                String password1 = password1Txt.getText().toString().trim();
                String password2 = password2Txt.getText().toString().trim();
                String genderInputText =genderInput.getText().toString().trim();
                String fullNametext = firstName + " " + lastName;
                if(validInput(fullNametext, email, password1, password2)) {
                    progressDialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialogue.show();
                    progressDialogue.setMessage("Registering...");
                    register(fullNametext, email, password1);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private boolean validInput(String fullName, String email, String password1, String password2) {
        if(TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2) ) {
            Toast.makeText(this, "Input invalid", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!password1.equals(password2)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void register(String fn, String e, String p) {
        fullName = fn;
        firebaseAuthentication.createUserWithEmailAndPassword(e, p)
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogue.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialogue.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration is successful", Toast.LENGTH_SHORT).show();
                            firebaseUser = firebaseAuthentication.getCurrentUser();
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fullName).build();
                            firebaseUser.updateProfile(userProfileChangeRequest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d("Success", "Success");
                                        }
                                    });

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            String picUrl="";
                            String status="no  status";
                            String key = databaseReference.child("Users").push().getKey();
                            User user=new User(emailTxt.getText().toString().trim(),firstNameTxt.getText().toString().trim(),lastNameTxt.getText().toString().trim(),picUrl,firebaseAuthentication.getCurrentUser().getUid(),genderInput.getText().toString().trim(),status);
                            Map<String, Object> postValues = user.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/Users/" + firebaseAuthentication.getCurrentUser().getUid(), postValues);
                            databaseReference.updateChildren(childUpdates);
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }
}