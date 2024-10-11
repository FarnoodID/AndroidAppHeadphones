package com.example.phoenixheadphones;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ChildRecyclerAdapter extends RecyclerView.Adapter<ChildRecyclerAdapter.MyViewHolder>{

    ArrayList<Integer> arrayList;
    ArrayList<String> arrayList2;
    String select;
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;

    public ChildRecyclerAdapter(ArrayList<Integer> arrayList, ArrayList<String> arrayList2, String select) {
        this.arrayList = arrayList;
        this.arrayList2 = arrayList2;
        this.select = select;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rowlayout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgs.setImageResource(arrayList.get(position));
        holder.txt.setText(arrayList2.get(position));
        int i = position;
        holder.imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().setChild(arrayList2.get(i));
                MainActivity.getInstance().setSelect(select);
                Log.v("inja1", arrayList2.get(i));
                Log.v("inja2", select);
                fragmentManager = MainActivity.getInstance().fragmentManager;
                fragmentTransaction = fragmentManager.beginTransaction();
                ProductsFragment productsFragment = new ProductsFragment();
                fragmentTransaction.replace(R.id.flFragment,productsFragment);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgs;
        TextView txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgs = itemView.findViewById(R.id.recycler_img);
            txt = itemView.findViewById(R.id.txt_name);
        }
    }

}
