package com.example.fc.activity;


import com.example.fc.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * 消息通知展示
 */

public class MqttNotifier {
    public static final String DEBUG_TAG = "MqttService"; // Log标记
    /**
     * 消息id标示
     */
    private static final int SUPUY_NOTIFER_ID = 674953337;


    public static void notify(Context context, String json) {

        if (!com.example.fc.activity.StringUtils.isBlank(json)) {
            //json解析
            com.example.fc.activity.NotifierBean mBean = null;
            Gson gson = new Gson();
            try {
                mBean = gson.fromJson(json, com.example.fc.activity.NotifierBean.class);
            } catch (JsonSyntaxException ex) {
                Log.i(DEBUG_TAG, "收到推送消息Json解析错误" + ex.toString());
                return;
            }

            if (mBean != null) {
          /*  	Notification n = new Notification();
            	NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
        		n.flags |= Notification.FLAG_SHOW_LIGHTS;
              	n.flags |= Notification.FLAG_AUTO_CANCEL;

                n.defaults = Notification.DEFAULT_ALL;
              	
        		n.icon = com.tokudu.demo.R.drawable.logo;
        		n.when = System.currentTimeMillis();

        		// Simply open the parent activity
        		
        		// Change the name of the notification here
        		n.setLatestEventInfo(context, mBean.getTitle(), mBean.getContent(), getDefalutIntent(context,mBean));

        		notificationManager.notify(SUPUY_NOTIFER_ID, n);*/
            	
                NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                //通知设置
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                
                Log.e("fffff", mBean.getContent());
                mBuilder.setContentTitle(mBean.getTitle())//设置通知栏标题
                        .setContentText(mBean.getContent()) //设置通知栏显示内容
                        .setContentIntent(getDefalutIntent(context, mBean)) //设置通知栏点击意图
                        .setTicker(mBean.getTitle()) //通知首次出现在通知栏，带上升动画效果的
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                       //设置该通知优先级
                        .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                        .setSmallIcon(R.drawable.icon_collect_press)//设置通知小ICON,为了应对
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_compute_address));


                Notification notification = mBuilder.getNotification();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                //展示推送消息
                notificationManager.notify(SUPUY_NOTIFER_ID, notification);
            }
        }
    }

    private static PendingIntent getDefalutIntent(Context context, com.example.fc.activity.NotifierBean mBean) {
        // 类型0商品列表1特卖列表2商品详情
        Intent mIntent = null;Log.e("type   ", ""+mBean.getType());
        switch (mBean.getType()) {
        
            case 0:
                //商品列表
              /*  String title = "";
                if (!StringUtils.isBlank(mBean.getSearchText())) {
                    title = mBean.getSearchText();
                } else if (!StringUtils.isBlank(mBean.getCategoryId())) {
                    //根据分类id获取分类名称
                 //   title = SqliteHelper.getCategoryByCategoryId(mBean.getCategoryId()).getCategoryName();
                } else if (!StringUtils.isBlank(mBean.getBrandId())) {
                    //根据品牌id获取品牌名称
                 //   title = SqliteHelper.getBrandByBrandId(mBean.getCategoryId()).getBrandName();
                }*/

             //   mIntent = GoodsListActivity_.intent(context).msearchKey(mBean.getSearchText())
                      //  .mcategoryId(mBean.getCategoryId()).mbrandId(mBean.getBrandId()).title(title).get();
                break;
            case 1:
                //组装特卖信息bean
               // BoutiqueSaleBean boutiqueSaleBean = new BoutiqueSaleBean();
            //    boutiqueSaleBean.setFlashSaleName(mBean.getTitle());
             //   boutiqueSaleBean.setFlashSaleId(mBean.getFlashId());
             mIntent = new Intent();
             
            mIntent.setClass(context, com.example.fc.activity.firstActivity.class);



       
          
                break;
            case 2:
                //商品详情
            	  mIntent = new Intent(context, com.example.fc.activity.SecondActivity.class);
                  break;
             //   mIntent = GoodsDetailActivity_.intent(context).goodsSN(mBean.getGoodsId()).get();
            default:
              //  mIntent = HomeActivity_.intent(context).get();
                break;

        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 5, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

}
