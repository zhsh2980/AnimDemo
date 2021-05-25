package anim.bro.com.animdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangshan on 2018/12/7 16:06.
 */
public class ClipRainProgressView extends AppCompatImageView {

    private final String TAG = "clipProgressView";

    private ValueAnimator mClipAnimator;
    private ClipDrawable mClipDrawable;
    // Y轴坐标
    private AnimatorSet mAnimatorSet;
    private Paint mPaint;

    public ClipRainProgressView(Context context) {
        this(context, null);
    }

    public ClipRainProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClipRainProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClipDrawable = (ClipDrawable) getDrawable();

        mPaint = new Paint();
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(38);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

    }

    //供外界设置进度
    public void setLevel(int fromSize, int totalSize) {
        int level = fromSize * 10000 / totalSize;
        mClipDrawable.setLevel(level);
    }

    String textMiddle = "";

    public void setText(int fromSize, int totalSize) {
        fromSize = Math.min(fromSize, totalSize);
        this.textMiddle = "剩" + (totalSize - fromSize) + "个";
        invalidate();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        Rect bounds = new Rect();
//        mPaint.getTextBounds(textMiddle, 0, textMiddle.length(), bounds);
//
//        //计算baseline
//        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
//        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
//
//        canvas.drawText(textMiddle, getMeasuredWidth() / 2, getMeasuredHeight() / 2 + distance, mPaint);
    }

    public void setClipProgressAnimView(int fromSize, final int gotoSize, final int totalSize) {
        resetAnim();
        setVisibility(VISIBLE);
        int level = fromSize * 10000 / totalSize;
        int clipSize = gotoSize * 10000 / totalSize;
        //裁剪动画
        mClipAnimator = ValueAnimator.ofInt(level, clipSize);
        mClipAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                mClipDrawable.setLevel(value);
            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(600);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.play(mClipAnimator);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setText(gotoSize, totalSize);
            }
        });


    }

    public void resetAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        if (mClipDrawable != null) {
            mClipDrawable.setLevel(0);
        }
    }
}
