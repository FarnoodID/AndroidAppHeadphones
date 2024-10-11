package com.example.phoenixheadphones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Provider;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BrandsFragment.OnBrandClickListener, SensorEventListener {

    private static SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    String SP_NIGHTMODE = "nightMode";

    public FragmentManager fragmentManager ;
    private static MainActivity instance;

    public static String select = "";
    public static String child = "";
    public static String getSelect() {
        return select;
    }

    public static void setSelect(String select) {
        MainActivity.select = select;
    }

    public static String getChild() {
        return child;
    }

    public static void setChild(String child) {
        MainActivity.child = child;
    }



    FragmentTransaction fragmentTransaction;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public MenuItem nightbutton;
    public Fragment globalFragment;
    Toolbar toolbar;

    //Brand descripsion variables
    ListView lvBrand;
    String brandName;
    String brandDetail;
    int brandImg;
    boolean mTwoPane = false;

    static boolean nightmodeswitched = false;
    static Fragment fragment;

    //Broadcast receiver
    private WifiManager wifiManager;

    //Light sensor
    SensorManager sensorManager;
    Sensor sensor;
    public static boolean dark = false;
    private static boolean switch_mode = false;

    //light
    Switch switchLight;
    String SP_THEME = "THEME";

    //Account
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        Log.v("nightmodeswitched",String.valueOf(nightmodeswitched));

        setNavigationViewListener();

        //Account
        mAuth = FirebaseAuth.getInstance();

        sharedPref = getSharedPreferences("phoenix_settings",MODE_PRIVATE);
        editor = sharedPref.edit();
        //Broadcast receiver
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


         toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        toolbar.setTitleTextColor(0xFFFFFFFF);


        addMainFragment();

        if(nightmodeswitched)
        {
            nightmodeswitched = false;
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment,fragment);
            fragmentTransaction.commit();
        }
        //Brand description
//        if(findViewById(R.id.constraintLayoutTwoPane) != null)
//        {
             mTwoPane = true;
//        }else{
//            mTwoPane = false;
//        }


        //Light sensor
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }
    //light sensor
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    //Broadcast receiver
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
//                    Toast.makeText(MainActivity.this,"Wifi is turned on",Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(MainActivity.this,"Wifi is turned off. Please turn on to access application",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        nightbutton = menu.findItem(R.id.app_bar_night);
        if (sharedPref.getBoolean(SP_NIGHTMODE, false)) {
            nightbutton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_wb_sunny_24));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String line1 = "Searched \"" + query+ "\"";
                String line2 = "Results for \"" + query + "\":";
                toastShow("#000000","#FFFFFF",line1);

