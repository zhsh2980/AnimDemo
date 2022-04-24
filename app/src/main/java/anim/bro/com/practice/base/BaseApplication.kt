package anim.bro.com.practice.base

import android.os.Bundle
import anim.bro.com.practice.util.ServiceUtil
import anim.bro.com.practice.service.SyncTimeService
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.os.Build
import android.util.Log
import anim.bro.com.practice.util.SingleContainer
import com.blankj.utilcode.util.Utils
import java.text.SimpleDateFormat

/**
 * Created by zhangshan on 2018/9/18 17:36.
 */
class BaseApplication : Application() {
    protected var visibleActiviesCount = 0
    override fun onCreate() {
        super.onCreate()
//        initDoKit()
        Utils.init(this)
        SingleContainer.init(this)
        initRegisterListener()
    }

//    private fun initDoKit() {
//        DoKit.Builder(this)
////            .productId("需要使用平台功能的话，需要到dokit.cn平台申请id")
//            .build()
//    }

    private fun initRegisterListener() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // HH:mm:ss
        registerActivityLifecycleCallback(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {
                if (visibleActiviesCount == 0) {
                    Log.d("bro", "------切换到前台------")
                    //异常上报
                    if (!ServiceUtil.isServiceRunning(
                            activity,
                            SyncTimeService.SYNC_TIME_SERVICE
                        )
                    ) {
                        SyncTimeService.startSyncTimeService(activity)
                    } else {
                        Log.d("bro", "SyncTimeService 正在运行中")
                    }
                }
                visibleActiviesCount++
                //pushPageTrace(activity.getComponentName().getClassName() + " onStarted()");
            }

            override fun onActivityResumed(activity: Activity) {
                Log.d("bro", "当前页面：" + activity.componentName.className)
            }

            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {
                visibleActiviesCount--
                if (visibleActiviesCount == 0) {
                    Log.d("bro", "------切换到后台------")
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: ActivityLifecycleCallbacks?) {
        registerActivityLifecycleCallbacks(callbacks)
    }
}