package com.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.common.SharedPreferencesUtils;
import com.example.fc.activity.MqttService;


/**
 * 开机自启接收器
 * Created by fc on 2015/3/31.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    public static final String DEBUG_TAG = "MqttService"; // Log标记

    @Override
    public void onReceive(Context context, Intent intent) {

        if (SharedPreferencesUtils.getInstance().getIsopensent()) {
            //打开推送
            Log.i(DEBUG_TAG, "开机启动推送服务");
            MqttService.actionStart(context);
        }
    }

}