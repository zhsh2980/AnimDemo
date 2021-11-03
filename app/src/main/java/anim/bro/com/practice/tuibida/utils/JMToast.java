package anim.bro.com.practice.tuibida.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;


/**
 * Created by zhangshan on 2018/10/31 18:29.
 */
public class JMToast {

    private static JMToast jmToast;
    private Toast toast;

    @SuppressLint("ShowToast")
    public JMToast() {
        Context context = SingleContainer.getApplicationContext();
        this.toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    private static JMToast getInstance() {
        if (jmToast == null) {
            synchronized (JMToast.class) {
                if (jmToast == null) {
                    jmToast = new JMToast();
                }
            }
        }
        return jmToast;
    }

    public static void show(final String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        SingleContainer.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                JMToast instance = getInstance();
                instance.toast.setText(msg);
                instance.toast.show();
            }
        });
    }

    public static void show(@StringRes int resId) {
    }
}
