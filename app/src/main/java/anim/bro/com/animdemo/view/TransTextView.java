package anim.bro.com.animdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangshan on 2018/12/7 16:06.
 */
public class TransTextView extends AppCompatTextView {

    private final String TAG = "clipImageView";

    private TransTextView iv2ndView;//第二张图片(传进来)
    private int mHeight;
    private int mHeight2;
    // Y轴坐标
    private float curTranslationY;
    private float curTranslationY2;
    private AnimatorSet mAnimatorSet;
    private AnimatorSet mAnimatorSet2;
    private ValueAnimator mTranstationY;
    private ValueAnimator mTranstationY_1_2;
    private int mDistance;
    private int mDistance2;

    public TransTextView(Context context) {
        this(context, null);
    }

    public TransTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TransTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    }

    public void set2ndIvView(final TransTextView iv2ndView) {
        this.iv2ndView = iv2ndView;
        iv2ndView.post(new Runnable() {
            @Override
            public void run() {
                mHeight2 = iv2ndView.getHeight();
                curTranslationY2 = iv2ndView.getTranslationY();
            }
        });
//        ViewGroup parent = (ViewGroup) iv2ndView.getParent();
//        mDistance2 = parent.getHeight() - parent.getTop();
    }

    public void setClipAnim() {

//        resetAnim();

        //位移动画
//        mTranstationY = ValueAnimator.ofFloat(curTranslationY, curTranslationY - mHeight);
//        mTranstationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (float) animation.getAnimatedValue();
//                setTranslationY(value);
//            }
//        });
        ViewGroup parent = (ViewGroup) getParent();
        mDistance = parent.getHeight() - parent.getTop();
        setTranslationY(-mDistance);
        setVisibility(VISIBLE);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", -mDistance, 0);

        //第一张图向上 + 渐变
        mTranstationY_1_2 = ValueAnimator.ofFloat(curTranslationY - mHeight, curTranslationY - mHeight * 2);
        mTranstationY_1_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setTranslationY(value);
            }
        });
        final ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0f);

        //第二张图
        //位移动画
        final ValueAnimator mTranstationY_2_1 = ValueAnimator.ofFloat(curTranslationY2, curTranslationY2 - mHeight2);
        mTranstationY_2_1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                iv2ndView.setTranslationY(value);
            }
        });
        final ObjectAnimator alpha2 = ObjectAnimator.ofFloat(iv2ndView, "alpha", 1.0f, 0f);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(2000);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.play(translationY);
        mAnimatorSet.start();


        mAnimatorSet2 = new AnimatorSet();
        mAnimatorSet2.setDuration(2000);
        mAnimatorSet2.setInterpolator(new LinearInterpolator());


        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                setVisibility(GONE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimatorSet2.play(mTranstationY_1_2).with(alpha).
                with(mTranstationY_2_1).before(alpha2);
                mAnimatorSet2.start();
                mAnimatorSet2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
//                        iv2ndView.setVisibility(GONE);
//                        setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
//                        iv2ndView.setVisibility(GONE);
//                        setVisibility(GONE);
                    }
                });
            }
        });


    }


    public void stopAnimSetAlpha() {

        if (mAnimatorSet != null) {
            mAnimatorSet.pause();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0f);
            alpha.setDuration(300);
            alpha.setInterpolator(new LinearInterpolator());
            alpha.start();
        }
        if (mAnimatorSet2 != null) {
            mAnimatorSet2.pause();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(iv2ndView, "alpha", 1.0f, 0f);
            alpha.setDuration(300);
            alpha.setInterpolator(new LinearInterpolator());
            alpha.start();
        }

    }

    public void resetAnim() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        if (mAnimatorSet2 != null && mAnimatorSet2.isRunning()) {
            mAnimatorSet2.cancel();
        }
        if (iv2ndView != null) {
            iv2ndView.setAlpha(1.0f);
            iv2ndView.setVisibility(VISIBLE);
        }
        setAlpha(1.0f);
        setVisibility(VISIBLE);
    }
}
