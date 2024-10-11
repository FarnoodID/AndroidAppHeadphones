package com.example.phoenixheadphones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BrandsFragment extends Fragment {

    ListView lvBrand;
    String[] brandNameArray = {"Sony", "JBL", "Beats", "Bose", "Apple", "Sennheiser", "Jabra", "Philips", "Microsoft", "Samsung Electronics", "Shure", "LG", "Baseus", "Xiaomi", "Lenovo", "AKG", "Anker", "Razer", "Logitech", "Asus", "HyperX", "Steelseries"};
    int[] brandImageArray = {R.drawable.sony, R.drawable.jbl, R.drawable.beats, R.drawable.bose, R.drawable.apple, R.drawable.sennheiser, R.drawable.jabra, R.drawable.philips, R.drawable.microsoft, R.drawable.samsung, R.drawable.shure, R.drawable.lg, R.drawable.baseus, R.drawable.xiaomi, R.drawable.lenovo, R.drawable.akg, R.drawable.anker, R.drawable.razer, R.drawable.logitech, R.drawable.asus, R.drawable.hyperx, R.drawable.steelseries};
    String[] brandDesArray = { "Sony Group Corporation, commonly known as Sony and stylized as SONY, is a Japanese multinational conglomerate corporation headquartered in Kōnan, Minato, Tokyo, Japan.", "JBL is an American company that manufactures audio equipment, including loudspeakers and headphones. JBL is owned by Harman International Industries, a subsidiary of Samsung Electronics.", "Beats by Dr. Dre (Beats) is a leading audio brand founded in 2006 by Dr. Dre and Jimmy Iovine. Through its family of premium consumer headphones, earphones and speakers, Beats has introduced an entirely new generation to the possibilities of premium sound entertainment. Beats was acquired by Apple Inc. in July 2014.", "Bose Corporation is an American manufacturing company that predominantly sells audio equipment. The company was established by Amar Bose in 1964 and is based in Framingham, Massachusetts.", "Apple Inc. is an American multinational technology company that specializes in consumer electronics, computer software and online services. Apple is the largest information technology company by revenue and, since January 2021, the world's most valuable company.", "Sennheiser electronic GmbH & Co. KG is a German privately held audio company specializing in the design and production of a wide range of high fidelity products, including microphones, headphones, telephone accessories and aviation headsets for personal, professional and business applications.", "Jabra is a Danish brand specializing in audio equipment, and more recently videoconference systems. It is owned by GN Audio, which is part of the Danish company, GN Group.", "Koninklijke Philips N.V. is a Dutch multinational conglomerate corporation that was founded in Eindhoven in 1891. Since 1997, it has been mostly headquartered in Amsterdam, though the Benelux headquarters is still in Eindhoven.", "Microsoft Corporation is an American multinational technology corporation which produces computer software, consumer electronics, personal computers, and related services.", "Samsung Electronics Co., Ltd. is a South Korean multinational electronics corporation headquartered in the Yeongtong District of Suwon. It is the pinnacle of the Samsung chaebol, accounting for 70% of the group's revenue in 2012.", "Shure Incorporated is an American audio products corporation. It was founded by Sidney N. Shure in Chicago, Illinois, in 1925 as a supplier of radio parts kits.", "LG Corporation, formerly Lucky-Goldstar from 1983 to 1995, is a South Korean multinational conglomerate corporation founded by Koo In-hwoi and managed by successive generations of his family. It is the fourth-largest chaebol in South Korea.", "Baseus is a China-based digital accessory brand. Owned by Shenzhen Besing Technology Co.", "Xiaomi Corporation, registered in Asia as Xiaomi Inc., is a Chinese designer and manufacturer of consumer electronics and related software, home appliances, and household items. Behind Samsung, it is the second largest manufacturer of smartphones.", "Lenovo Group Limited, often shortened to Lenovo, is a Chinese-American multinational technology company specializing in designing, manufacturing, and marketing consumer electronics, personal computers, software, business solutions, and related services.", "AKG Acoustics is an acoustics engineering and manufacturing company. It was founded in 1947 by Rudolf Görike and Ernest Plass in Vienna, Austria. It is a part of Harman International Industries, a subsidiary of Samsung Electronics.", "Anker Innovations is a Chinese electronics company based in Changsha, Hunan. The company is known for producing computer and mobile peripherals including phone chargers, power banks, earbuds, headphones, speakers, data hubs, charging cables, torches, screen protectors, and more under its multiple brands.", "Razer Inc., is a Singaporean-American multinational technology company that designs, develops, and sells consumer electronics, financial services, and gaming hardware. Founded by Min-Liang Tan and Robert Krakoff, it is dual headquartered in one-north, Singapore and Irvine, California, US.", "Logitech International S.A. is a Swiss manufacturer of computer peripherals and software, with headquarters in Lausanne, Switzerland.", "ASUSTek Computer Inc. is a Taiwanese multinational computer and phone hardware and electronics company headquartered in Beitou District, Taipei, Taiwan.", "Kingston Technology Corporation is an American multinational computer technology corporation that develops, manufactures, sells and supports flash memory products and other computer-related memory products, as well as the HyperX gaming division (now owned by HP).", "SteelSeries is a Danish manufacturer of gaming peripherals and accessories, including headsets, keyboards, mice, controllers, and mousepads."};
    private ArrayAdapter<String> listAdapter;
    String brandName;
    String brandDetail;
    int brandImg;
    String BUNDLE_IMAGE = "ImageID";
    String BUNDLE_NAME = "BrandName";
    String BUNDLE_DETAIL = "BrandDetail";
    boolean mTwoPane = false;
    // CodeByFarnoodID


    public void onBrandSelected(int img, String name, String detail) {
        brandImg = img;
        brandName = name;
        brandDetail = detail;
    }
    OnBrandClickListener mCallback;

    public interface OnBrandClickListener{
        void onBrandSelected(int img, String name, String detail);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnBrandClickListener) context;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement OnBrandClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brands, container, false);
        lvBrand = view.findViewById(R. id. lvBrand);
        listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, brandNameArray);
        lvBrand.setAdapter(listAdapter);
        MainActivity activity = (MainActivity) getActivity();
        mTwoPane = activity.getMyData();
        lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String brandName = (String) adapterView.getItemAtPosition(position);
                mCallback.onBrandSelected(brandImageArray[position], brandName, brandDesArray[position]);
                Bundle bundle = new Bundle();
                bundle.putInt(BUNDLE_IMAGE, brandImageArray[position]);
                bundle.putString(BUNDLE_NAME, brandName);
                bundle.putString(BUNDLE_DETAIL, brandDesArray[position]);
//                if(mTwoPane == false)
//                {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
//                }
            }
        });

        return view;
    }
}