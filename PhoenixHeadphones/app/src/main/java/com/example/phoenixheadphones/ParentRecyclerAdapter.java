package com.example.phoenixheadphones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParentRecyclerAdapter extends RecyclerView.Adapter<ParentRecyclerAdapter.MyViewHolder> {

    ArrayList<String> parentArrayList;
    Context context;


    public ParentRecyclerAdapter(ArrayList<String> parentArrayList, Context context) {
        this.parentArrayList = parentArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_rowlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ItemName.setText(parentArrayList.get(position));
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.childRV.setLayoutManager(layoutManager);
        holder.childRV.setHasFixedSize(true);
        ArrayList<Integer> imgArrayList = new ArrayList<>();
        ArrayList<String> nameArrayList = new ArrayList<>();
        if (parentArrayList.get(position).equals("Brands")){
            int[] brandImageArray = {R.drawable.sony, R.drawable.jbl, R.drawable.beats, R.drawable.bose, R.drawable.apple, R.drawable.sennheiser, R.drawable.jabra, R.drawable.philips, R.drawable.microsoft, R.drawable.samsung, R.drawable.shure, R.drawable.lg, R.drawable.baseus, R.drawable.xiaomi, R.drawable.lenovo, R.drawable.akg, R.drawable.anker, R.drawable.razer, R.drawable.logitech, R.drawable.asus, R.drawable.hyperx, R.drawable.steelseries};;
            String[] brandNameArray = {"Sony", "JBL", "Beats", "Bose", "Apple", "Sennheiser", "Jabra", "Philips", "Microsoft", "Samsung Electronics", "Shure", "LG", "Baseus", "Xiaomi", "Lenovo", "AKG", "Anker", "Razer", "Logitech", "Asus", "HyperX", "Steelseries"};
            for (int i = 0; i < brandImageArray.length; i++)
            {
                imgArrayList.add(brandImageArray[i]);
                nameArrayList.add(brandNameArray[i]);
            }
        }
        else if (parentArrayList.get(position).equals("Headphone recommended use")){
            int[] img = {R.drawable.exercising, R.drawable.gaming, R.drawable.recording, R.drawable.running, R.drawable.snowboarding, R.drawable.skateboarding};
            String[] name = {"Exercising Headphone", "Gaming Headphone", "Recording Headphone", "Running Headphone", "Snowboarding Headphone", "SkateBoarding Headphone"};
            for (int i = 0; i < img.length; i++)
            {
                imgArrayList.add(img[i]);
                nameArrayList.add(name[i]);
            }
        }
        else if (parentArrayList.get(position).equals("Headphone material")){
            int[] img = {R.drawable.aluminum, R.drawable.fauxleather, R.drawable.leather, R.drawable.plastic, R.drawable.rubber, R.drawable.stainlesssteel, R.drawable.wood};
            String[] name = {"Aluminum", "Faux Leather", "Leather", "Plastic", "Rubber", "Stainless Steel", "Wood"};
            for (int i = 0; i < img.length; i++)
            {
                imgArrayList.add(img[i]);
                nameArrayList.add(name[i]);
            }
        }
        else if (parentArrayList.get(position).equals("Headphone connectivity")){
            int[] img = {R.drawable.wired, R.drawable.wireless};
            String[] name = {"Wired Headphone", "Wireless Headphone"};
            for (int i = 0; i < img.length; i++)
            {
                imgArrayList.add(img[i]);
                nameArrayList.add(name[i]);
            }
        }
        else if (parentArrayList.get(position).equals("Headphone types")){
            int[] img = {R.drawable.onear, R.drawable.overear, R.drawable.openback, R.drawable.closedback, R.drawable.inear, R.drawable.bluetooth, R.drawable.earbuds, R.drawable.noisecancelling};
            String[] name = {"On-Ear Headphones", "Over-Ear Headphones", "Open-Back Headphones", "Closed-Back Headphones", "In-Ear Headphones", "Bluetooth Headphones", "Earbuds", "Noise-Cancelling Headphones"};
            for (int i = 0; i < img.length; i++)
            {
                imgArrayList.add(img[i]);
                nameArrayList.add(name[i]);
            }
        }
        ChildRecyclerAdapter childRecyclerAdapter = new ChildRecyclerAdapter(imgArrayList, nameArrayList, parentArrayList.get(position));
        holder.childRV.setAdapter(childRecyclerAdapter);
        childRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ItemName;
        RecyclerView childRV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ItemName = itemView.findViewById(R.id.itemname);
            childRV = itemView.findViewById(R.id.childRV);
        }
    }
}
