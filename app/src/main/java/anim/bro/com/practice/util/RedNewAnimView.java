package anim.bro.com.practice.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


public class RedNewAnimView extends AppCompatImageView {

    // Y轴坐标
    private float curTranslationY;
    private AnimatorSet mAnimatorSet;
    private ImageView ivRedReal;

    public RedNewAnimView(Context context) {
        this(context, null);
    }

    public RedNewAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RedNewAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        curTranslationY = getTranslationY();

    }

    boolean isFront = false;

    //传入真正的红包 view
    public void setAnimViewReal(ImageView ivRedReal) {
        this.ivRedReal = ivRedReal;
    }

    public void startRedNewAnim() {
        startRedNewAnim(720);
    }

    public void startRedNewAnim(long duration) {
        resetAnim();
        setVisibility(VISIBLE);
        float max = 1.3f;
        ObjectAnimator scaleXBefore = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, max);
        ObjectAnimator scaleYBefore = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, max);
        scaleXBefore.setDuration(200);
        scaleYBefore.setDuration(200);
        scaleXBefore.start();
        scaleYBefore.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", max, 1.1f, max, 1.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", max, 1.1f, max, 1.2f, 1.0f);
        final ValueAnimator animTranstationY = ValueAnimator.ofFloat(curTranslationY, curTranslationY - 200, curTranslationY);
        animTranstationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setTranslationY(value);
                if (value < curTranslationY - 100 && !isFront) {
                    bringToFront();
                    isFront = true;
                }
            }
        });
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(duration);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.playTogether(scaleX, scaleY, animTranstationY);
        mAnimatorSet.setStartDelay(200);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isFront = false;
                setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isFront = false;
                setVisibility(GONE);
            }
        });
    }

    public void resetAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        if (ivRedReal != null) {
            ivRedReal.bringToFront();
        }
    }

}
