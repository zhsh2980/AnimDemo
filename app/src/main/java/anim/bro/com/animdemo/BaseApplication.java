package anim.bro.com.animdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import anim.bro.com.animdemo.util.SingleContainer;

/**
 * Created by zhangshan on 2018/9/18 17:36.
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        SingleContainer.init(this);
    }
}
