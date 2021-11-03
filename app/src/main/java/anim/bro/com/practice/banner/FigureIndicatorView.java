package anim.bro.com.practice.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.zhpan.indicator.base.BaseIndicatorView;

import anim.bro.com.practice.R;
import anim.bro.com.practice.banner.bannerview.utils.BannerUtils;

/**
 * Created by zhangshan on 2021/10/15 16:22.
 * Description：请添加类注释
 */
public class FigureIndicatorView extends BaseIndicatorView {

    private int radius = BannerUtils.dp2px(20);

    private int backgroundColor = Color.parseColor("#88FF5252");

    private int textColor = Color.WHITE;

    private int textSize = BannerUtils.dp2px(13);

    private Paint mPaint;

    private Bitmap mBitmap;

    private Rect mTopSrcRect, mTopDestRect;
    private int dp_23;
    private int dp_35;
    private int dp_40;

    public FigureIndicatorView(Context context) {
        this(context, null);
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        initBitmap();
        dp_23 = SizeUtils.dp2px(23);
        dp_35 = SizeUtils.dp2px(35);
        dp_40 = SizeUtils.dp2px(40);
    }

    private void initBitmap() {
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.icon_banner_vertical))
                .getBitmap();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2 * radius, 2 * radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {

            mTopSrcRect = new Rect(0, 0, dp_35, dp_23);
            mTopDestRect = new Rect(0, 0, dp_35, dp_23);
            canvas.drawBitmap(mBitmap, mTopSrcRect, mTopDestRect, mPaint);

//            mPaint.setColor(backgroundColor);
//            canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mPaint);
            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize);
            String text = getCurrentPosition() + 1 + "/" + getPageSize();
            int textWidth = (int) mPaint.measureText(text);
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int baseline = (getMeasuredHeight() - fontMetricsInt.bottom + fontMetricsInt.top) / 2
                    - fontMetricsInt.top;
            canvas.drawText(text, (getWidth() - textWidth) / 2f, baseline - dp_23 / 4, mPaint);
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
