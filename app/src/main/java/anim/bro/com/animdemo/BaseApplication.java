package anim.bro.com.animdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import anim.bro.com.animdemo.service.SyncTimeService;
import anim.bro.com.animdemo.util.ServiceUtil;
import anim.bro.com.animdemo.util.SingleContainer;


/**
 * Created by zhangshan on 2018/9/18 17:36.
 */
public class BaseApplication extends Application {

    protected int visibleActiviesCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        SingleContainer.init(this);
        initRegisterListener();
    }

    private void initRegisterListener() {

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        registerActivityLifecycleCallback(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (visibleActiviesCount == 0) {
                    Log.d("bro", "------切换到前台------");
                    //异常上报
                    if (!ServiceUtil.isServiceRunning(activity , SyncTimeService.SYNC_TIME_SERVICE)){
                        SyncTimeService.startSyncTimeService(activity);
                    }else {
                        Log.d("bro" , "SyncTimeService 正在运行中");
                    }

                }
                visibleActiviesCount++;
                //pushPageTrace(activity.getComponentName().getClassName() + " onStarted()");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d("bro", "当前页面：" + activity.getComponentName().getClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                visibleActiviesCount--;
                if (visibleActiviesCount == 0) {
                    Log.d("bro", "------切换到后台------");
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        this.registerActivityLifecycleCallbacks(callbacks);
    }
}
