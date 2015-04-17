package com.example.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.example.R;
import com.example.common.Constants;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.EActivity;

/**
 * 带actionBar的activity基类
 * Created by zhangxinhao on 2015/1/15.
 */
@EActivity
public class BaseActionBarActivity extends SwipeBackActivity {
    /**
     * 当前activity的全路径标识
     */
    protected String TAG = getClass().getSimpleName();
    /**
     * actionBar  V7
     */
    protected ActionBar mActionBar;

    /**
     * actionBar标题
     */
    protected String title;

    /**
     * 设置actionBar是否显示logo
     */
    protected boolean showIcon = false;
    /**
     * 设置actionBar是否显示返回按钮
     */
    protected boolean showBackPress = true;
    /**
     * 设置actionBar是否显示title
     */
    protected boolean showTitle = true;
    /**
     * 设置是否支持滑动关闭
     */
    protected boolean SwipeBack = true;
    /**
     * 设置是否显示ProgressDialog
     */
    protected boolean isshowprodialog = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (SharedPreferencesUtils.getInstance().isNightTheme()) {
           setTheme(R.style.AppTheme_Night);
        } else {
           setTheme(R.style.AppTheme_Normal);
        }
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initActionBar();
        initSwipeBack();
    }


    /**
     * 初始化actionBar
     */
    void initActionBar() {
        mActionBar = getSupportActionBar();
        if (!StringUtils.isBlank(title))
            mActionBar.setTitle(title);//设置页面标题
        //设置是否显示logo
        mActionBar.setDisplayShowHomeEnabled(showIcon);
        //设置是否显示返回按钮
        mActionBar.setDisplayHomeAsUpEnabled(showBackPress);
        //设置是否显示title
        mActionBar.setDisplayShowTitleEnabled(showTitle);
        //设置actionbar  progress dialog
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layout.gravity = Gravity.RIGHT;
        View v = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_progress, null);
        mActionBar.setCustomView(v, layout);
        mActionBar.setDisplayShowCustomEnabled(isshowprodialog);
    }

    /**
     * 是否显示右上角ProgressDialog  wheel
     *
     * @param b
     */
    void setprogressdialogEnable(boolean b) {
        this.isshowprodialog = b;
        if (mActionBar != null)
            mActionBar.setDisplayShowCustomEnabled(isshowprodialog);

    }

    /**
     * 初始化滑动关闭
     */
    void initSwipeBack() {
        setSlidable(SwipeBack);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
    }

    public void onResume() {
        super.onResume();
        if (!Constants.UMENG) {
            MobclickAgent.onPageStart(TAG); // 统计页面
            MobclickAgent.onResume(this); // 统计时长
        }
    }

    public void onPause() {
        super.onPause();
        if (!Constants.UMENG) {
            MobclickAgent.onPageEnd(TAG); // 保证 onPageEnd 在onPause
            // 之前调用,因为 onPause 中会保存信息
            MobclickAgent.onPause(this);// 统计时长
        }
    }

    /**
     * 设置右滑finish 标志位(不用直接调用，在initview 时设置标志位SwipeBack即可 )
     *
     * @param allowslide_flage
     */
    public void setSlidable(boolean allowslide_flage) {
        this.SwipeBack = allowslide_flage;
        mSwipeBackLayout.setAllowslide_flage(allowslide_flage);
    }

    /**
     * 收起软键盘
     */
    public void collapseSoftInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (this.getCurrentFocus() != null
                    && this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
