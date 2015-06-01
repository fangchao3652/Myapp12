package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.viewpager.PagerSlidingTab;
import com.example.R;
import com.example.adapter.FragmentPageAdapter;
import com.example.common.CommonUtils;
import com.example.common.CustomToast;
import com.example.common.Exit;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;
import com.example.data.DataHelper;
import com.example.fragment.FragmentFresh_;
import com.example.fragment.FragmentNewsList_;
import com.example.fragment.FragmentRecommend_;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页activity
 * Created by fc on 2015/1/16.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActionBarActivity implements DataHelper.DataListener {
    @ViewById(R.id.tabs)
    PagerSlidingTab tabs;
    @ViewById(R.id.pager)
    ViewPager pager;
    ActionBar actionBar;
    Exit exit = new Exit();
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private boolean flg;
    /**
     * 左滑个人中心
     */
    private SlidingMenu menu;
    private LinearLayout view_search;
    private ImageView img_search;
    private FragmentPageAdapter mAdapter;
    private List<Fragment> fragments;
    private List<String> titles;
    private boolean isHide = false;


    public ActionBar getActionBar1() {
        return actionBar;
    }

    public void setActionBar1(ActionBar actionBar) {
        this.actionBar = actionBar;
    }

    /**
     * 初始化View
     */
    @AfterViews
    void initView() {
        flg = SharedPreferencesUtils.getInstance().isNightTheme();
        showIcon = true;
        showBackPress = false;
        showTitle = false;
        SwipeBack = false;
        actionBar = mActionBar;

        initSlidingMenu();
        initTabView();
        initData();
        initViewPager();
       /* if (!UpdateUtils.getInstance(this).isDownload()) {
            checkUpdate();
        }*/
    }

    private void initData() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();   //0.科大要闻  1.校园传真   2.媒体科大 3.校园公告  4.校内通知  5 学术动态   6.banner
        fragments.add(FragmentRecommend_.builder().type(3).build());
        titles.add("校园公告");  //里面包含 banner

        fragments.add(FragmentNewsList_.builder().type(0).build());
        titles.add("科大要闻");
        fragments.add(FragmentNewsList_.builder().type(1).build());
        titles.add("校园传真");
        fragments.add(FragmentNewsList_.builder().type(2).build());
        titles.add("媒体科大");
        fragments.add(FragmentNewsList_.builder().type(4).build());
        titles.add("校内通知");
        fragments.add(FragmentNewsList_.builder().type(5).build());
        titles.add("学术动态");
        fragments.add(FragmentFresh_.builder().build());
        titles.add("新鲜事");
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (menu.isMenuShowing()) {
            menu.toggle();
        }
    }

    /**
     * 初始化TitleTab属性
     */
    private void initTabView() {
        tabs.setIndicatorHeight(10);
        tabs.setTextSize(CommonUtils.spTopx(this, 15f));
    }

    private void initViewPager() {
        mAdapter = new FragmentPageAdapter(getSupportFragmentManager(), pager, fragments, titles);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 6) {
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);


                } else {
                    menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 个人中心
     */
    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.menu_right);
    }

    void initActionBar() {
        mActionBar = getSupportActionBar();
        if (!StringUtils.isBlank(title))
            mActionBar.setTitle(title);//设置页面标题
        //设置是否显示logo
        mActionBar.setDisplayShowHomeEnabled(true);
        //设置是否显示返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(false);
        //设置是否显示title
        mActionBar.setDisplayShowTitleEnabled(showTitle);
        //设置actionbar  progress dialog
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.RIGHT;
        /*View v = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_home, null);
        mActionBar.setCustomView(v, layout);
        img_search = (ImageView) v.findViewById(R.id.img_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity_.intent(HomeActivity.this).start();
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flg != SharedPreferencesUtils.getInstance().isNightTheme()) {
            //更改当前页面的主题
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_person:
                menu.toggle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.toggle();
        } else {
            if (exit.isExit()) {
                finish();
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            } else {
                CustomToast.showToast(R.string.exit_message, HomeActivity.this, Toast.LENGTH_SHORT);

                exit.doExitInOneSecond();
            }
        }
    }

    public SlidingMenu getMenu() {
        return menu;
    }

    @Override
    public void sucess(JSONObject response, int code) {
        switch (code) {
            case 100:

                break;
        }
    }

    @Override
    public void err(String error, int code) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        isHide = true;
        super.onSaveInstanceState(outState);
    }
}
