package com.example.mayarfareed.sunshineapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements forecastFragment.OnFragmentInteractionListener{

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        Log.d("MainActivitylog", "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivitylog", "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d("MainActivitylog", "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("MainActivitylog", "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.d("MainActivitylog", "onStart: ");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivitylog", "onCreate: ");
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.mainActivity, new forecastFragment()).commit();
        }
    }
}
