package com.example.fc.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

import com.example.common.SharedPreferencesUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;


/**
 * 推送服务
 */
public class MqttService extends Service implements MqttCallback {

    public static final String DEBUG_TAG = "MqttService"; // Log标记
    private static final String MQTT_THREAD_NAME = "MqttService[" + DEBUG_TAG + "]"; // Handler Thread ID
    public static final int MQTT_QOS_0 = 0; //消息投放级别 QOS Level 0 (最多一次，有可能重复或丢失。 )
    public static int[] qos = {MQTT_QOS_0};//订阅级别
    private static final int MQTT_KEEP_ALIVE_QOS = MQTT_QOS_0; //心跳包的发送级别默认最低
    public static final int MQTT_QOS_1 = 1; //消息投放级别 QOS Level 1 (至少一次，有可能重复。 )
    public static final int MQTT_QOS_2 = 2; //消息投放级别 QOS Level 2 (只有一次，确保消息只到达一次（用于比较严格的计费系统）。)
    private static final String MQTT_BROKER_TEST = "192.168.0.183"; //测试地址@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2
    private static final String MQTT_BROKER = MQTT_BROKER_TEST;
    private static final String MQTT_BROKER_ONLINE = "mqtt.supumall.com"; //正式地址
    private static final int MQTT_PORT = 1883;                // 服务器推送端口
    private static final int MQTT_KEEP_ALIVE = 4 * 60 * 1000; //心跳包时间，毫秒
    private static final String MQTT_KEEP_ALIVE_TOPIC_FORAMT = "/users/%s/keepalive"; // Topic format for KeepAlives
    private static final byte[] MQTT_KEEP_ALIVE_MESSAGE = {0}; // 心跳包发送内容
    private static final boolean MQTT_CLEAN_SESSION = true; // Start a clean session?
    private static final String MQTT_URL_FORMAT = "tcp://%s:%d"; // 推送url格式组装
    private static final String DEVICE_ID_FORMAT = "an_%s"; // 设备id的前缀
    public static String[] topicFilters = {"Fangchao"};//订阅的主题               @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2
    public static String MQTT_CLIENT_ID = "Fangchao";
    private static final String ACTION_START = MQTT_CLIENT_ID + ".START"; // Action to start 启动
    private static final String ACTION_STOP = MQTT_CLIENT_ID + ".STOP"; // Action to stop 停止
    private static final String ACTION_KEEPALIVE = MQTT_CLIENT_ID + ".KEEPALIVE"; // Action to keep alive used by alarm manager保持心跳闹钟使用
    private static final String ACTION_RECONNECT = MQTT_CLIENT_ID + ".RECONNECT"; // Action to reconnect 重新连接
    // Note:设备id限制长度为23个 字符
    // An NPE if you go over that limit
    private boolean mStarted = false; //推送client是否启动
    private String mDeviceId;          // Device ID, Secure.ANDROID_ID
    private Handler mConnHandler;      // Seperate Handler thread for networking

    private MqttDefaultFilePersistence mDataStore; // Defaults to FileStore
//    private MqttConnectOptions mOpts;            //连接参数

    private MqttTopic mKeepAliveTopic;            // Instance Variable for Keepalive topic

