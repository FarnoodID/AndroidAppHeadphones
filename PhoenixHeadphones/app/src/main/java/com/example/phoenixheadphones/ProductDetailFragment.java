package com.example.phoenixheadphones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ProductDetailFragment extends Fragment {


    private BottomSheetDialog bottomSheetDialog;
    ImageView img;
    TextView name, price, detail;
    FirebaseDatabase database;
    DatabaseReference reference;
    Cart carts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        img  = view.findViewById(R.id.img_det);
        name = view.findViewById(R.id.tv_name_det);
        price = view.findViewById(R.id.tv_price_det);
        detail = view.findViewById(R.id.tv_detail_det);
        carts = new Cart();

        Picasso.get().load(MainActivity.getInstance().getProductImg()).into(img);
        name.setText(MainActivity.getInstance().getProductName());
        price.setText(MainActivity.getInstance().getProductPrice());
        detail.setText(MainActivity.getInstance().getProductDetail());
        detail.setMovementMethod(new ScrollingMovementMethod());

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetDialogView = getLayoutInflater().inflate(R.layout.buttomsheet_layout, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);

        View cart = bottomSheetDialogView.findViewById(R.id.cart);



        ImageButton button = view.findViewById(R.id.btn_cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("carts");
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        getValues();
                        reference.push().setValue(carts);
                        Toast.makeText(getActivity(), "Add to cart", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.cancel();
            }
        });

        return view;
    }
    private void getValues()
    {
        carts.setName(MainActivity.getInstance().getProductName());
        carts.setPrice(MainActivity.getInstance().getProductPrice());
        carts.setImage(MainActivity.getInstance().getProductImg());
    }
}