package anim.bro.com.animdemo.view;

/**
 * Created by zhangshan on 2021/10/25 15:31.
 * Description：请添加类注释
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;


import anim.bro.com.animdemo.Interface.UnableQuickClickView;
import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.util.StringUtils;
import anim.bro.com.animdemo.util.UIUtils;

/**
 * 用于展示 textView后面接 图片和文字
 */
public class AdvertThreeTextView extends AppCompatTextView implements UnableQuickClickView {
    private SpannableStringBuilder spannableStringBuilder;
    private Drawable drawable;
    private ImageSpan imageSpan;
    private String labelTitle;
    private int length = 0;
    private Paint paint = new Paint();
    private Paint mPaint = new Paint();

    private boolean isThreeLine = false;

    private long lastClickTime;
//    private JmSettings mSharedPreferences;

    public AdvertThreeTextView(Context context) {
        this(context, null);
    }

    public AdvertThreeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawable = this.getResources().getDrawable(R.drawable.ic_yuanbao_black);
    }

    @Override
    public boolean isFastMultipleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        int btn_click_time = 1 * 1000;//转换成毫秒
        if (0 < timeD && timeD < btn_click_time) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public boolean isFastMultipleClick(int clickTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < clickTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 设置末尾带标签
     *
     * @param content
     * @param labelTitle
     */
    public synchronized void setTextContent(String content, String labelTitle) {
        setHighlightColor(Color.parseColor("#00000000"));
        if (spannableStringBuilder != null) {
            if (imageSpan != null) {
                spannableStringBuilder.removeSpan(imageSpan);
            }
        }
        length = 0;
        if (!TextUtils.isEmpty(labelTitle)) {
            length = labelTitle.length() + 2;//保险起见  超出后多 删2个字符
        }
        this.labelTitle = "\t" + labelTitle;
        if (!TextUtils.isEmpty(content)) {
            setText(content);
        } else {
            setText("\t\t");
        }
        setLabelTitile(labelTitle);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDraw(Canvas canvas) {
        try {
            int maxLines = getMaxLines();
            if (maxLines > 0 && getLayout().getLineCount() > maxLines) {
                isThreeLine = true;
                CharSequence charSequence = getText();
                int lastCharDown = getLayout().getLineVisibleEnd(maxLines - 1);
                if (charSequence.length() > lastCharDown) {
                    spannableStringBuilder = new SpannableStringBuilder();
                    spannableStringBuilder.append(charSequence.subSequence(0, lastCharDown - length)).append("...\t");
                    setText(spannableStringBuilder);
                    setLabelTitile(labelTitle);
                }
            } else {
                isThreeLine = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("onDrawonDrawon", labelTitle + "   ");
        super.onDraw(canvas);
    }

    private void setLabelTitile(String labelTitle) {
        try {
            if (!TextUtils.isEmpty(labelTitle.trim())) {
//                if (drawable == null) {
                    drawable = getResources().getDrawable(R.drawable.anim_box_1);
//                }
                imageSpan = new ImageSpan(getContext(), drawableToBitmap(drawable, labelTitle));
                spannableStringBuilder = new SpannableStringBuilder(getText().toString() + "\t");
                spannableStringBuilder.setSpan(imageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                append(spannableStringBuilder);
                setText(spannableStringBuilder);
            }
        } catch (Exception e) {
            Log.e("AdvertThreeTextView", e.getMessage());
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable, String title) {

        // 取 drawable 的长宽
        int w = 0;
        int h = UIUtils.dip2px(7);

        // 取 drawable 的颜色格式
        Bitmap.Config config = Bitmap.Config.ARGB_8888;

        paint.setColor(Color.parseColor("#ffffff"));
        paint.setTextSize(UIUtils.dip2px(9));
//        paint.setAlpha(55);//文字透明度
        paint.setAntiAlias(true);
        float length = paint.measureText(title);

        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap((int) (w + length + UIUtils.dip2px(6)), h + UIUtils.dip2px(8), config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);

        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(UIUtils.dip2px(2));
//        mPaint.setAlpha(100);//背景透明度

        canvas.drawRoundRect(new RectF(0, 0, w + length + UIUtils.dip2px(6), h + UIUtils.dip2px(5)), UIUtils.dip2px(2), UIUtils.dip2px(2), mPaint);

        drawable.setBounds(0, 0, w, h);
        if (isThreeLine) {
            // 把 drawable 内容画到画布中
            canvas.translate(UIUtils.dip2px(0.0f), UIUtils.dip2px(0.0f));
        } else {
            canvas.translate(UIUtils.dip2px(1.1f), UIUtils.dip2px(0.0f));
        }
//        drawable.draw(canvas);
//        canvas.translate(-UIUtils.dip2px(2), -UIUtils.dip2px(2));
        canvas.drawText(title, w + UIUtils.dip2px(2), h + UIUtils.dip2px(2), paint);
        return bitmap;
    }
}