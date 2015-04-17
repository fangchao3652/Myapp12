package com.example.fc.activity;


import android.app.Activity;
import android.os.Bundle;

import com.example.R;

import org.androidannotations.annotations.EActivity;


public class firstActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);
    }
}
