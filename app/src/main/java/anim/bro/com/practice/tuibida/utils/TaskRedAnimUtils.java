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
public class TaskRedAnimUtils {

    private int mHeightExpand;//伸展高度

    private int mWidthExpand;//伸展宽度

    private View hideView, down;//需要展开隐藏的布局，开关控件

    private RotateAnimation animation;//旋转动画

    int togStyle; //true 表示从0 - 180 旋转  false 表示从 180 - 0
    public final static int tog_0_180 = 0;
    public final static int tog_180_0 = 1;
    public final static int tog_no = 2;

    private TaskRedAnimUtils() {
    }

    private static class SingletonInstance {
        private static final TaskRedAnimUtils INSTANCE = new TaskRedAnimUtils();
    }

    public static TaskRedAnimUtils getInstance() {
        return SingletonInstance.INSTANCE;
    }

    /**
     * 构造器(可根据自己需要修改传参)
     *
     * @param context      上下文
     * @param hideView     需要隐藏或显示的布局view
     * @param down         按钮开关的view
     * @param heightExpand 布局展开的高度(根据实际需要传)
     * @param togStyle     旋转方式(3种)
     */

    public TaskRedAnimUtils init(Context context, View hideView, View down, int heightExpand, int widthExpand, int togStyle) {
        this.hideView = hideView;
        this.down = down;
        float mDensity = context.getResources().getDisplayMetrics().density;
        mHeightExpand = (int) (mDensity * heightExpand + 0.5);//伸展高度
        mWidthExpand = (int) (mDensity * widthExpand + 0.5);//伸展高度
        this.togStyle = togStyle;
        return this;
    }

    /**
     * 开关
     */
    public TaskRedAnimUtils toggle() {
        startAnimation();
        openAnim(hideView);//布局铺开
        return this;
    }

    /**
     * 开关
     */
    public TaskRedAnimUtils startChangeSize() {
        openAnim(hideView);//布局铺开
        return this;
    }

    /**
     * 开关旋转动画
     */
    private void startAnimation() {
        if (down.getVisibility() != View.VISIBLE) {
            return;
        }
        if (togStyle == tog_no) {
            return;
        } else if (togStyle == tog_0_180) {
            animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        animation.setDuration(100);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        down.startAnimation(animation);
    }

    private void openAnim(View view) {
        ValueAnimator animatorHeight = createDropAnimator(view, view.getHeight(), mHeightExpand, true);
        ValueAnimator animatorWidth = createDropAnimator(view, view.getWidth(), mWidthExpand, false);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorHeight).with(animatorWidth);
        animatorSet.start();

//        view.setPivotX(0);  // X 0
//        view.setPivotY(0);   // Y 0
//        float valueA = new BigDecimal(mHeightExpand).divide(new BigDecimal(view.getHeight())).floatValue();
//        float valueB = new BigDecimal(mWidthExpand).divide(new BigDecimal(view.getWidth())).floatValue();
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, valueA);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, valueB);
//        AnimatorSet animatorSet1 = new AnimatorSet();
//        animatorSet1.setDuration(1000);  //动画时间
//        animatorSet1.play(scaleX).with(scaleY);
//        animatorSet1.start();


    }

//    private void closeAnimate(final View view) {
//        ValueAnimator animatorHeight = createDropAnimator(view, view.getHeight(), mHeightExpand,true);
//        ValueAnimator animatorWidth = createDropAnimator(view,  view.getWidth(), mWidthExpand  , false);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(animatorHeight).with(animatorWidth);
//        animatorSet.start();
////        animator.start();
//    }

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
