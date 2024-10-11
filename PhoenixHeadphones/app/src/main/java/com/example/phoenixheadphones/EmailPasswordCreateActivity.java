package com.example.phoenixheadphones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordCreateActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    Button btnSignup,btnSigninIntent;
    EditText etEmailCreate, etPasswordCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password_create);
        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        btnSignup = findViewById(R.id.btn_signup);
        btnSigninIntent = findViewById(R.id.btn_signin_intent);
        etEmailCreate = findViewById(R.id.et_email_create);
        etPasswordCreate = findViewById(R.id.et_password_create);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(etEmailCreate.getText()) != "" && String.valueOf(etPasswordCreate.getText())!="")
                    createAccount(String.valueOf(etEmailCreate.getText()),String.valueOf(etPasswordCreate.getText()));
            }
        });
        btnSigninIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmailPasswordCreateActivity.this,EmailPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            MainActivity.getInstance().toastShow("#000000","#FFFFFF","Account Exists!");
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void updateUI(FirebaseUser user) {
        if (user!= null) {
            Intent i = new Intent(EmailPasswordCreateActivity.this,AccountActivity.class);
            startActivity(i);
            MainActivity.getInstance().toastShow("#000000","#FFFFFF","Account Created");
        }
        finish();
    }
}