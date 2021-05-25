package anim.bro.com.animdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import anim.bro.com.animdemo.R;

/**
 * Created by zhangshan on 2018/12/7 16:06.
 */
public class TransTextView extends AppCompatTextView {

    private final String TAG = "clipImageView";

    private TransTextView iv2ndView;//第二张图片(传进来)
    private RelativeLayout mRelaRoot;//根容器
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

    public void setView(final TransTextView iv2ndView, final RelativeLayout relaRoot) {
        this.iv2ndView = iv2ndView;
        mRelaRoot = relaRoot;
        iv2ndView.post(new Runnable() {
            @Override
            public void run() {
                mHeight2 = iv2ndView.getHeight();
                curTranslationY2 = iv2ndView.getTranslationY();
            }
        });
    }


    public void setClipAnim() {
        resetAnim();
        setBackgroundResource(R.drawable.pink);
        iv2ndView.setBackgroundResource(R.drawable.pink);
        ViewGroup parent = (ViewGroup) getParent();
        mDistance = parent.getHeight() - parent.getTop();
        setTranslationY(-mDistance);
        setVisibility(VISIBLE);
        setAlpha(1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", -mDistance, 0);

        final ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0f);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(2000);
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.play(translationY);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mRelaRoot.setVisibility(INVISIBLE);
                setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //第一张图向上 + 渐变
                mTranstationY_1_2 = ValueAnimator.ofFloat(0, -mHeight);
                mTranstationY_1_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setTranslationY(value);
                    }
                });


                //第二张图 位移动画
                ViewGroup parent2 = (ViewGroup) iv2ndView.getParent();
                mDistance2 = parent2.getHeight() - parent2.getTop();
                ObjectAnimator translationY2 = ObjectAnimator.ofFloat(iv2ndView, "translationY", -mDistance2, 0);
                ObjectAnimator alpha2 = ObjectAnimator.ofFloat(iv2ndView, "alpha", 1.0f, 0f);
                iv2ndView.setTranslationY(-mDistance2);
                iv2ndView.setAlpha(1.0f);
                iv2ndView.setVisibility(VISIBLE);


                mAnimatorSet2 = new AnimatorSet();
                mAnimatorSet2.setDuration(2000);
                mAnimatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimatorSet2.play(mTranstationY_1_2).with(alpha).
                        with(translationY2).before(alpha2);
                mAnimatorSet2.start();
                mAnimatorSet2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        mRelaRoot.setVisibility(INVISIBLE);
                        iv2ndView.setVisibility(INVISIBLE);
                        setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRelaRoot.setVisibility(INVISIBLE);
                        iv2ndView.setVisibility(INVISIBLE);
                        setVisibility(INVISIBLE);
                    }
                });
            }
        });
    }


    public void startFirstSingleAnim() {

        resetFirstSingleAnim();
        setBackgroundResource(R.drawable.grey_hard);
        ViewGroup parent = (ViewGroup) getParent();
        mDistance = parent.getHeight() - parent.getTop();
        setTranslationY(-mDistance);
        setVisibility(VISIBLE);
        setAlpha(1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", -mDistance, 0);

        final ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0f);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(2000);
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet.play(translationY);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mRelaRoot.setVisibility(INVISIBLE);
                setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //第一张图向上 + 渐变
                mTranstationY_1_2 = ValueAnimator.ofFloat(0, -mHeight);
                mTranstationY_1_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        setTranslationY(value);
                    }
                });

                mAnimatorSet2 = new AnimatorSet();
                mAnimatorSet2.setDuration(2000);
                mAnimatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimatorSet2.play(mTranstationY_1_2).with(alpha);
                mAnimatorSet2.start();
                mAnimatorSet2.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        mRelaRoot.setVisibility(INVISIBLE);
                        setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRelaRoot.setVisibility(INVISIBLE);
                        setVisibility(INVISIBLE);
                    }
                });
            }
        });
    }


    public void startSecondSingleAnim() {

        resetSecondSingleAnim();

        iv2ndView.setBackgroundResource(R.drawable.grey_hard);
        iv2ndView.setText("体力不足");

        //第二张图 位移动画
        ViewGroup parent2 = (ViewGroup) iv2ndView.getParent();
        mDistance2 = parent2.getHeight() - parent2.getTop();
        ObjectAnimator translationY2 = ObjectAnimator.ofFloat(iv2ndView, "translationY", -mDistance2, 0);
        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(iv2ndView, "alpha", 1.0f, 0f);
        iv2ndView.setTranslationY(-mDistance2);
        iv2ndView.setAlpha(1.0f);
        iv2ndView.setVisibility(VISIBLE);

        mAnimatorSet2 = new AnimatorSet();
        mAnimatorSet2.setDuration(1500);
        mAnimatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorSet2.play(translationY2).before(alpha2);
        mAnimatorSet2.start();
        mAnimatorSet2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mRelaRoot.setVisibility(INVISIBLE);
                iv2ndView.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRelaRoot.setVisibility(INVISIBLE);
                iv2ndView.setVisibility(INVISIBLE);
            }
        });
    }


    public void stopAnimSetAlpha() {

        if (mAnimatorSet != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAnimatorSet.pause();
            }
            ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", getAlpha(), 0f);
            alpha.setDuration(300);
            alpha.setInterpolator(new LinearInterpolator());
            alpha.start();
        }
        if (mAnimatorSet2 != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAnimatorSet2.pause();
            }

            ObjectAnimator alpha = ObjectAnimator.ofFloat(iv2ndView, "alpha", iv2ndView.getAlpha(), 0f);
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
        //开始动画,显示根容器,结束动画隐藏
        mRelaRoot.setVisibility(VISIBLE);
    }

    public void resetSecondSingleAnim() {
        resetAnim();
        if (iv2ndView != null) {
            ViewGroup parent2 = (ViewGroup) iv2ndView.getParent();
            mDistance2 = parent2.getHeight() - parent2.getTop();
            iv2ndView.setTranslationY(-mDistance2);
            iv2ndView.setAlpha(1.0f);
            iv2ndView.setVisibility(VISIBLE);
        }
    }

    public void resetFirstSingleAnim() {
        resetAnim();
        ViewGroup parent = (ViewGroup) getParent();
        mDistance = parent.getHeight() - parent.getTop();
        setTranslationY(-mDistance);
        setAlpha(1.0f);
        setVisibility(VISIBLE);
    }
}
