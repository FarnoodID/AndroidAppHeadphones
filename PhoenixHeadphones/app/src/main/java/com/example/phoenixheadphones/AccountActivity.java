package com.example.phoenixheadphones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    TextView tvVerifyState,tvEmailAddress;
    Button btnCloseAccount,btnVerify, btnChangePass;
    EditText passNew,passRenew;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // [declare_auth]
        setContentView(R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        tvVerifyState = findViewById(R.id.tv_verify_state);
        tvEmailAddress = findViewById(R.id.tv_email_address);
        btnChangePass = findViewById(R.id.btn_change_pass);
        btnCloseAccount = findViewById(R.id.btn_close_account);
        btnVerify = findViewById(R.id.btn_verify);
        passNew = findViewById(R.id.pass_new);
        passRenew = findViewById(R.id.pass_renew);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailVerification();
                MainActivity.getInstance().toastShow("#000000","#FFFFFF","Email verification has been sent");
                mAuth.signOut();
                Intent i = new Intent(AccountActivity.this,EmailPasswordActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnCloseAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            signIn();
            finish();
            // CodeByFarnoodID
        }
        else {
            tvEmailAddress.setText(currentUser.getEmail());
            if (mAuth.getCurrentUser().isEmailVerified())
                tvVerifyState.setText("Verified");
            else
                tvVerifyState.setText("Not Verified");
        }

    }


    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
        // [END send_email_verification]
    }
    private void signIn() {
        Intent  i = new Intent(AccountActivity.this,EmailPasswordActivity.class);
        startActivity(i);
    }
    private void changePassword(){
        String newPass = String.valueOf(passNew.getText());
        String renewPass = String.valueOf(passRenew.getText());
        if (!newPass.equals("") && !renewPass.equals("")) {
            final FirebaseUser user = mAuth.getCurrentUser();
            if (newPass.equals(renewPass)) {
                user.updatePassword(newPass);
                MainActivity.getInstance().toastShow("#000000", "#FFFFFF", "Password Changed");
                finish();
            }
            else
                MainActivity.getInstance().toastShow("#000000","#FFFFFF","Passwords don't match");
        }
        else{
            MainActivity.getInstance().toastShow("#000000","#FFFFFF","Boxes above can't be empty");
        }
    }
}