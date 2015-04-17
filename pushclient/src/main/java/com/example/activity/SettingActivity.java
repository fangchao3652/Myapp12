package com.example.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.R;
import com.example.common.BitmapCache;
import com.example.common.CommonUtils;
import com.example.common.CustomToast;
import com.example.common.SharedPreferencesUtils;
import com.example.common.StringUtils;
import com.example.data.DiskDataHelper;
import com.example.fc.activity.MqttService;
import com.example.view.ProgressDialog;
import com.example.view.UISwitchButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;


/**
 * 设置界面
 *
 * @author Fangchao
 */
@EActivity(R.layout.activity_setting)
public class SettingActivity extends BaseActionBarActivity {
    @ViewById(R.id.setting_selected_time)
    TextView tv_quiteTime;//安静时段
    @ViewById(R.id.setting_tvCache)//缓存大小
            TextView setting_tvCache;
    @ViewById(R.id.setting_showHimg)
    RelativeLayout setting_showHimg;// 高清
    @ViewById(R.id.setting_showMimg)
    RelativeLayout setting_showMimg;// 标清
    @ViewById(R.id.setting_showNimg)
    RelativeLayout setting_showNimg;// 不显示
    @ViewById(R.id.cb_showHimg)
    CheckBox cb_showHimg;// 高清
    @ViewById(R.id.cb_showMimg)
    CheckBox cb_showMimg;// 标清
    @ViewById(R.id.cb_showNimg)
    CheckBox cb_showNimg;// 不显示

    @ViewById(R.id.setting_tvCache)
    TextView tvCache;
    @ViewById(R.id.setting_removeCache) //清除缓存
            RelativeLayout rlRemoveCache;

    @ViewById(R.id.switch_open_send)//是否开启推送
            UISwitchButton switch_open_sent;


    @ViewById(R.id.setting_quiet_time)//安静时段 rl
            RelativeLayout rl_setting_quiet_time;
    ProgressDialog md;

    @ViewById(R.id.person_detail_exit)
    Button btn_exit;//退出登录
    TextView starttime, lasttime, tv_time;
    int timestart = 0, timelast = 0;

    @AfterViews
    void initview() {
        int imgQuerity = SharedPreferencesUtils.getInstance().getImgQuerity();
        if (1 == imgQuerity) {
            cb_showHimg.setChecked(true);
            cb_showMimg.setChecked(false);
            cb_showNimg.setChecked(false);
        } else if (2 == imgQuerity) {
            cb_showHimg.setChecked(false);
            cb_showMimg.setChecked(true);
            cb_showNimg.setChecked(false);
        } else if (3 == imgQuerity) {
            cb_showHimg.setChecked(false);
            cb_showMimg.setChecked(false);
            cb_showNimg.setChecked(true);
        }


        switch_open_sent.setChecked(SharedPreferencesUtils.getInstance().getIsopensent());
        md = ProgressDialog.createDialog(this);
        if (SharedPreferencesUtils.getInstance().getIsopensent()) {
            rl_setting_quiet_time.setVisibility(View.VISIBLE);
        } else {
            rl_setting_quiet_time.setVisibility(View.GONE);
        }

        if (SharedPreferencesUtils.getInstance().isLogin()) {
            btn_exit.setVisibility(View.VISIBLE);
        } else {
            btn_exit.setVisibility(View.GONE);

        }
    }

    @Click({R.id.setting_removeCache, R.id.person_detail_exit,
            R.id.setting_showHimg, R.id.setting_showMimg, R.id.setting_quiet_time,
            R.id.setting_showNimg})
    void click(View v) {
        switch (v.getId()) {
            case R.id.setting_removeCache:// 清除缓存
                if ("0B".equals(setting_tvCache.getText().toString())) {
                    CustomToast.showToast("没有缓存", this);
                } else {
                    showBasicNoTitle();
                }
                break;

            case R.id.setting_showHimg:
                if (!cb_showHimg.isChecked()) {
                    CustomToast.showToast("显示高清图片！", this);
                    cb_showHimg.setChecked(true);
                    cb_showMimg.setChecked(false);
                    cb_showNimg.setChecked(false);
                    SharedPreferencesUtils.getInstance().putImgQuerity(1);
                }
                break;
            case R.id.setting_showMimg:
                if (!cb_showMimg.isChecked()) {
                    CustomToast.showToast("显示标清图片！(节省约70%的流量)", this);
                    cb_showHimg.setChecked(false);
                    cb_showMimg.setChecked(true);
                    cb_showNimg.setChecked(false);
                    SharedPreferencesUtils.getInstance().putImgQuerity(2);
                }
                break;
            case R.id.setting_showNimg:
                if (!cb_showNimg.isChecked()) {
                    CustomToast.showToast("不显示图片！", this);
                    cb_showHimg.setChecked(false);
                    cb_showMimg.setChecked(false);
                    cb_showNimg.setChecked(true);
                    SharedPreferencesUtils.getInstance().putImgQuerity(3);
                }
                break;

            case R.id.setting_quiet_time://安静时段
                showCustomView();
                break;
            case R.id.person_detail_exit:
                SharedPreferencesUtils.getInstance().cleanUserMessage();
                setResult(RESULT_OK);
                finish();
                overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
                break;

        }
    }

