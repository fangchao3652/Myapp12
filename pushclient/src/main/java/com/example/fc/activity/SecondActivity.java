package com.example.fc.activity;


import android.app.Activity;
import android.os.Bundle;

import com.example.fc.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondt);
    }
}