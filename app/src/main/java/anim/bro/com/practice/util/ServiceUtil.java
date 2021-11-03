package anim.bro.com.practice.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zhangshan on 2021/8/6 17:55.
 */
public class ServiceUtil {

    public static boolean isServiceRunning(Context context, String serviceName) {
        boolean isServiceRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                isServiceRunning = true;
                break;
            }

        }
        Log.d("bro" , String.format("service [%s] running status:[%s]", serviceName, isServiceRunning));
        return isServiceRunning;
    }

    public static void startService(Context context, String action) {
        Log.d("bro" , String.format("start service [%s]", action));
        Intent mIntent = new Intent();
        mIntent.setAction(action);
        mIntent.setPackage(context.getPackageName());
        context.startService(mIntent);
    }

}
