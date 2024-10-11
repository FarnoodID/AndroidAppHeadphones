package com.example.phoenixheadphones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;


public class MailTabFragment extends Fragment {
    public EditText mSubject, mMessage;
    public TextView mEmail;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fagView = inflater.inflate(R.layout.mail_tab, container, false);
        mEmail = fagView.findViewById(R.id.tv_mail);
        mSubject = fagView.findViewById(R.id.ed_subject);
        mMessage = fagView.findViewById(R.id.ed_message);
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = fagView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        // Inflate the layout for this fragment
        return fagView;
    }

    private void sendMail(){
        String mail = mEmail.getText().toString().trim();
        String message = mMessage.getText().toString();
        String subject = mSubject.getText().toString().trim();
        if (mAuth.getCurrentUser()!=null) {
            String emailAdd = mAuth.getCurrentUser().getEmail();
            message = message + "\n\n\nEmail sent from user <" + emailAdd + ">";
        }

       JavaMailAPI javaMailAPI = new JavaMailAPI(getActivity(), mail, subject, message);
       javaMailAPI.execute();
    }
}