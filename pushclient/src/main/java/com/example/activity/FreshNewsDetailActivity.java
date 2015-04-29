package com.example.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.Bean.FreshNews;
import com.example.R;
import com.example.fragment.FreshNewsDetailFragment;
import com.example.fragment.FreshNewsDetailFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * Created by fangchao on 2015/4/28.
 */
@EActivity(R.layout.activity_freshdetails)
public class FreshNewsDetailActivity extends BaseActionBarActivity {
    @Extra
    ArrayList<FreshNews> FreshNewsList;
    @Extra
    int position = 0;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @AfterViews
    void init() {
        title = "";
        viewPager.setAdapter(new FreshNewsDetailAdapter(getSupportFragmentManager(), FreshNewsList));
        viewPager.setCurrentItem(position);
    }


    private class FreshNewsDetailAdapter extends FragmentPagerAdapter {

        private ArrayList<FreshNews> freshNewses;

        public FreshNewsDetailAdapter(FragmentManager fm, ArrayList<FreshNews> freshNewses) {
            super(fm);
            this.freshNewses = freshNewses;
        }

        @Override
        public Fragment getItem(int position) {

            return      FreshNewsDetailFragment_.builder().freshNews(freshNewses.get(position)).build();
        }

        @Override
        public int getCount() {
            return freshNewses.size();
        }
    }


}
