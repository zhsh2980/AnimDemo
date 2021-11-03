package anim.bro.com.practice.tuibida.utils;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by zhangshan on 2018/11/2 10:58.
 */
public class HiddenAnimUtils {

    private int mHeightExpand;//伸展高度

    private int mWidthExpand;//伸展宽度

    private View hideView, down;//需要展开隐藏的布局，开关控件

    private RotateAnimation animation;//旋转动画

    boolean mToExpand;

    /**
     * 构造器(可根据自己需要修改传参)
     *
     * @param context      上下文
     * @param hideView     需要隐藏或显示的布局view
     * @param down         按钮开关的view
     * @param heightExpand 布局展开的高度(根据实际需要传)
     * @param toExpand     是否要展开
     */
    public static HiddenAnimUtils newInstance(Context context, View hideView, View down, int heightExpand, int widthExpand, boolean toExpand) {
        return new HiddenAnimUtils(context, hideView, down, heightExpand, widthExpand, toExpand);
    }

    private HiddenAnimUtils(Context context, View hideView, View down, int heightExpand, int widthExpand, boolean toExpand) {
        this.hideView = hideView;
        this.down = down;
        float mDensity = context.getResources().getDisplayMetrics().density;
        mHeightExpand = (int) (mDensity * heightExpand + 0.5);//伸展高度
        mWidthExpand = (int) (mDensity * widthExpand + 0.5);//伸展高度
        mToExpand = toExpand;
    }

    /**
     * 开关
     */
    public void toggle() {
        startAnimation();
        if (mToExpand) {
            openAnim(hideView);//布局铺开
        } else {
            closeAnimate(hideView);//布局隐藏
        }
    }

    /**
     * 开关旋转动画
     */
    private void startAnimation() {
        if (mToExpand) {
            animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        animation.setDuration(30);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        down.startAnimation(animation);
    }

    private void openAnim(View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator animatorHeight = createDropAnimator(view, view.getHeight(), mHeightExpand, true);
        ValueAnimator animatorWidth = createDropAnimator(view,  view.getWidth(), mWidthExpand  , false);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorHeight).with(animatorWidth);
        animatorSet.start();
    }

    private void closeAnimate(final View view) {
        ValueAnimator animatorHeight = createDropAnimator(view, view.getHeight(), mHeightExpand,true);
        ValueAnimator animatorWidth = createDropAnimator(view,  view.getWidth(), mWidthExpand  , false);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorHeight).with(animatorWidth);
        animatorSet.start();
//        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end, final boolean isHeight) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (isHeight) {
                    layoutParams.height = value;
                } else {
                    layoutParams.width = value;
                }
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
}
