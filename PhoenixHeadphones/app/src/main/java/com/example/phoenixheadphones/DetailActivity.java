package com.example.phoenixheadphones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    String BUNDLE_IMAGE = "ImageID";
    String BUNDLE_NAME = "BrandName";
    String BUNDLE_DETAIL = "BrandDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        BrandDetailFragment brandDetailFragmentt = new BrandDetailFragment();
        brandDetailFragmentt.setBrandImg(getIntent().getIntExtra(BUNDLE_IMAGE, 0));
        brandDetailFragmentt.setBrandName(getIntent().getStringExtra(BUNDLE_NAME));
        brandDetailFragmentt.setBrandDetail(getIntent().getStringExtra(BUNDLE_DETAIL));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerDetail, brandDetailFragmentt).commit();
    }
}