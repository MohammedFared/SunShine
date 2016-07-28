package com.example.mayarfareed.sunshineapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private TextView textView;
    private String data;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        //up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //get data from the last activity
        Intent intent= getIntent();
        data = intent.getStringExtra("data");
        textView= (TextView) findViewById(R.id.textView);
        textView.setText(data);
    }
    private Intent shareforecastintent(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        shareIntent.setType("Text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, data);
        Log.d("gogo", "shareforecastintent: "+data);
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = new MenuInflater(this);
        getMenuInflater().inflate(R.menu.details, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);


        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Log.d("goo", "shareforecastintent: "+mShareActionProvider+ item.getTitle());
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareforecastintent());
            Log.d("goo", "shareforecastintent: "+data);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
