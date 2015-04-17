package com.example.activity;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;

import com.example.R;
import com.example.view.SwipeBackLayout;


/**
 * 支持向左滑动关闭当前页面的Activity
 *
 * @author FangChao
 */
public class SwipeBackActivity extends ActionBarActivity {
    protected SwipeBackLayout mSwipeBackLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base, null);

        mSwipeBackLayout.attachToActivity(this);
    }


}
