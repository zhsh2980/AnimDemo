package anim.bro.com.practice.util;

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;

public class EnterDebugUtils {
    private static EnterDebugUtils instance;

    private EnterDebugUtils() {
    }

    public static synchronized EnterDebugUtils getInstance() {
        if (instance == null) {
            instance = new EnterDebugUtils();
        }
        return instance;
    }

    public String getQrCodeEnterDebug() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH-mm");
        String nowTimeString = TimeUtils.getNowString(dateFormat);
        String[] timeSplit = nowTimeString.split("-");

        String preSplit = timeSplit[0];
        String minSplit = timeSplit[1];

        Log.i("bro", "preSplit: " + preSplit + "    --minSplit:" + minSplit);

        int minIntSplit = Integer.parseInt(minSplit);
        Log.i("bro", "minIntSplit: " + minIntSplit);

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
        Log.i("bro", "verifyCode: " + verifyCode);

        String encryptMD5ToString = EncryptUtils.encryptMD5ToString(verifyCode);
        Log.i("bro", "encryptMD5ToString: " + encryptMD5ToString);

        String lowerCase = encryptMD5ToString.toLowerCase();
        Log.i("bro", "lowerCase: " + lowerCase);


        return lowerCase;
    }


}