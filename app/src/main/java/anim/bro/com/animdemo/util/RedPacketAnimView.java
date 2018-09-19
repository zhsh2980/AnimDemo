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


public class RedPacketAnimView extends AppCompatImageView {

    private Pos pos;
    private Pos endPos;
    private View endView;
    //红包上方的引导文案
    private View guideFirstView;
    //红包下方的引导文案
    private View guideSecondView;
    //红包每一次从头飞时,是否暂停过
    boolean isStoped = false;
    AnimatorSet animatorSetGuide;

    public RedPacketAnimView(Context context) {
        this(context, null);
    }

    public RedPacketAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RedPacketAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 首次引导登陆动效
     * 气泡缩小 + 圆形精度条 --> 圆形精度条缩小 --> 红包弧形飞 + 先变大后变小
     */
    public void startGuideFirstAnim() {

        if (endView == null) return;
        if (guideFirstView == null) return;
        guideFirstView.setVisibility(VISIBLE);
        setVisibility(View.VISIBLE);

        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);

        //---------------------------文字的缩小和渐变-------------------------
        guideFirstView.setPivotX(0);  // X方向中点
        guideFirstView.setPivotY(guideFirstView.getHeight());   // Y方向底边
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(guideFirstView, "scaleX", 1.0f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(guideFirstView, "scaleY", 1.0f, 0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(guideFirstView, "alpha", 1f, 0.1f);

        //----------------------------弧形--------------------------------
        final ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float scale;
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

    /**
     * 第二次引导登陆动效
     * 圆形精度条 --> 圆形精度条缩小 --> 红包弧形变大飞 --> 中间停止 --> 下方气泡渐变弹开 ->  气泡停顿 --> 气泡渐变收起 --> 红包缩小飞
     */
    public void startGuideLoginAnim() {
        if (endView == null) return;

        setVisibility(View.VISIBLE);
//        guideSecondView.setAlpha(0);
//        guideSecondView.setVisibility(VISIBLE);
//        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);
        //----------------------------文案放大缩小--------------------------------
        //---------------------------文字的缩小和渐变-------------------------
        guideSecondView.setPivotX(guideSecondView.getWidth() / 2);  // X方向中点
        guideSecondView.setPivotY(0);   // Y方向底边
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(guideSecondView, "scaleX", 0f, 1.0f, 1.0f, 1.0f, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(guideSecondView, "scaleY", 0f, 1.0f, 1.0f, 1.0f, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(guideSecondView, "alpha", 0f, 1.0f, 1.0f, 1.0f, 0);
        //------------------------------先走文字缩小--------------------------------
        final AnimatorSet animatorSetSecondView;  //组合动画
        animatorSetSecondView = new AnimatorSet();
        animatorSetSecondView.setDuration(4000);  //动画时间
        animatorSetSecondView.setInterpolator(new AccelerateInterpolator());  //设置插值器
        animatorSetSecondView.play(scaleX).with(scaleY).with(alpha);  //同时执行

        //----------------------------弧形--------------------------------
        final ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0, 1);
        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);

        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float scale;
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
            }
        });
        valueAnimator1.setInterpolator(new AccelerateInterpolator());

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
                if (fraction > 0.47 && !isStoped) {
                    pauseSet();
                    isStoped = !isStoped;
                    // TODO: 2018/9/19 开始文案放大缩小的动画
                    animatorSetSecondView.start();  //启动动画
                }
                int[] screenWidthAndHeight = UIUtils.getScreenWidthAndHeight();
                return (1 - fraction) * (1 - fraction) * pos.y + 2 * fraction * (1 - fraction) * screenWidthAndHeight[1] * 0.5f + fraction * fraction * endPos.y;
            }
        });


        //-------------------------弧形-------------------------------
        animatorSetGuide = new AnimatorSet();
        animatorSetGuide.playTogether(valueAnimator1, valueAnimator2);
        animatorSetGuide.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setAlpha(1f);
                setScaleX(1);
                setScaleY(1);
                setTranslationX(0);//回到原点
                setTranslationY(0);//回到原点
                isStoped = false;
//                        setVisibility(View.INVISIBLE);
                endView.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                endView.startAnimation(shake);
            }

        });
        animatorSetGuide.setDuration(1000);
        animatorSetGuide.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                resumeSet();
                LogUtils.i("resumeSet");
            }
        }, 6000);
    }

//    public void


    public void pauseSet() {
        animatorSetGuide.pause();
    }

    public void resumeSet() {
        animatorSetGuide.resume();
    }


    public void setEndView(View endView) {
        this.endView = endView;
    }

    public void setGuideView(View guideFirstView, View guideSecondView) {
        this.guideFirstView = guideFirstView;
        this.guideSecondView = guideSecondView;
        guideFirstView.setVisibility(GONE);
        guideSecondView.setAlpha(0);
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
        if (endPos == null && endView != null) {
            int[] outLocation = new int[2];
            endView.getLocationInWindow(outLocation);
            endPos = new Pos();
            endPos.x = outLocation[0];
            endPos.y = outLocation[1];
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
