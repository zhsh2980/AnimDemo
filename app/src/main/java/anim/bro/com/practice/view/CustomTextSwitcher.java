package anim.bro.com.practice.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by zhangshan on 2022/6/28 19:41.
 * Description：请添加类注释
 */
public class CustomTextSwitcher extends TextSwitcher {

    public CustomTextSwitcher(Context context) {
        super(context);
    }

    public CustomTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTextContent(CharSequence text) {
        TextView currentView = (TextView) getCurrentView();
        String strTextView = currentView.getText().toString().trim();
        if (TextUtils.isEmpty(strTextView)) {
            setCurrentText(text);
        } else if (!TextUtils.equals(text, strTextView)) {
            setText(text);
        } else {
//            LogUtils.i("相同,不赋值");
        }
    }
}
