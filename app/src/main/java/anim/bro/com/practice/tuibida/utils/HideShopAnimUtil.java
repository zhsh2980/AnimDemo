package anim.bro.com.practice.tuibida.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;


public class HideShopAnimUtil {
    ImageView shopView;
    private final String TAG = "HideShopAnimUtil";

    private int mHeight;
    private int mHeightShort;
    // Y轴坐标
    private float curTranslationX;
    private int mWidth;
    private int mWidthShort;

    private ValueAnimator mTranstationX1;

    private ValueAnimator mTranstationX2;

    private final static int WHAT_SEND = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_SEND:
//                    hideShopView();
                    break;
            }
        }
    };
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;

    public HideShopAnimUtil(final ImageView shopView) {
        this.shopView = shopView;
        shopView.post(() -> {
            mWidth = shopView.getWidth();
            mHeight = shopView.getHeight();
            curTranslationX = shopView.getTranslationX();
            Log.d(TAG, "viewWidth:" + mWidth + "\nviewHeight:" + mHeight+ "\ncurTranslationX:" + curTranslationX);
        });
    }

    public void showShopView() {

//        shopView.setVisibility(View.VISIBLE);
//        shopViewShort.setVisibility(View.INVISIBLE);

        //位移动画
        mTranstationX1 = ValueAnimator.ofFloat(curTranslationX, curTranslationX - mWidth - ConvertUtils.dp2px(10 + 10));
        mTranstationX1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationX(value);
            }
        });
        mTranstationX1.setDuration(1000);

        mShowAnimatorSet = new AnimatorSet();
        mShowAnimatorSet.setInterpolator(new LinearInterpolator());
        mShowAnimatorSet.play(mTranstationX1);
        mShowAnimatorSet.start();
        mShowAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //开始倒计时5秒,自动隐藏
                handler.removeMessages(WHAT_SEND);
                handler.sendEmptyMessageDelayed(WHAT_SEND, 5000);
            }
        });

    }

    public void hideShopView() {

//        shopViewShort.setVisibility(View.INVISIBLE);
//        shopView.setVisibility(View.INVISIBLE);
        //位移动画
        mTranstationX2 = ValueAnimator.ofFloat(curTranslationX - mWidth - ConvertUtils.dp2px(10 + 10), curTranslationX);
        mTranstationX2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationX(value);
            }
        });
        mTranstationX2.setDuration(1000);

        mHideAnimatorSet = new AnimatorSet();
        mHideAnimatorSet.setInterpolator(new LinearInterpolator());
        mHideAnimatorSet.play(mTranstationX2);
        mHideAnimatorSet.start();
    }

    public void resetAnim(){
        if (mShowAnimatorSet != null && mShowAnimatorSet.isRunning()) {
            mShowAnimatorSet.cancel();
        }
        if (mHideAnimatorSet != null && mHideAnimatorSet.isRunning()) {
            mHideAnimatorSet.cancel();
        }
        shopView.setTranslationX(0);
    }


}
