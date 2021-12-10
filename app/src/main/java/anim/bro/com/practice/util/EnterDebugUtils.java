package anim.bro.com.practice.util;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

public class EnterDebugUtils {
    private static EnterDebugUtils instance;
    private final WeakReference<Context> contextWeakReference;
    /**
     * 打开调试页面
     */
    static String ACTION_SHOW_DEBUG = "local_force_debug://";

    private EnterDebugUtils(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    public static synchronized EnterDebugUtils getInstance(Context context) {
        if (instance == null) {
            instance = new EnterDebugUtils(context);
        }
        return instance;
    }

    public String getQrCodeEnterDebug() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH-mm");
        String nowTimeString = TimeUtils.getNowString(dateFormat);

        String[] timeSplit = nowTimeString.split("-");

        String preSplit = timeSplit[0];
        String minSplit = timeSplit[1];

//        Log.i("bro", "preSplit: " + preSplit + "    --minSplit:" + minSplit);

        int minIntSplit = Integer.parseInt(minSplit);
//        Log.i("bro", "minIntSplit: " + minIntSplit);

        String addition = "";
        if (minIntSplit >= 0 && minIntSplit <= 9) {
            addition = "A";
        } else if (minIntSplit >= 10 && minIntSplit <= 19) {
            addition = "B";
        } else if (minIntSplit >= 20 && minIntSplit <= 29) {
            addition = "C";
        } else if (minIntSplit >= 30 && minIntSplit <= 39) {
            addition = "D";
        } else if (minIntSplit >= 40 && minIntSplit <= 49) {
            addition = "E";
        } else if (minIntSplit >= 50 && minIntSplit <= 60) {
            addition = "F";
        }

        String verifyCode = preSplit + addition;
//        Log.i("bro", "verifyCode: " + verifyCode);

        String encryptMD5ToString = EncryptUtils.encryptMD5ToString(verifyCode);
//        Log.i("bro", "encryptMD5ToString: " + encryptMD5ToString);

        String lowerCase = encryptMD5ToString.toLowerCase();
//        Log.i("bro", "lowerCase: " + lowerCase);


        return ACTION_SHOW_DEBUG + "code=" + lowerCase;
    }


    /**
     * 设置 App 为 debug 模式
     */
    public void setAppDebug() {
        //1. 全局变量为 true,只管本次打开 app
//        AppConfigUtils.IS_DEBUG = "true";
        // 2. 改变 config.properties 文件下的 IS_DEBUG = true,持久改变
//        if (isContextSafe()) {
//            DataHelper.setReleaseEnvirOpenDebug(contextWeakReference.get(), true);
//
//        }
//        BaseToast.toast("debug 模式已开启,请重新打开App");

    }

    /**
     * 校验二维码是否符合条件
     *
     * @return
     */
    public boolean isAccessIn(String code) {
        if (TextUtils.equals(getQrCodeEnterDebug(), code)) {
            return true;
        }
        return false;
    }

    private boolean isContextSafe() {
        return contextWeakReference != null && contextWeakReference.get() != null;
    }


}