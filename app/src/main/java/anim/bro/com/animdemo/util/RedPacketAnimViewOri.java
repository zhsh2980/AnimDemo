package anim.bro.com.animdemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.blankj.utilcode.util.LogUtils;

import anim.bro.com.animdemo.R;


public class RedPacketAnimViewOri extends AppCompatImageView {

    private Pos pos;
    private View endView;
    private View guideView;

    public RedPacketAnimViewOri(Context context) {
        this(context, null);
    }

    public RedPacketAnimViewOri(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RedPacketAnimViewOri(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startUnloginFirstAnim() {

        if (endView == null) return;
        if (guideView == null) return;

        int[] outLocation = new int[2];
//        if (pos == null || (pos.x == 0 && pos.y == 0)) {
//            pos = new Pos();
//            this.getLocationInWindow(outLocation);
//            pos.x = outLocation[0];
//            pos.y = outLocation[1];
//        }
        setVisibility(View.VISIBLE);

        endView.getLocationInWindow(outLocation);
        final Pos endPos = new Pos();
        endPos.x = outLocation[0];
        endPos.y = outLocation[1];

        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);


        //---------------------------文字的缩小和渐变-------------------------
        guideView.setPivotX(0);  // X方向中点
        guideView.setPivotY(guideView.getHeight());   // Y方向底边
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(guideView, "scaleX", 1.0f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(guideView, "scaleY", 1.0f, 0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(guideView, "alpha", 1f, 0.1f);

        //----------------------------弧形--------------------------------
        final ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float alpha;
                float scale;
                if (value < 0.2) {
                    alpha = (float) (0.4 + 0.6 * (value / 0.2));
                } else if (value < 0.8) {
                    alpha = 1;
                } else {
                    alpha = (float) ((1 - value) / 0.2);
                }
                if (value < 0.4) {
                    scale = 1 + (value / 2);
                } else if (value < 0.6) {
                    scale = 1.2f;
                } else {
                    scale = (float) (1.2 - (value - 0.6) / 2);
                }
                setTranslationX(value * (endPos.x - pos.x));
                setScaleX(scale);
                setScaleY(scale);
//                setAlpha(alpha);
            }
        });
        valueAnimator1.setInterpolator(new AccelerateInterpolator());


        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                setTranslationY(value - pos.y);
            }
        });
        valueAnimator2.setInterpolator(new AccelerateInterpolator());
        valueAnimator2.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                int[] screenWidthAndHeight = UIUtils.getScreenWidthAndHeight();
                return (1 - fraction) * (1 - fraction) * pos.y + 2 * fraction * (1 - fraction) * screenWidthAndHeight[1] * 0.5f + fraction * fraction * endPos.y;
            }
        });

//        final ObjectAnimator scaleX_red = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 1.5f, 1.0f);
//        final ObjectAnimator scaleY_red = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 1.5f, 1.0f);
//        scaleX_red.setInterpolator(new DecelerateAccelerateInterpolator());
//        scaleY_red.setInterpolator(new DecelerateAccelerateInterpolator());
        //-------------------------再走弧形-------------------------------
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator1, valueAnimator2);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                        setPivotX(0);//旋转的时候设置中心点
//                        setPivotY(0);//旋转的时候设置中心点
                setAlpha(1f);
                setScaleX(1);
                setScaleY(1);
                setTranslationX(0);//回到原点
                setTranslationY(0);//回到原点
                LogUtils.i("动画结束之后坐标是: " + "pos.x + pos.y: " + pos.x + ":" + pos.y);
//                        setVisibility(View.INVISIBLE);
                endView.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
//                        shake.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
////                                LogUtils.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画开始"));
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
////                                LogUtils.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画结束"));
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
                endView.startAnimation(shake);

            }
        });
        animatorSet.setDuration(1000);
//        animatorSet.start();

        //------------------------------动画集合--------------------------------


        //------------------------------先走文字缩小--------------------------------
        AnimatorSet animatorSet_textContent = new AnimatorSet();  //组合动画
        animatorSet_textContent.setDuration(1000);  //动画时间
        animatorSet_textContent.setInterpolator(new AccelerateInterpolator());  //设置插值器
        animatorSet_textContent.play(scaleX).with(scaleY).with(alpha).before(animatorSet);  //同时执行
        animatorSet_textContent.start();  //启动动画
    }

    public void setEndView(View endView) {
        this.endView = endView;
    }

    public void setGuideView(View guideView) {
        this.guideView = guideView;
    }

    public void setStartViewPosition() {
        if (pos == null) {
            int[] outLocation = new int[2];
            pos = new Pos();
            this.getLocationInWindow(outLocation);
            LogUtils.i("首次 getLocationInWindow 坐标是: " + outLocation[0] + ":" + outLocation[1]);
            pos.x = outLocation[0];
            pos.y = outLocation[1];
        }
    }


    public class Pos {
        public float x;
        public float y;
        public float alpha;
        public float scale;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
