package anim.bro.com.practice.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by zhangshan on 2021/7/27 16:30.
 */
public class SyncTimeService extends Service {

    public static final String SYNC_TIME_SERVICE = "anim.bro.com.animdemo.service.SyncTimeService";

    private int timeInterval = 1000 * 2;// 60秒一次

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doExceptionRepott();
            handler.postDelayed(this, timeInterval);
        }
    };


    public static void startSyncTimeService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), SyncTimeService.class);
        try {
            context.startService(intent);
        } catch (Throwable e) {
            Log.e("bro", "startSyncTimeService error" + e.getMessage());
        }
    }


    /**
     * 执行异常上报逻辑
     */
    private void doExceptionRepott() {
//        Log.d("bro", "异常上报==" + System.currentTimeMillis());
    }


    public SyncTimeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("bro", "定时服务启动==onStartCommand");
        startForeground(0, new Notification());
        handler.postDelayed(runnable, timeInterval);//每两秒执行一次runnable.
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("bro", "定时服务关闭==onDestroy");
        stopForeground(true);
        handler.removeCallbacks(runnable);
        handler = null;
    }


}