//                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
//                intent.putExtra("text",line2);
//                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                // set Fragmentclass Arguments
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flFragment,searchFragment);
                fragmentTransaction.commit();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        navigationView.setCheckedItem(R.id.app_bar_switch);
        navigationView.getMenu().performIdentifierAction(R.id.app_bar_switch, 0);
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//
//
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }

        switch (item.getItemId()){
            case R.id.app_bar_night:
                if(!switch_mode) {
                    if (sharedPref.getBoolean(SP_NIGHTMODE, false)) { // It is turning to Light mode
                        nightbutton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_moon));
                        toastShow("#000000", "#FFFFFF", "Light Mode Activated");
                        Log.v("NIGHTMODE", "was on");
                        editor.putBoolean(SP_NIGHTMODE, false);
                        editor.apply();
                        nightmodeswitched = true;
                        fragment = fragmentManager.findFragmentById(R.id.flFragment);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
//
                    } else { // It is turning to dark mode
                        nightbutton.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_wb_sunny_24));
                        toastShow("#000000", "#FFFFFF", "Night Mode Activated");
                        Log.v("NIGHTMODE", "was off");
                        editor.putBoolean(SP_NIGHTMODE, true);
                        editor.apply();

                        nightmodeswitched = true;
                        fragment = fragmentManager.findFragmentById(R.id.flFragment);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    }
                }
                break;

        }
        return super.onOptionsItemSelected(item);



    }

    NavigationView navigationView;
    private void setNavigationViewListener() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//     switchLight = navigationView.getMenu().findItem(R.id.switch_light);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.nav_cart:
                toastShow("#000000","#FFFFFF","Cart");
                fragmentTransaction = fragmentManager.beginTransaction();
                CartFragment cartFragment = new CartFragment();
                fragmentTransaction.replace(R.id.flFragment,cartFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_account:
                toastShow("#000000","#FFFFFF","My Account");
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_brands:
                toastShow("#000000","#FFFFFF","Brands");
                BrandsFragment brandsFragment = new BrandsFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flFragment,brandsFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_note:
                toastShow("#000000","#FFFFFF","Settings");
                NoteFragment noteFragment = new NoteFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flFragment,noteFragment);
                fragmentTransaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.app_bar_switch:
                //MenuItem menuItem = navigationView.getMenu().findItem(R.id.switchToggleButton); // This is the menu item that contains your switch
                Switch drawerSwitch = (Switch) item.getActionView().findViewById(R.id.switch_light);
                drawerSwitch.setChecked(sharedPref.getBoolean(SP_THEME, false));
                if(drawerSwitch.isChecked())
                    switch_mode = true;
                else
                    switch_mode = false;
                drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        editor.putBoolean(SP_THEME, isChecked);
                        editor.commit();
                        if (isChecked) {
                            Toast.makeText(MainActivity.this, "Switch turned on", Toast.LENGTH_SHORT).show();
                            switch_mode = true;
                        } else {
                            Toast.makeText(MainActivity.this, "Switch turned off", Toast.LENGTH_SHORT).show();
                            switch_mode = false;
                        }
                    }
                });
                break;
            case R.id.nav_help:
                Intent i = new Intent(MainActivity.this,HelpActivity.class);
                startActivity(i);
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_logout:
                toastShow("#000000","#FFFFFF","Logged out");
                drawerLayout.closeDrawers();
                mAuth.signOut();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.findFragmentByTag("mainFrag");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }
        else if (fragment == null ){
            fragmentTransaction = fragmentManager.beginTransaction();
            MainFragment mainFragment = new MainFragment();
            fragmentTransaction.replace(R.id.flFragment,mainFragment,"mainFrag");
            fragmentTransaction.commit();
        }else{
            super.onBackPressed();
        }
    }

    private void addMainFragment (){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.add(R.id.flFragment,mainFragment,"mainFrag");
        fragmentTransaction.commit();
    }

    public void toastShow (String backgroundColor, String textColor, String text){
        Toast toast;
        View view;
        TextView textView;
        toast = Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT);
        view = toast.getView();
        textView = (TextView) view.findViewById(android.R.id.message);
        textView.setTextColor(Color.parseColor(textColor));
        (view.getBackground()).setColorFilter(Color.parseColor(backgroundColor), PorterDuff.Mode.SRC_IN);
        toast.show();
    }
    public boolean isDarkThemeOn (){
        Configuration configuration = new Configuration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
                return false;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                return true;
        }
        return false;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    //Brand description method
    @Override
    public void onBrandSelected(int img, String name, String detail) {
        brandImg = img;
        brandName = name;
        brandDetail = detail;
//        if(mTwoPane)
//        {
//            BrandDetailFragment brandDetailFragmentt = new BrandDetailFragment();
//            brandDetailFragmentt.setBrandImg(img);
//            brandDetailFragmentt.setBrandName(name);
//            brandDetailFragmentt.setBrandDetail(detail);
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fragmentContainerDetai, brandDetailFragmentt).commit();
//        }
    }

    public Boolean getMyData() {
        return mTwoPane;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.values[0] < 20000)
        {
            dark = true;
        }
        else
        {
            dark = false;
        }
        if(switch_mode) {
            if (dark) {
//                toastShow("#000000", "#FFFFFF", "Night Mode Activated");
                Log.v("NIGHTMODE", "was off");
                editor.putBoolean(SP_NIGHTMODE, true);
                editor.apply();
                nightmodeswitched = true;
                fragment = fragmentManager.findFragmentById(R.id.flFragment);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
//                toastShow("#000000", "#FFFFFF", "Light Mode Activated");
                Log.v("NIGHTMODE", "was on");
                editor.putBoolean(SP_NIGHTMODE, false);
                editor.apply();
                nightmodeswitched = true;
                fragment = fragmentManager.findFragmentById(R.id.flFragment);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public static String productName;
    public static String productPrice;
    public static String productDetail;
    public static String productImg;

    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        MainActivity.productName = productName;
    }

    public static String getProductPrice() {
        return productPrice;
    }

    public static void setProductPrice(String productPrice) {
        MainActivity.productPrice = productPrice;
    }

    public static String getProductDetail() {
        return productDetail;
    }

    public static void setProductDetail(String productDetail) {
        MainActivity.productDetail = productDetail;
    }

    public static String getProductImg() {
        return productImg;
    }

    public static void setProductImg(String productImg) {
        MainActivity.productImg = productImg;
    }




}
