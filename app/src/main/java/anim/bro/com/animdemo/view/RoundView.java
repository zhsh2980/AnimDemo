package anim.bro.com.animdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.ConvertUtils;

import anim.bro.com.animdemo.R;

/**
 * Created by zhangshan on 2018/12/6 18:09.
 */

/**
 * 自定义进度圆环
 */
public class RoundView extends View {

    private final String TAG = "RoundView";

    // Y轴坐标
    private float curTranslationY;
    // X轴坐标
    private float curTranslationX;

    /**
     * 圆环宽度,默认半径的1／5
     */
    private float mRingWidth = 0;
    /**
     * 圆环颜色,默认 #CBCBCB
     */
    private int mRingColor = 0;

    /**
     * 圆环半径,默认：Math.min(getHeight()/2,getWidth()/2)
     */
    private float mRadius = 0;
    /**
     * 底环画笔
     */
    private Paint mRingPaint;
    private ValueAnimator mCircleAnimator;//圆圈放大再消失
    private ValueAnimator mTranslateXAnimator;// X 位移
    private ValueAnimator mTranslateYAnimator;// Y 位移
    private AnimatorSet mTranslateAnimSet;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        if (typedArray != null) {
            mRingWidth = typedArray.getDimension(R.styleable.RoundView_ring_width, 10);
            mRingColor = typedArray.getColor(R.styleable.RoundView_ring_color, getResources().getColor(R.color.colorAccent));
            mRadius = typedArray.getDimension(R.styleable.RoundView_radius, 0);
            typedArray.recycle();
        }
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        curTranslationY = getTranslationY();
        curTranslationX = getTranslationX();

//        Log.i(TAG, "curTranslationY: " + curTranslationY + "-----curTranslationX: " + curTranslationX);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);// 抗锯齿效果
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mRingWidth);
        mRingPaint.setColor(mRingColor);// 背景
    }

    @Override
    public void onDraw(Canvas canvas) {
        // 圆心坐标,当前View的中心
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        //如果未设置半径，则半径的值为view的宽、高一半的较小值
//        mRadius = mRadius == 0 ? Math.min(getWidth() / 2, getHeight() / 2) : mRadius;
        //圆环的宽度默认为半径的1／5
//        mRingWidth = (mRingWidth == 0 ? mRadius / 5 : mRingWidth);
        // 底环
//        canvas.drawCircle(x, y, mRadius, mRingPaint);
        mRingPaint.setStrokeWidth(mRingWidth);
        canvas.drawCircle(x, y, mRadius - mRingWidth / 2, mRingPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int width;

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = (int) mRadius * 2 + getPaddingLeft() + getPaddingRight();

            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
//        mViewWidth = width;
//        circleRadius = width/2;
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;

        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);


        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            //不是精确模式的话得自己结合paddin
            int desire = (int) mRadius * 2 + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
//        mViewHeight = height;
        return height;
    }


    /**
     * 圆环宽度
     *
     * @param width 单位: dp
     */
    public void setRingWidth(float width) {
        if (width > mRadius) {
            width = mRadius;
        }
        int widthPx = ConvertUtils.dp2px(width);
        mRingWidth = widthPx;
        if (mRingPaint != null) {
            mRingPaint.setStrokeWidth(widthPx);
            invalidate();
//            requestLayout();
        }
    }

    /**
     * 半径
     *
     * @param radius 单位: dp
     */
    public void setRadius(float radius) {
        mRadius = ConvertUtils.dp2px(radius);
        invalidate();
//        requestLayout();
    }

    public void startCircleAnim(final int startRadius, final int endRadius) {
        resetAnim();
        setVisibility(VISIBLE);
        //1.实心圆先放大
//        final int startRadius = ConvertUtils.px2dp(mRadius);
        //半径变化范围
        mCircleAnimator = ValueAnimator.ofFloat(startRadius, endRadius);
        mCircleAnimator.setDuration(500);
        mCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();

                mRadius = ConvertUtils.dp2px(value);

                //2.边框变窄
                if (value < (endRadius + startRadius) / 2) {
                    mRingWidth = ConvertUtils.dp2px(value);
                } else if (value < endRadius) {
                    mRingWidth = ConvertUtils.dp2px(endRadius - value);
                } else {
                    setVisibility(GONE);
                }

                invalidate();
            }
        });
//        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setRepeatMode(ValueAnimator.RESTART);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.start();
        mCircleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        });

    }

    public void startTranslateAnim(int xOffset, int yOffset, long duration) {
        resetAnim();
        setVisibility(VISIBLE);
        setAlpha(1.0f);
//        setAlpha(0.5f);
        //X坐标变化
        mTranslateXAnimator = ValueAnimator.ofFloat(curTranslationX, curTranslationX + xOffset);
        mTranslateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setTranslationX(value);
            }
        });
        //半径变化范围
        mTranslateYAnimator = ValueAnimator.ofFloat(curTranslationY, curTranslationY + yOffset);
        mTranslateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setTranslationY(value);
            }
        });

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.8F, 0.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.8F, 0.2f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 1.0f, 1.0f, 0f);

        mTranslateAnimSet = new AnimatorSet();
        mTranslateAnimSet.setDuration(duration);
        mTranslateAnimSet.setInterpolator(new LinearInterpolator());
        mTranslateAnimSet.playTogether(mTranslateXAnimator, mTranslateYAnimator, scaleX, scaleY, alpha);
        mTranslateAnimSet.start();
        mTranslateAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        });

    }

    public void resetAnim() {
        if (mCircleAnimator != null && mCircleAnimator.isRunning()) {
            mCircleAnimator.cancel();
        }
        if (mTranslateAnimSet != null && mTranslateAnimSet.isRunning()) {
            mTranslateAnimSet.cancel();
        }
    }

    public void setCircleColor(int color) {
        mRingPaint.setColor(color);
    }


}