package com.company.lahacks.lahacks2018;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private FirebaseFunctions mFunctions;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    public boolean getEntries() {
        EditText user = (EditText) findViewById(R.id.et_username);
        email = user.getText().toString();

        EditText pw = (EditText) findViewById(R.id.et_password);
        password = pw.getText().toString();

        if (email.equals("") || password.equals("")) {
            return false;
        }

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);
    }

    public void updateUI() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void signIn(View view) {
        if (!getEntries()) {
            Toast.makeText(MainActivity.this, "Email or password cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Successfully logged in.",
                                    Toast.LENGTH_SHORT).show();
                           updateUI();
                         //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            EditText pw = (EditText) findViewById(R.id.et_password);
                            pw.setText("");
                          //  updateUI(null);
                        }

                        // ...
                    }

                });

    }

    public void createAccount(View view) {
        if (!getEntries()) {
            Toast.makeText(MainActivity.this, "Email or password cannot be empty!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    myRef.child("Users").child(EncodeString(email)).child("coins").setValue((Integer)50);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            updateUI();
                         //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            EditText pw = (EditText) findViewById(R.id.et_password);
                            pw.setText("");
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}
