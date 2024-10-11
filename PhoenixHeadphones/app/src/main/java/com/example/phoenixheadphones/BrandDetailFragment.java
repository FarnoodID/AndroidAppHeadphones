package com.example.phoenixheadphones;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BrandDetailFragment extends Fragment {
    ImageView imgbrand;
    TextView tvBrandName, tvBrandDetail;
    String brandName = "";
    String brandDetail = "";
    int brandImg = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_detail, container, false);
        imgbrand = view.findViewById(R.id.brand_img);
        tvBrandName = view.findViewById(R.id.tv_brand);
        tvBrandDetail = view.findViewById(R.id.tv_brand_detail);
        // CodeByFarnoodID
        imgbrand.setImageResource(brandImg);
        tvBrandName.setText(brandName);
        tvBrandDetail.setText(brandDetail);
        return view;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandDetail(String brandDetail) {
        this.brandDetail = brandDetail;
    }

    public void setBrandImg(int brandImg) {
        this.brandImg = brandImg;
    }
}