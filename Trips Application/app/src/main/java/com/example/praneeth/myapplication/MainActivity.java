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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private String email, password;
    private EditText emailTxt, passwordTxt;
    private Button signInBtn, signupBtn;
    private ProgressDialog progressDialogue;
    private FirebaseAuth firebaseAuthentication;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private ImageView ivGoogleSignIn;
    public static FirebaseUser mCurrentUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthentication.addAuthStateListener(mAuthListener);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialogue = new ProgressDialog(this);

        firebaseAuthentication = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuthentication.getCurrentUser();
        emailTxt = (EditText) findViewById(R.id.emailInput);
        passwordTxt = (EditText) findViewById(R.id.password1Input);
        signInBtn = (Button) findViewById(R.id.loginBtn);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        ivGoogleSignIn = (ImageView) findViewById(R.id.ivGoogleSignIn);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    mCurrentUser = firebaseAuthentication.getCurrentUser();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


       // if (firebaseUser != null)



        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validInput())
                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                else
                    login(email, password);


            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

            }
        });

        ivGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });


    }

    private void login(String e, String p) {
        progressDialogue.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialogue.show();
        progressDialogue.setMessage("Logging in...");
        firebaseAuthentication.signInWithEmailAndPassword(e, p)
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogue.dismiss();
                        Toast.makeText(MainActivity.this, "Error while logging in\n Wrong user name or password \n Register if u dont have account", Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialogue.dismiss();
                        if (task.isSuccessful()){
                            Log.d("Log","d");
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }

                    }
                });
    }

    private boolean validInput() {
        email = emailTxt.getText().toString().trim();
        password = passwordTxt.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Input invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        // Firebase sign out
        firebaseAuthentication.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void revokeAccess() {
        firebaseAuthentication.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);

        }
    }


    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.getStatus());
        //Log.d(TAG,"dd",result.+"");
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            firebaseAuthWithGoogle(acct);


        } else {

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        final String firstName = acct.getGivenName();
        final String lastName = acct.getFamilyName();
        final String gender = "";
        final String status="";
        final String photoUrl =acct.getPhotoUrl().toString();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuthentication.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuthentication.getCurrentUser();
                            final String uid = user.getUid();
                            final String email =user.getEmail();
                            final String name = user.getDisplayName();


                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(uid)){
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    }else{
                                        User user=new User(email,firstName,lastName,photoUrl,firebaseAuthentication.getCurrentUser().getUid(),"",status);
                                        register(user);
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void register(User usr) {
        final String fullName = usr.getFirstname() + " " + usr.getLastname();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String picUrl="";
        String status="no  status";
        String key = databaseReference.child("Users").push().getKey();
        //User user=new User(emailTxt.getText().toString().trim(),firstNameTxt.getText().toString().trim(),lastNameTxt.getText().toString().trim(),picUrl,firebaseAuthentication.getCurrentUser().getUid(),genderInput.getText().toString().trim(),status);
        Map<String, Object> postValues = usr.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Users/" + firebaseAuthentication.getCurrentUser().getUid(), postValues);
        databaseReference.updateChildren(childUpdates);
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        /*firebaseAuthentication.createUserWithEmailAndPassword(usr.getEmail(), usr.getFirstname())
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
                             FirebaseUser firebaseUser;
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

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
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
                });*/
    }

}

