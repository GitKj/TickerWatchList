package com.example.tickerwatchlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;

public class MainActivity extends AppCompatActivity {


    ListView lv_ticker;
    TickerListFragment tickerListFragment;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting message from receiver
        Intent intent = getIntent();
        String SMS = intent.getStringExtra("sms");

        //Toast.makeText(this, "View: " + SMS, Toast.LENGTH_SHORT ).show();


        // sending message to fragment
        //Bundle bundle = new Bundle();
        //bundle.putString("msg", SMS);

        //TickerListFragment tickerListFragment = new TickerListFragment();
        //tickerListFragment.setArguments(bundle);

        wv  = findViewById(R.id.wv_main);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            String[] permissions = new String[]{Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissions, 101);
        }

        if (SMS != null)
        {
            tickerListFragment = (TickerListFragment) getSupportFragmentManager().findFragmentById(R.id.frag_ticker);
            tickerListFragment.validateSMS(SMS);
        }
    }

    public void validateSMS(String SMS) {
        String subFormat = "Ticker:<";
        char[] SMSChar = SMS.toCharArray();


        char lastChar = SMSChar[SMSChar.length - 1];

        //Toast.makeText(this, "Inside validateSMS", Toast.LENGTH_SHORT).show();

        tickerListFragment = new TickerListFragment();

        Toast.makeText(this, "Entries length: " + tickerListFragment.entries.size(), Toast.LENGTH_SHORT).show();

    }


}