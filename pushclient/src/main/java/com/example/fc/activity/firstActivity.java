package com.example.fc.activity;


import android.widget.TextView;

import com.example.R;
import com.example.activity.BaseActionBarActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.first)
public class firstActivity extends BaseActionBarActivity {
    @Extra
    String title = "";
    @Extra
    String content = "";
    @ViewById(R.id.Tv_content)
    TextView Tv_content;

    @AfterViews
    void init() {
        super.title = this.title;
        Tv_content.setText(content);
    }
}
