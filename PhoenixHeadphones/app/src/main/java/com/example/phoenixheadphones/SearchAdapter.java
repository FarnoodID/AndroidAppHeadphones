package com.example.phoenixheadphones;

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

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter{

    List<Model> modelDetaList;

    public SearchAdapter(List<Model> modelDetail) {
        this.modelDetaList = modelDetail;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem, parent, false);
        SearchAdapter.ViewHolderClass viewHolderClass = new SearchAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        SearchAdapter.ViewHolderClass viewHolderClass = (SearchAdapter.ViewHolderClass)holder;

        Model modelData = modelDetaList.get(position);

        viewHolderClass.name.setText(modelData.getName());
        viewHolderClass.price.setText(modelData.getPrice());
        Log.v("price",String.valueOf(modelData.getPrice()));
        Log.v("image",String.valueOf(modelData.getImg()));
        Picasso.get().load(modelData.getImg()).into(viewHolderClass.image);
        ((SearchAdapter.ViewHolderClass) holder).image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.getInstance().setProductName(modelData.getName());
                MainActivity.getInstance().setProductPrice(modelData.getPrice());
                MainActivity.getInstance().setProductImg(modelData.getImg());
                MainActivity.getInstance().setProductDetail(modelData.getDetail());
                fragmentManager = MainActivity.getInstance().fragmentManager;
                fragmentTransaction = fragmentManager.beginTransaction();
                ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                fragmentTransaction.replace(R.id.flFragment,productDetailFragment);
                fragmentTransaction.commit();
            }
        });

//        viewHolderClass.image.setImageResource(modelData.getImage());

    }

    @Override
    public int getItemCount() {
        return modelDetaList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name, price;
        ImageView image;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_tv);
            price = itemView.findViewById(R.id.price_tv);

        }
    }
}
