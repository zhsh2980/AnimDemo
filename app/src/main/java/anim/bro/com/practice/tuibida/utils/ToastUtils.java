package anim.bro.com.practice.tuibida.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;


/**
 * Created by zhangshan on 2018/10/31 18:05.
 */
public final class ToastUtils {
    private static Toast sToast;
    private static int gravity = 81;
    private static int xOffset = 0;
    private static int yOffset;
    @SuppressLint({"StaticFieldLeak"})
    private static View customView;
    private static Handler sHandler;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setGravity(int gravity, int xOffset, int yOffset) {
        gravity = gravity;
        xOffset = xOffset;
        yOffset = yOffset;
    }

    public static void setView(@LayoutRes int layoutId) {
//        LayoutInflater inflater = (LayoutInflater) MyApplication.mContext.getSystemService("layout_inflater");
//        customView = inflater.inflate(layoutId, (ViewGroup)null);
    }

    public static void setView(View view) {
        customView = view;
    }

    public static View getView() {
        if (customView != null) {
            return customView;
        } else {
            return sToast != null ? sToast.getView() : null;
        }
    }

    public static void showShortSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(text, 0);
            }
        });
    }

    public static void showShortSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(resId, 0);
            }
        });
    }

    public static void showShortSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(resId, 0, args);
            }
        });
    }

    public static void showShortSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(format, 0, args);
            }
        });
    }

    public static void showLongSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(text, 1);
            }
        });
    }

    public static void showLongSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(resId, 1);
            }
        });
    }

    public static void showLongSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(resId, 1, args);
            }
        });
    }

    public static void showLongSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.show(format, 1, args);
            }
        });
    }

    public static void showShort(CharSequence text) {
        show(text, 0);
    }

    public static void showShort(@StringRes int resId) {
        show(resId, 0);
    }

    public static void showShort(@StringRes int resId, Object... args) {
        show(resId, 0, args);
    }

    public static void showShort(String format, Object... args) {
        show(format, 0, args);
    }

    public static void showLong(CharSequence text) {
        show(text, 1);
    }

    public static void showLong(@StringRes int resId) {
        show(resId, 1);
    }

    public static void showLong(@StringRes int resId, Object... args) {
        show(resId, 1, args);
    }

    public static void showLong(String format, Object... args) {
        show(format, 1, args);
    }

    private static void show(@StringRes int resId, int duration) {
//        show(MyApplication.mContext.getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes int resId, int duration, Object... args) {
//        show(String.format(MyApplication.mContext.getResources().getString(resId), args), duration);
    }

    private static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(CharSequence text, int duration) {
        cancel();
//        if (customView != null) {
//            sToast = new Toast(MyApplication.mContext);
//            sToast.setView(customView);
//            sToast.setDuration(duration);
//        } else {
//            sToast = Toast.makeText(MyApplication.mContext, text, duration);
//        }

        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();
    }

    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }

    }

    static {
//        yOffset = (int)((double)(64.0F * MyApplication.mContext.getResources().getDisplayMetrics().density) + 0.5D);
//        sHandler = new Handler(Looper.getMainLooper());
    }
}