    private void showCustomView() {


        SeekBar startseekbar;
        SeekBar lasttseekbar;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("安静时段")
                .customView(R.layout.dialog_selecttime, true)
                .positiveText(R.string.dialog_ok)
                .negativeText(android.R.string.cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        SharedPreferencesUtils.getInstance().putPushStarttime(timestart);
                        SharedPreferencesUtils.getInstance().putLastTime(timelast);
                        SetTime(tv_quiteTime, SharedPreferencesUtils.getInstance().getPushStarttime(), SharedPreferencesUtils.getInstance().getLastTime());
                        //Toast.makeText(getApplicationContext(), "Password: " + passwordInput.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                    }
                }).build();

        timestart = SharedPreferencesUtils.getInstance().getPushStarttime();
        timelast = SharedPreferencesUtils.getInstance().getLastTime();
        tv_time = (TextView) dialog.getCustomView().findViewById(R.id.time_txt);
        starttime = (TextView) dialog.getCustomView().findViewById(R.id.starttime_txt);//开始时间
        lasttime = (TextView) dialog.getCustomView().findViewById(R.id.lasttime_txt);//持续时间

        startseekbar = (SeekBar) dialog.getCustomView().findViewById(R.id.seekbar_starttime);//开始时间seekbar
        lasttseekbar = (SeekBar) dialog.getCustomView().findViewById(R.id.seekbar_last);//持续时间seekbar
        SetTime(tv_time, timestart, timelast);
        starttime.setText(String.format(getResources().getString(R.string.starttime), timestart));
        lasttime.setText(String.format(getResources().getString(R.string.lastime), timelast));

        startseekbar.setProgress(timestart * 10);
        lasttseekbar.setProgress(timelast * 10);
        startseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timestart = progress / 10;
                starttime.setText(String.format(getResources().getString(R.string.starttime), timestart));

                SetTime(tv_time, timestart, timelast);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lasttseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timelast = progress / 10;
                lasttime.setText(String.format(getResources().getString(R.string.lastime), timelast));
                SetTime(tv_time, timestart, timelast);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.show();

    }

    private String SetTime(TextView tv_time, int start, int last) {
        String str = "";
        if (last == 0) {
            tv_time.setText("安静时段：无");
        } else if (last == 24) {
            tv_time.setText("安静时段：全天");
        } else {
            if (start + last == 24) {
                tv_time.setText(String.format(getResources().getString(R.string.dialogtime), "每日" + start, "次日00"));

            } else if (start + last > 24) {
                int t1 = start + last - 24;
                str = "次日" + t1;
                tv_time.setText(String.format(getResources().getString(R.string.dialogtime), "每日" + start, str));
            } else {
                str = start + last + "";
                tv_time.setText(String.format(getResources().getString(R.string.dialogtime), "每日" + start, str));
            }
        }

        return str;
    }

    /**
     * UISwitch  选中事件
     *
     * @param compoundButton
     * @param isChecked
     */
    @CheckedChange({R.id.switch_open_send})
    void CheckChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {

            case R.id.switch_open_send:
                SharedPreferencesUtils.getInstance().editIsopensent(isChecked);
                if (SharedPreferencesUtils.getInstance().getIsopensent()) {
                    //打开推送
                    MqttService.actionStart(SettingActivity.this);
                    rl_setting_quiet_time.setVisibility(View.VISIBLE);
                } else {
                    //关闭推送
                    MqttService.actionStop(SettingActivity.this);
                    rl_setting_quiet_time.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
         * 缓存大小
		 */
        setting_tvCache.setText(CommonUtils.FormetFileSize(getCacheSize()));
        rlRemoveCache.setClickable(true);
        SetTime(tv_quiteTime, SharedPreferencesUtils.getInstance().getPushStarttime(), SharedPreferencesUtils.getInstance().getLastTime());

    }

    private void showBasicNoTitle() {
        new MaterialDialog.Builder(this)
                .content(R.string.dialog_title_clearcatch).contentGravity(GravityEnum.CENTER)

                .positiveText(R.string.yes).callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                new ClearTask().execute();
            }


            @Override
            public void onNegative(MaterialDialog dialog) {

            }
        }).negativeText(R.string.no)
                .show();

    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!StringUtils.isBlank(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                        if (i == files.length - 1) {
                            /*
                             * 清理完成
							 */
                           /* rlRemoveCache.setClickable(true);
                            tvCache.setText(CommonUtils
                                    .FormetFileSize(getCacheSize()));
                          CustomToast.showToast("清理完成",this);*/
                        }
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */

    private long getCacheSize() {
        long cacheSize = 0;
        try {
            cacheSize = CommonUtils.getAutoFileOrFilesRealSize(BitmapCache
                    .getInstance().getDirectory())
                    + CommonUtils.getAutoFileOrFilesRealSize(getCacheDir()
                    .getAbsolutePath());
        } catch (Exception e) {
        }

        return cacheSize / 2;
    }

    private class ClearTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            md.setMessage(getString(R.string.dialog_clearing));
            md.show();
        }

        protected Void doInBackground(Void... params) {

            // 删除所有图片文件，不删除文件夹
            rlRemoveCache.setClickable(false);
            //清除数据缓存
            DiskDataHelper.getInstance().clearDataCache();
            deleteFolderFile(getCacheDir().getAbsolutePath(), false);
            deleteFolderFile(BitmapCache.getInstance().getDirectory(), false);
            return null;
        }

        protected void onPostExecute(Void result) {
            if (md != null) {
                md.dismiss();

            }
            long cacheSize = CommonUtils
                    .getAutoFileOrFilesRealSize(BitmapCache.getInstance()
                            .getDirectory())
                    + CommonUtils.getAutoFileOrFilesRealSize(getCacheDir()
                    .getAbsolutePath());
            tvCache.setText(CommonUtils.FormetFileSize(cacheSize));
            rlRemoveCache.setClickable(true);
        }

    }
}
