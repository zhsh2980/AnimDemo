package anim.bro.com.practice.tuibida.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import anim.bro.com.practice.R;


/**
 * Created by TangKe on 16/8/1.
 */
public class JuMeiToast {
    public final static int LENGTH_LONG = Toast.LENGTH_LONG;
    public final static int LENGTH_SHORT = Toast.LENGTH_SHORT;

    public static JuMeiToast makeText(Context context, CharSequence text) {
        return makeText(context, text, LENGTH_SHORT);
    }

    public static JuMeiToast makeText(Context context, CharSequence text, int duration) {
        JuMeiToast toast = new JuMeiToast(context);
        toast.setText(text);
        toast.setDuration(duration);
        return toast;
    }

    public static JuMeiToast makeText(Context context, int resId, int duration) {
        JuMeiToast toast = new JuMeiToast(context);
        toast.setText(resId);
        toast.setDuration(duration);
        return toast;
    }

    private Toast mToast;
    private TextView mContentView;

    private JuMeiToast(Context context) {
        try {
            mContentView = (TextView) LayoutInflater.from(context).inflate(R.layout.jumei_toast, null);
            mToast = new Toast(context);
            mToast.setView(mContentView);
        }catch(Exception ex){
            Log.d("JuMeiToast", "出异常了");
            Toast.makeText(context, "出异常了", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void setDuration(int duration) {
        if(null != mToast) {
            mToast.setDuration(duration);
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if(mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void show() {
        if(mToast != null) {
            mToast.show();
        }
    }

    public void cancel() {
        if(null != mToast) {
            mToast.cancel();
        }
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        if(null != mToast) {
            mToast.setMargin(horizontalMargin, verticalMargin);
        }
    }

    public void setText(int resId) {
        if(null != mToast && null != mContentView) {
            mContentView.setText(resId);
        }
    }

    public void setText(CharSequence s) {
        if(null != mToast && null != mContentView) {
            mContentView.setText(s);
        }
    }

    public int getYOffset() {
        if(null != mToast) {
            return mToast.getYOffset();
        }
        return 0;
    }

    public int getXOffset() {
        if(null != mToast) {
            return mToast.getXOffset();
        }
        return 0;
    }

    public View getView() {
        if(null != mToast) {
            return mContentView;
        }
        return null;
    }

    public float getVerticalMargin() {
        if(null != mToast) {
            return mToast.getVerticalMargin();
        }
        return 0;
    }

    public float getHorizontalMargin() {
        if(null != mToast) {
            return mToast.getHorizontalMargin();
        }
        return 0;
    }

    public int getGravity() {
        if(null != mToast) {
            return mToast.getGravity();
        }
        return 0;
    }

    public int getDuration() {
        if(null != mToast) {
            return mToast.getDuration();
        }
        return 0;
    }
}
