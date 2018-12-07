package anim.bro.com.animdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangshan on 2018/12/7 16:06.
 */
public class ClipProgressView extends AppCompatImageView {

    private final String TAG = "clipProgressView";

    private ValueAnimator mClipAnimator;
    private ClipDrawable mClipDrawable;
    private int mHeight;
    // Y轴坐标
    private float curTranslationY;
    private AnimatorSet mAnimatorSet;

    public ClipProgressView(Context context) {
        this(context, null);
    }

    public ClipProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClipProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                int viewWidth = getWidth();
                mHeight = getHeight();
                curTranslationY = getTranslationY();
                Log.d(TAG, "viewWidth:" + viewWidth + "\nviewHeight:" + mHeight);
            }
        });
        mClipDrawable = (ClipDrawable) getDrawable();
    }

    //供外界设置进度
    public void setLevel(int level) {
        mClipDrawable.setLevel(level);
    }

    public void setClipProgressAnimView(int gotoSize, int totalSize) {
        resetAnim();
        setVisibility(VISIBLE);
        int level = mClipDrawable.getLevel();
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
            }
        });


    }

    private void resetAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        if (mClipDrawable != null) {
            mClipDrawable.setLevel(0);
        }
    }
}
