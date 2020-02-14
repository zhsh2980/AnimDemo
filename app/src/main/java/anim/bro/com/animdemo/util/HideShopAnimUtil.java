package anim.bro.com.animdemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;

import anim.bro.com.animdemo.R;


public class HideShopAnimUtil {
    ImageView shopView, shopViewShort;
    private final String TAG = "HideShopAnimUtil";

    private int mHeight;
    private int mHeightShort;
    // Y轴坐标
    private float curTranslationX;
    private float curTranslationXShort;
    private int mWidth;
    private int mWidthShort;

    private ValueAnimator mTranstationX1;
    private ValueAnimator mTranstationXShort1;

    private ValueAnimator mTranstationX2;
    private ValueAnimator mTranstationXShort2;

    private int shutUpTime = 1;//停留时间
    private int shakeIntervalTime = 5;//抖动时间间隔
    private int shakeNum = 3;//抖动次数

    private final static int MESSAGE_SHAKE = 1;
    private final static int MESSAGE_INTERVAL = 2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SHAKE:
                    showShakeAnim();
//                    hideShopView();
                    break;
                case MESSAGE_INTERVAL:
                    if (shakeNum <= 0) {
                        handler.removeMessages(MESSAGE_INTERVAL);
                    } else {
                        showShakeAnim();
                    }
                    break;
            }
        }
    };
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;

    public HideShopAnimUtil(final ImageView shopView, final ImageView shopViewShort) {
        this.shopView = shopView;
        this.shopViewShort = shopViewShort;

        shopView.post(new Runnable() {
            @Override
            public void run() {
                mWidth = shopView.getWidth();
                mHeight = shopView.getHeight();
                curTranslationX = shopView.getTranslationX();
                Log.d(TAG, "viewWidth:" + mWidth + "\nviewHeight:" + mHeight);
            }
        });
        shopViewShort.post(new Runnable() {
            @Override
            public void run() {
                mWidthShort = shopViewShort.getWidth();
                mHeightShort = shopViewShort.getHeight();
                curTranslationXShort = shopViewShort.getTranslationX();
                Log.d(TAG, "viewWidth:" + mWidthShort + "\nviewHeight:" + mHeightShort);
            }
        });

    }

    public void showShopView() {
        initApiData();
        if (shopView == null) return;
        shopView.clearAnimation();
        handler.removeMessages(MESSAGE_INTERVAL);
        //位移动画
        mTranstationX1 = ValueAnimator.ofFloat(curTranslationX, curTranslationX - mWidth - ConvertUtils.dp2px(9));
        mTranstationX1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationX(value);
            }
        });
        mTranstationX1.setDuration(1000);

        //位移动画
        mTranstationXShort1 = ValueAnimator.ofFloat(curTranslationXShort, curTranslationXShort + mWidthShort);
        mTranstationXShort1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopViewShort.setTranslationX(value);
            }
        });
        mTranstationXShort1.setDuration(300);

        mShowAnimatorSet = new AnimatorSet();
        mShowAnimatorSet.setInterpolator(new LinearInterpolator());
        mShowAnimatorSet.play(mTranstationXShort1).before(mTranstationX1);
        mShowAnimatorSet.start();
        mShowAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //开始倒计时1秒,开始抖动
                handler.removeMessages(MESSAGE_SHAKE);
                handler.sendEmptyMessageDelayed(MESSAGE_SHAKE, shutUpTime * 1000);
            }
        });

    }

    private void initApiData() {
        shutUpTime = 1;
        shakeIntervalTime = 5;
        shakeNum = 3;

    }

    /**
     * 晃动动画
     * <p>
     * 那么CycleInterpolator是干嘛用的？？
     * Api demo里有它的用法，是个摇头效果！
     *
     * @param counts 1秒钟晃动多少下
     * @return Animation
     */
    public Animation shakeAnimation(int counts) {
        Animation rotateAnimation = new RotateAnimation(0, 15, Animation.RELATIVE_TO_SELF, -0.6f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setInterpolator(new CycleInterpolator(counts));
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setDuration(500);
        return rotateAnimation;
    }

    //抖动动画
    private void showShakeAnim() {
        if (shakeNum > 0) {
            shakeNum--;
            doShakeAnim();
        }
    }


    private void doShakeAnim() {
        //颤抖动画交给底部tab组件
        if (shopView != null) {
//            shake();
            shopView.clearAnimation();
//            Animation shake = AnimationUtils.loadAnimation(shopView.getContext(), R.anim.add_tab_shake_anim);
            Animation shake = shakeAnimation(1);
            shake.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
//                    JuMeiLogMng.getInstance().i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画开始"));
                }

                @Override
                public void onAnimationEnd(Animation animation) {
//                    if(null != animView) {
//                        animView.clearAnimation();
//                    }
                    //开始倒计时1秒,开始抖动
                    handler.removeMessages(MESSAGE_INTERVAL);
                    handler.sendEmptyMessageDelayed(MESSAGE_INTERVAL, shakeIntervalTime * 1000);
//                    JuMeiLogMng.getInstance().i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画结束"));

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            shopView.startAnimation(shake);
        }

    }

    public void hideShopView() {
        shopView.clearAnimation();
        shakeNum = 0;
//        shopViewShort.setVisibility(View.INVISIBLE);
//        shopView.setVisibility(View.INVISIBLE);
        //位移动画
        mTranstationX2 = ValueAnimator.ofFloat(curTranslationX - mWidth - ConvertUtils.dp2px(9), curTranslationX);
        mTranstationX2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationX(value);
            }
        });
        mTranstationX2.setDuration(1000);

        //位移动画
        mTranstationXShort2 = ValueAnimator.ofFloat(curTranslationXShort + mWidthShort, curTranslationXShort);
        mTranstationXShort2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopViewShort.setTranslationX(value);
            }
        });
        mTranstationXShort2.setDuration(300);

        mHideAnimatorSet = new AnimatorSet();
        mHideAnimatorSet.setInterpolator(new LinearInterpolator());
        mHideAnimatorSet.play(mTranstationX2).before(mTranstationXShort2);
        mHideAnimatorSet.start();
    }

    public void resetAnim() {
        if (mShowAnimatorSet != null && mShowAnimatorSet.isRunning()) {
            mShowAnimatorSet.cancel();
        }
        if (mHideAnimatorSet != null && mHideAnimatorSet.isRunning()) {
            mHideAnimatorSet.cancel();
        }
        shopView.setTranslationX(0);
        shopViewShort.setTranslationX(0);

    }


}
