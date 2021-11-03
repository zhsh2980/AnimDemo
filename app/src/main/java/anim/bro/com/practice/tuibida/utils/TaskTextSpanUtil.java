package anim.bro.com.practice.tuibida.utils;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SpanUtils;

import anim.bro.com.practice.util.UIUtils;


/**
 * Created by zhangshan on 2018/11/9 19:35.
 */
public class TaskTextSpanUtil {

    private final String TAG = "TaskTextSpanUtil";

    private static Activity mActivity;

    private TaskTextSpanUtil() {
    }

    private static class SingletonInstance {
        private static final TaskTextSpanUtil INSTANCE = new TaskTextSpanUtil();
    }

    public static TaskTextSpanUtil getInstance(Activity activity) {
        mActivity = activity;
        return SingletonInstance.INSTANCE;
    }

    //设置中间的文字
    public void setTvMiddle(TextView textView, String strAll, String strPart) {
        //表示普通的文案
        if (TextUtils.isEmpty(strPart)) {
            textView.setText(strAll);
            return;
        }
        //已完成${progress}%  56.3
        //再花${price}元可得  5.6
        try {
            String str_start = strAll.substring(0, strAll.indexOf("$"));
            String[] moneySplit = strPart.split("\\.");
            String moneyBefore = moneySplit[0];
            String moneyAfter = moneySplit[1];
            String str_end = strAll.substring(strAll.lastIndexOf("}") + 1, strAll.length());
            textView.setText(setSpan(str_start, moneyBefore, "." + moneyAfter, str_end) + "");
        } catch (Exception e) {
            Log.i("bro", " -- setTvMiddle Exception --");
        }
    }

    private SpannableStringBuilder setSpan(String strStart, String moneyBefore, String moneyAfter, String strEnd) {
        SpannableStringBuilder stringBuilder = TextSpanUtils.getBuilder(mActivity, strStart)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append(moneyBefore)
                .setForegroundColor(Color.parseColor("#FFF000"))
                .setTextSize(UIUtils.dip2px(38))
                .append(moneyAfter)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append(strEnd)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .create();
        return stringBuilder;
    }


    //设置中间的文字(已切换至最新任务)
    public void setTvDialogMiddle(TextView textView, String strAll, String strPart) {
        //表示普通的文案
        if (TextUtils.isEmpty(strAll) || TextUtils.isEmpty(strPart)) {
            return;
        }
        try {
            String str_start = strAll.substring(0, strAll.indexOf("$"));
            String str_end = strAll.substring(strAll.lastIndexOf("}") + 1, strAll.length());
            textView.setMovementMethod(CustomLinkMovementMethod.getInstance());
//            textView.setText(setDialogSpan(str_start, strPart, str_end));
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("TaskTextSpanUtil", "点击了文字");
//                }
//            });
            textView.setText(getSpan(str_start, strPart, str_end));
        } catch (Exception e) {
            Log.i("bro", " -- setTvMiddle Exception --");
        }

    }

    private SpannableStringBuilder getSpan(String strStart, String moneyMiddle, String strEnd) {
        return new SpanUtils()
                .append(strStart).setFontSize(12, true).setForegroundColor(Color.WHITE)
                .append(moneyMiddle).setFontSize(12, true).setForegroundColor(Color.parseColor("#FE4070")).setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Log.i(TAG, "点我了,要跳转");
                    }
                })
                .append(strEnd).setFontSize(12, true).setForegroundColor(Color.WHITE)
                .create();
    }

    //发现页已切换到最新任务
    private SpannableStringBuilder setDialogSpan(String strStart, String moneyMiddle, String strEnd) {
        SpannableStringBuilder stringBuilder = TextSpanUtils.getBuilder(SingleContainer.getApplicationContext(), strStart)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .append(moneyMiddle)
                .setForegroundColor(Color.parseColor("#FE4070"))
                .setTextSize(UIUtils.dip2px(12))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View view) {
                        Log.i(TAG, "点我了,要跳转");
                    }
                })
                .append(strEnd)
                .setForegroundColor(Color.parseColor("#FFFFFF"))
                .setTextSize(UIUtils.dip2px(12))
                .create();
        return stringBuilder;
    }


}
