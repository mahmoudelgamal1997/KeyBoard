package com.example2017.android.keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Advertise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
