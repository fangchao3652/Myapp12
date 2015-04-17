package com.example.activity;

import android.os.Handler;

import com.example.common.SharedPreferencesUtils;
import com.example.db.SqliteHelper;
import com.example.R;
import com.example.fc.activity.MqttService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;


/**
 * 欢迎界面
 *
 * @author zxh
 */
@EActivity(R.layout.activity_welcome)
@Fullscreen
public class WelcomeActivity extends BaseActivity {
    @AfterViews
    void initView() {

        if (SharedPreferencesUtils.getInstance().getIsopensent()) {
            //打开推送
            MqttService.actionStart(WelcomeActivity.this);
        }
if(SharedPreferencesUtils.getInstance().isLogin()){
    MqttService.actionStop(this);
    //添加主题
    MqttService.AddTopic(SharedPreferencesUtils.getInstance().getUserMessage().getMemberId());
    MqttService.AddTopic("计算机112");
    MqttService.actionStart(this);
}

        jump();
    }

    /**
     * 跳转到首页
     */
    void jump() {
//        HomeActivity_.intent(this).start();
//        this.finish();
        SqliteHelper.insertDatabase();
        //为了单独应用动画而修改
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
              /*  if (SharedPreferencesUtils.getInstance().isFirstLogin()) {
                    //第一次登陆跳转导读页面
                    // GuidanceActivity_.intent(WelcomeActivity.this).start();
                    close();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
             */
                    HomeActivity_.intent(WelcomeActivity.this).start();
                    close();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }

        }, 1000);
    }

    @Override
    public void onBackPressed() {
    }


    @Background(delay = 400)
    void close() {
        finish();
    }
}