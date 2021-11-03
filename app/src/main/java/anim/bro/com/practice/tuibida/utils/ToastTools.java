package anim.bro.com.practice.tuibida.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 *
 * @author rand
 * @version 调整为JuMeiToast
 */
public class ToastTools {



    public static JuMeiToast makeText(Context context, String msg, int duration) {
//
//        LayoutInflater inflater = LayoutInflater.from(context);
//        LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast, null);
//        TextView toastView = (TextView) toastLayout.findViewById(R.id.toast_text);
//        if (null != toastView) {
//            if (!TextUtils.isEmpty(msg)) {
//                toastView.setText(msg);
//            } else {
//                toastView.setText("操作繁忙，请稍等");
//            }
//        }

        JuMeiToast toast = JuMeiToast.makeText(context, msg, duration);
        return toast;
    }

    public static JuMeiToast makeText(Context context, int msg, int duration) {
        String msgStr = context.getResources().getString(msg);
        return makeText(context, msgStr, duration);
    }

    public static void showShort(Context context, String message) {
        makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String message) {
        makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
