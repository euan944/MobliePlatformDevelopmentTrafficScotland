package org.me.gcu.traffic_scotland;
//Adds imports to the Java file

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.content.BroadcastReceiver;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import org.me.gcu.traffic_scotland.databinding.ActivityMainBinding;

/**
 * @author Euan Penman S1821916
 */
public class MainActivity extends AppCompatActivity   {
    //Sets up priavte varibales for this class
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    ServiceManager ServiceManager = new ServiceManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                Each page must be included to have a new view
                R.id.nav_home, R.id.nav_date_plannerFragment, R.id.nav_journey_plannerFragment, R.id.nav_road_searchFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

//Extra code for airplane mode to alert the user it has been enabled
//        ConnectionReceiver ConnectionReceiver = new ConnectionReceiver();
//
//
//        @Override
//        protected void onStart() {
//            super.onStart();
//            IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//            registerReceiver(ConnectionReceiver, filter);
//        }
//
//        @Override
//        protected void onStop() {
//            super.onStop();
//            unregisterReceiver(ConnectionReceiver);
//        }


//The below creates a new filter to register with the serviermanager
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(ServiceManager, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(ServiceManager);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}