package anim.bro.com.animdemo.view;

/**
 * Created by zhangshan on 2021/10/25 14:42.
 * Description：请添加类注释
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;

import anim.bro.com.animdemo.R;

/**
 * 在文本后面拼接一个view
 *
 * @author ly
 * date 2020/3/9 10:22
 */
public class AppendViewAfterTextView extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {

    private TextView tv;
    private TextView tvSpecial;
    private String text;
    private FrameLayout.LayoutParams paramsSpecial;
    private int space;

    public AppendViewAfterTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public AppendViewAfterTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppendViewAfterTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        space = SizeUtils.dp2px(5);

        tv = new TextView(getContext());
        tv.setTextSize(21);
        tv.setLineSpacing(1.0f, 1.2f);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setIncludeFontPadding(false);
        setTextColor(ContextCompat.getColor(getContext(), R.color.black));

        tvSpecial = new TextView(getContext());
        tvSpecial.setBackgroundResource(R.drawable.anim_box_1);
        tvSpecial.setTextSize(12);
        tvSpecial.setSingleLine();
        tvSpecial.setTextColor(Color.WHITE);

        paramsSpecial = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvSpecial.setLayoutParams(paramsSpecial);

        addView(tv, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tvSpecial);
    }

    @Override
    public void onGlobalLayout() {
        //为保证TextView.getLayout()!=null，在这里再执行相关逻辑
        setMoreViewPosition();
        //记得移除，不然会一直回调
        tv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    private void setMoreViewPosition() {
        Layout layout = tv.getLayout();
        if (layout == null)
            return;
        int lineCount = layout.getLineCount();
        float lineWidth = layout.getLineWidth(lineCount - 1);

        //获取最后行最后一个字符的下标
        int lineEnd = layout.getLineEnd(lineCount - 1);
        if (lineWidth + tvSpecial.getMeasuredWidth() + space - (getWidth() - getPaddingRight() - getPaddingLeft()) > 0) {//最后一行显示不下，将最后一行换行
            if (text.length() > 2) {
                //分两个字符到tvSpecial那一行，更协调
                text = text.subSequence(0, text.length() - 2) + "\n" + text.subSequence(text.length() - 2, text.length());
                setText(text);
                return;//setText会重新触发onGlobalLayout
            }
        }
//        int lineH = layout.getLineBottom(lineCount - 1) - layout.getLineTop(lineCount - 1);
        int lineH = layout.getHeight() / layout.getLineCount();
        int lastLineRight = (int) layout.getSecondaryHorizontal(lineEnd);
        paramsSpecial.leftMargin = lastLineRight + space;
        //tvSpecial的中间和tv最后一行的中间上下对齐
        paramsSpecial.topMargin = layout.getHeight() - tv.getPaddingBottom() - lineH / 2 - tvSpecial.getHeight() / 2;
        tvSpecial.setLayoutParams(paramsSpecial);
    }

    public void setText(final String text) {
        this.text = text;
        tv.setText(this.text);
        tv.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setSpecialViewText(String text) {
        tvSpecial.setText(text);
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }
}