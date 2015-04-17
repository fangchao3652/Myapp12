package com.example.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

/**
 * Created by fc on 2015/1/14.
 * <p/>
 * Toast提示工具类
 */
public class CustomToast {

    private static Toast mToast;

    private static ToastStyle styleType;

    /**
     * 对话框类型
     */
    public enum ToastStyle {
        TOAST_NORMAL, TOAST_ERROR, TOAST_IMPORTANT
    }

    /**
     * Toast提示
     *
     * @param msg     消息内容
     * @param context
     */
    public static void showToast(CharSequence msg, Context context) {
        showToast(msg, context, Toast.LENGTH_LONG, ToastStyle.TOAST_NORMAL);
    }

    /**
     * Toast提示
     *
     * @param msgId     消息内容
     * @param context
     */
    public static void showToast(int msgId, Context context) {
        showToast(msgId, context, Toast.LENGTH_LONG, ToastStyle.TOAST_NORMAL);
    }

    /**
     * Toast提示
     *
     * @param msg     消息内容
     * @param context
     * @param type    显示类型
     */
    public static void showToast(CharSequence msg, Context context, ToastStyle type) {
        showToast(msg, context, Toast.LENGTH_LONG, type);
    }

    /**
     * Toast提示
     *
     * @param msgId     消息内容
     * @param context
     * @param type    显示类型
     */
    public static void showToast(int msgId, Context context, ToastStyle type) {
        showToast(msgId, context, Toast.LENGTH_LONG, type);
    }

    /**
     * Toast提示
     *
     * @param msg      消息内容
     * @param context 上下文对象
     * @param showTime 显示时长
     */
    public static void showToast(CharSequence msg, Context context, int showTime) {
        showToast(msg, context, showTime, ToastStyle.TOAST_NORMAL);
    }

    /**
     * Toast提示
     *
     * @param msgId      消息内容
     * @param context 上下文对象
     * @param showTime 显示时长
     */
    public static void showToast(int msgId, Context context, int showTime) {
        showToast(msgId, context, showTime, ToastStyle.TOAST_NORMAL);
    }

    /**
     * Toast提示
     *
     * @param msg      消息内容
     * @param context
     * @param showTime 显示时长
     */
    public static void showToast(CharSequence msg, Context context, int showTime, ToastStyle type) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, showTime);
            setStyleType(type);
            mToast.setView(getView(context, msg, type));
        } else {
            mToast.setDuration(showTime);
        }
        if (type != getStyleType()) {
            mToast.setView(getView(context, msg, type));
            setStyleType(type);
        } else {
            setText(mToast.getView(), msg);
        }
//        mToast.setGravity(Gravity.CENTER, 0, 100);
        mToast.show();
    }

    public static void showToast(int msgId, Context context, int showTime, ToastStyle type){
        if (mToast == null) {
            mToast = Toast.makeText(context, msgId, showTime);
            setStyleType(type);
            mToast.setView(getView(context, msgId, type));
        } else {
            mToast.setDuration(showTime);
        }
        if (type != getStyleType()) {
            mToast.setView(getView(context, msgId, type));
            setStyleType(type);
        } else {
            setText(mToast.getView(), msgId);
        }
//        mToast.setGravity(Gravity.CENTER, 0, 100);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 取消Toast显示
     */
    public static void clearToast(){
        if(mToast != null){
            mToast.cancel();
            mToast = null;
        }
    }

    /**
     * 获取自定义的界面
     *
     * @param context
     * @param msg
     * @return
     */
    private static View getView(Context context, CharSequence msg, ToastStyle type) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,
                null);
        ImageView img = (ImageView) view.findViewById(R.id.toast_img);

        switch (type) {
            case TOAST_NORMAL://默认提示
                img.setVisibility(View.GONE);
                break;
            case TOAST_ERROR://错误提示
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.logo);
                break;
            case TOAST_IMPORTANT://重要提示
                //重要提示相关View操作
                break;
        }
        TextView title = (TextView) view.findViewById(R.id.toast_title);
        title.setText(msg);
        return view;
    }

    /**
     * 获取自定义的界面
     *
     * @param context
     * @param msgId
     * @return
     */
    private static View getView(Context context, int msgId, ToastStyle type) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,
                null);
        ImageView img = (ImageView) view.findViewById(R.id.toast_img);
        switch (type) {
            case TOAST_NORMAL://默认提示
                img.setVisibility(View.GONE);
                break;
            case TOAST_ERROR://错误提示
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.logo);
                break;
            case TOAST_IMPORTANT://重要提示
                //重要提示相关View操作
                break;
        }
        TextView title = (TextView) view.findViewById(R.id.toast_title);
        title.setText(msgId);
        return view;
    }

    /**
     * 设置Toast消息
     *
     * @param view
     * @param msg
     */
    private static void setText(View view, CharSequence msg) {
        TextView title = (TextView) view.findViewById(R.id.toast_title);
        title.setText(msg);
    }

    /**
     * 设置Toast消息
     *
     * @param view
     * @param msgId
     */
    private static void setText(View view, int msgId) {
        TextView title = (TextView) view.findViewById(R.id.toast_title);
        title.setText(msgId);
    }

    public static ToastStyle getStyleType() {
        return styleType;
    }

    public static void setStyleType(ToastStyle styleType) {
        CustomToast.styleType = styleType;
    }

}
