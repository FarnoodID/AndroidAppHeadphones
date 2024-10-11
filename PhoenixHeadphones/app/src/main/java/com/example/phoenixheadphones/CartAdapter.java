package com.example.phoenixheadphones;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter{

    List<Cart> modelDetaList;

    public CartAdapter(List<Cart> modelDetail) {
        this.modelDetaList = modelDetail;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        CartAdapter.ViewHolderClass viewHolderClass = new CartAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }// CodeByFarnoodID
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        CartAdapter.ViewHolderClass viewHolderClass = (CartAdapter.ViewHolderClass)holder;

        Cart modelData = modelDetaList.get(position);

        viewHolderClass.name.setText(modelData.getName());
        viewHolderClass.price.setText(modelData.getPrice());
        Log.v("price",String.valueOf(modelData.getPrice()));
        Log.v("image",String.valueOf(modelData.getImage()));
        Picasso.get().load(modelData.getImage()).into(viewHolderClass.image);

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
            image = itemView.findViewById(R.id.product_img_cart);
            name = itemView.findViewById(R.id.product_tv_cart);
            price = itemView.findViewById(R.id.price_tv_cart);

        }
    }
}
