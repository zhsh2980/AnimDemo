package anim.bro.com.animdemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;


public class HideShopAnimUtil {
    Activity mActivity;
    ImageView shopView, shopViewShort;
    private final String TAG = "HideShopAnimUtil";

    private int mHeight;
    private int mHeightShort;
    // Y轴坐标
    private float curTranslationX;
    private float curTranslationXShort;
    private int mWidth;
    private int mWidthShort;

    private ValueAnimator mTranstationX;
    private ValueAnimator mTranstationXShort;

    public void setGuideView(Activity activity, final ImageView shopView, final ImageView shopViewShort) {
        mActivity = activity;
        this.shopView = shopView;
        this.shopViewShort = shopViewShort;
        shopView.setVisibility(View.INVISIBLE);
        shopViewShort.setVisibility(View.INVISIBLE);

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

        shopView.setVisibility(View.VISIBLE);
        //位移动画
        mTranstationX = ValueAnimator.ofFloat(curTranslationX + mWidth + ConvertUtils.dp2px(15 + 15), curTranslationX);
        mTranstationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationY(value);
            }
        });
        mTranstationX.setDuration(1000);
        mTranstationX.setInterpolator(new LinearInterpolator());
        mTranstationX.start();
        mTranstationX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                shopViewShort.setVisibility(View.VISIBLE);
            }
        });

    }

    public void hideShopView() {

        shopViewShort.setVisibility(View.INVISIBLE);
        shopView.setVisibility(View.VISIBLE);
        //位移动画
        mTranstationX = ValueAnimator.ofFloat(curTranslationX, curTranslationX + mWidth + ConvertUtils.dp2px(15 + 15));
        mTranstationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                shopView.setTranslationY(value);
            }
        });
        mTranstationX.setDuration(1000);
        mTranstationX.setInterpolator(new LinearInterpolator());
        mTranstationX.start();
        mTranstationX.addListener(new AnimatorListenerAdapter() {
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


}
