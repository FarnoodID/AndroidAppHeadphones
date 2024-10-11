package com.example.phoenixheadphones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<String> itemsArrayList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.ParentRV);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ParentRecyclerAdapter(itemsArrayList, getActivity());
        recyclerView.setAdapter(adapter);
        String[] items = {"Brands", "Headphone types", "Headphone recommended use", "Headphone material", "Headphone connectivity"};
        for (int i = 0; i < items.length; i++)
        {
            itemsArrayList.add(items[i]);
        }
        adapter.notifyDataSetChanged();
        return view;
    }
}