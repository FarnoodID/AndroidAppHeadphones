package com.example.phoenixheadphones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    List<Cart> itemList;
    CartAdapter itemAdapter;
    DatabaseReference databaseReference;
    Button btn, clrBtn;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        btn = view.findViewById(R.id.btn_checkout);
        clrBtn = view.findViewById(R.id.btn_clr);
        databaseReference = FirebaseDatabase.getInstance().getReference("carts");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Check out",Toast.LENGTH_SHORT).show();
            }
        });

        clrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.getRef().removeValue();
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                fragmentManager = MainActivity.getInstance().fragmentManager;
                fragmentTransaction = fragmentManager.beginTransaction();
                CartFragment cartFragment = new CartFragment();
                fragmentTransaction.replace(R.id.flFragment,cartFragment);
                fragmentTransaction.commit();
            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Log.d("test", snapshot.toString());
                    Cart model = ds.getValue(Cart.class);
                    Log.v("test2", String.valueOf(model.getImage()));
                    itemList.add(model);
                }// CodeByFarnoodID
                itemAdapter = new CartAdapter(itemList);
                recyclerView.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}