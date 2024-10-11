package com.example.phoenixheadphones;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class CallTabFragment extends Fragment {

    Button button;
    @Override
    // CodeByFarnoodID
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_tab, container, false);
        button = view.findViewById(R.id.btn_call);
        Context context;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: +9812345678"));
                if(callIntent.resolveActivity(getActivity().getPackageManager()) != null)
                {
                    startActivity(callIntent);
                }
                else
                {
                    Toast.makeText(getActivity(), "Sorry, no app can handle this action and data.", Toast.LENGTH_SHORT);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}