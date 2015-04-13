package com.example.fc.activity;


import android.app.Activity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

public class SecondActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondt);
	}
}