    private MqttClient mClient;                    // Mqtt Client
    /**
     * 网络状态发生变化接收器
     */
    private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkAvailable()) {
                Log.e(DEBUG_TAG, "网络连接发生了变化--网络连接");
                reconnectIfNecessary();
            } else {
                Log.e(DEBUG_TAG, "网络连接发生了变化--网络断开");
                stopKeepAlives();
                mClient = null;
            }
        }
    };
    private AlarmManager mAlarmManager;            //闹钟
    private ConnectivityManager mConnectivityManager; //网络改变接收器

    /**
     * 启动推送服务
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStart(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_START);
        ctx.startService(i);
    }

    /**
     * 停止推送服务
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStop(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_STOP);
        ctx.startService(i);
    }

    /**
     * 发送心跳包
     *
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionKeepalive(Context ctx) {
        Intent i = new Intent(ctx, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        ctx.startService(i);
    }

    public static String[] getTopicFilters() {
        return topicFilters;
    }

    public static int[] getQos() {
        return qos;
    }

    public static void setQos(int[] qos) {
        MqttService.qos = qos;
    }

    public static String[] AddTopic(String topic) {
        Set<String> topiclist = new TreeSet<>();

        for (int i = 0; i < getTopicFilters().length; i++) {
            topiclist.add(new String(getTopicFilters()[i]));

        }
        topiclist.add(new String(topic));


        int m = 0;

        SharedPreferencesUtils.getInstance().putTopics(topiclist);//添加到本地
        topicFilters = topiclist.toArray(topicFilters);
        return topicFilters;
    }

    /**
     * Initalizes the DeviceId and most instance variables
     * Including the Connection Handler, Datastore, Alarm Manager
     * and ConnectivityManager.
     * 初始化设备id和请求参数包含连接处理、数据存储、闹钟警报、网络接收器
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("fc", "oncreate");
        //初始化设备id，长度不能超过23
        mDeviceId = String.format(DEVICE_ID_FORMAT,
                Secure.getString(getContentResolver(), Secure.ANDROID_ID));

        HandlerThread thread = new HandlerThread(MQTT_THREAD_NAME);
        thread.start();

        mConnHandler = new Handler(thread.getLooper());

        mDataStore = new MqttDefaultFilePersistence(getCacheDir().getAbsolutePath());


//        mOpts = new MqttConnectOptions();
//        mOpts.setCleanSession(MQTT_CLEAN_SESSION);
        // Do not set keep alive interval on mOpts we keep track of it with alarm's

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean isRestricted() {
        return super.isRestricted();
    }

    /**
     * Service onStartCommand
     * Handles the action passed via the Intent
     * 通过意图处理服务
     *
     * @return START_REDELIVER_INTENT
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        String action = intent.getAction();
        Log.e(DEBUG_TAG, "推送服务接收到一个请求 " + action);

        if (action == null) {
            Log.e(DEBUG_TAG, "推送服务接收到的请求为null！推送服务不执行任何操作");
        } else {
            if (action.equals(ACTION_START)) {
                Log.e(DEBUG_TAG, "接收到《启动》推送服务命令");
                start();
            } else if (action.equals(ACTION_STOP)) {
                Log.e(DEBUG_TAG, "接收到《停止》推送服务命令");
                stop();
            } else if (action.equals(ACTION_KEEPALIVE)) {
                Log.e(DEBUG_TAG, "接收到《发送心跳包》推送服务命令");
                keepAlive();
            } else if (action.equals(ACTION_RECONNECT)) {
                Log.e(DEBUG_TAG, "接收到《重启》推送服务命令");
                if (isNetworkAvailable()) {
                    reconnectIfNecessary();
                }
            }
        }

        return START_REDELIVER_INTENT;
    }

    /**
     * 尝试启动推送服务器，并注册网络改变接收器
     */
    private synchronized void start() {
        if (mStarted) {
            Log.e(DEBUG_TAG, "尝试启动推送服务，但推送服务已经启动");
            return;
        }

        if (hasScheduledKeepAlives()) {
            stopKeepAlives();
        }

        connect();

        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * 停止推送服务
     */
    private synchronized void stop() {
        if (!mStarted) {
            Log.e(DEBUG_TAG, "试图停止推送服务器但是推送服务并没有运行");
            return;
        }

        if (mClient != null) {
            mConnHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        mClient.disconnect();
                    } catch (MqttException ex) {
                        ex.printStackTrace();
                    }
                    mClient = null;
                    mStarted = false;

                    stopKeepAlives();
                }
            });
        }

        unregisterReceiver(mConnectivityReceiver);
    }

    /**
     * Connects to the broker with the appropriate datastore
     * 连接到推送服务器与适当的数据存储
     */
    private synchronized void connect() {
        String url = String.format(Locale.US, MQTT_URL_FORMAT, MQTT_BROKER, MQTT_PORT);
        Log.e(DEBUG_TAG, "连接推送服务器 设备id：" + mDeviceId + " with URL: " + url);
        try {
            mClient = new MqttClient(url, mDeviceId, mDataStore);

        } catch (MqttException e) {
            e.printStackTrace();
        }

        mConnHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient.connect();
                    //fc
                    topicFilters = castSet2Array(SharedPreferencesUtils.getInstance().getTopics());
                    mClient.subscribe(topicFilters, new int[topicFilters.length]);

                    mClient.setCallback(MqttService.this);

                    mStarted = true; // Service is now connected

                    Log.e(DEBUG_TAG, "成功连接推送服务器并启动心跳包闹钟");

                    startKeepAlives();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String[] castSet2Array(Set<String> topicset) {
        String topicArray[] = new String[topicset.size()];
        int n = 0;
        for (String ss : topicset) {
            topicArray[n] = ss;
            n++;
        }
        return topicArray;
    }

    /**
     * 启动心跳包闹钟
     */
    private void startKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + MQTT_KEEP_ALIVE,
                MQTT_KEEP_ALIVE, pi);
    }

    /**
     * 取消已经存在的闹钟
     */
    private void stopKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
        mAlarmManager.cancel(pi);
    }

    /**
     * 发送心跳数据到服务器
     */
    private synchronized void keepAlive() {
        if (isConnected()) {
            try {
                sendKeepAlive();
                return;
            } catch (MqttConnectivityException ex) {
                ex.printStackTrace();
                reconnectIfNecessary();
            } catch (MqttPersistenceException ex) {
                ex.printStackTrace();
                stop();
            } catch (MqttException ex) {
                ex.printStackTrace();
                stop();
            }
        }
    }

    /**
     * 重新连接如果他是必须的
     */
    private synchronized void reconnectIfNecessary() {

        if (mStarted && mClient == null) {
            connect();
        } else {
            Log.e(DEBUG_TAG, "重新连接没有启动，mStarted:" + String.valueOf(mStarted) + " mClient:" + mClient);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    /**
     * 通过ConnectivityManager查询网络连接状态
     *
     * @return 如果网络状态正常则返回true反之flase
     */
    private boolean isNetworkAvailable() {
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

        return (info == null) ? false : info.isConnected() && info.isAvailable();

    }

    /**
     * 判断推送服务是否连接
     *
     * @return 如果是连接的则返回true反之false
     */
    private boolean isConnected() {
        if (mStarted && mClient != null && !mClient.isConnected()) {
            Log.e(DEBUG_TAG, "判断推送服务已经断开");
        }

        if (mClient != null) {
            return (mStarted && mClient.isConnected()) ? true : false;
        }

        return false;
    }

    /**
     * 发送保持连接的指定的主题
     *
     * @return MqttDeliveryToken specified token you can choose to wait for completion
     */
    private synchronized MqttDeliveryToken sendKeepAlive()
            throws MqttConnectivityException, MqttPersistenceException, MqttException {
        if (!isConnected())
            throw new MqttConnectivityException();

        if (mKeepAliveTopic == null) {
            mKeepAliveTopic = mClient.getTopic(
                    String.format(Locale.US, MQTT_KEEP_ALIVE_TOPIC_FORAMT, mDeviceId));
        }

        Log.e(DEBUG_TAG, "向服务器发送心跳包url： " + MQTT_BROKER);

        MqttMessage message = new MqttMessage(MQTT_KEEP_ALIVE_MESSAGE);
        message.setQos(MQTT_KEEP_ALIVE_QOS);

        return mKeepAliveTopic.publish(message);
    }

    /**
     * 查询是否已经有一个心跳包的闹钟
     *
     * @return 如果已经有一个心跳包的闹钟则返回true反之false
     */
    private synchronized boolean hasScheduledKeepAlives() {
        Intent i = new Intent();
        i.setClass(this, MqttService.class);
        i.setAction(ACTION_KEEPALIVE);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_NO_CREATE);

        return (pi != null) ? true : false;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * 连接丢失回调
     */
    @Override
    public void connectionLost(Throwable arg0) {
        Log.e(DEBUG_TAG, "推送回调函数连接丢失connectionLost方法执行");
        stopKeepAlives();

        mClient = null;

        if (isNetworkAvailable()) {
            reconnectIfNecessary();
        }
    }

    /**
     * 收到推送信息
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        Log.e(DEBUG_TAG, "收到推送信息如下\n  Topic:\t" + topic +
                "  Message:\t" + new String(mqttMessage.getPayload()) +
                "  QoS:\t" + mqttMessage.getQos());

        com.example.fc.activity.MqttNotifier.notify(this, new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        Log.e(DEBUG_TAG, "推送回调函数deliveryComplete方法执行");
    }

    /**
     * MqttConnectivityException Exception class
     */
    private class MqttConnectivityException extends Exception {
        private static final long serialVersionUID = -7385866796799469420L;
    }
    //{"title": "这是标题","content":"这是内容","type":1}
}
