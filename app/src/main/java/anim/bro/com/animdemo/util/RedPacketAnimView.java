package anim.bro.com.animdemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import anim.bro.com.animdemo.R;


public class RedPacketAnimView extends AppCompatImageView {

    private Pos pos;
    //    private Pos endPos;
    private View endView;
    //红包上方的引导文案
    private View guideFirstView;
    //红包下方的引导文案
    private View guideSecondView;
    //底部价格
    private View tvPriceBottomView;
    //底部价格
    private float curTranslationY;
    //红包每一次从头飞时,是否暂停过
    boolean isStoped = false;
    //第三种动画,价格是否出现过
    boolean isShownPrice = false;
    AnimatorSet animatorSetGuideFirst;
    AnimatorSet animatorSetGuide;
    AnimatorSet animatorNormal;
    AnimatorSet animatorSetSecondView;  //组合动画
    AnimatorSet animatorPriceView;  //价格动画1
    AnimatorSet animatorPriceView_2;  //价格动画2

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

        int[] outLocation = new int[2];
        if (pos == null || pos.x == 0 || pos.y == 0) {
            pos = new Pos();
            getLocationInWindow(outLocation);
            pos.x = outLocation[0];
            pos.y = outLocation[1];
        }
        endView.getLocationInWindow(outLocation);
        final Pos endPos = new Pos();
        endPos.x = outLocation[0];
        endPos.y = outLocation[1];

        setVisibility(View.VISIBLE);
        bringToFront();
        guideFirstView.setVisibility(VISIBLE);

        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);

        //---------------------------文字的缩小和渐变-------------------------
        guideFirstView.setPivotX(60);  // X方向中点
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

        //-------------------------再走弧形-------------------------------
        final AnimatorSet animatorSet = new AnimatorSet();
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
                setVisibility(View.INVISIBLE);
                endView.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                endView.startAnimation(shake);

            }

        });
        animatorSet.setDuration(1000);
//        animatorSet.start();

        //------------------------------动画集合--------------------------------
        animatorSetGuideFirst = new AnimatorSet();//组合动画
        animatorSetGuideFirst.setDuration(1000);  //动画时间
        animatorSetGuideFirst.setInterpolator(new AccelerateInterpolator());  //设置插值器
        animatorSetGuideFirst.play(scaleX).with(scaleY).with(alpha);  //同时执行
        animatorSetGuideFirst.start();  //启动动画
        animatorSetGuideFirst.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * 第二次引导登陆动效
     * 圆形精度条 --> 圆形精度条渐变缩小 --> 红包弧形变大飞 --> 中间停止 --> 下方气泡渐变弹开 ->  气泡停顿 --> 气泡渐变收起 --> 红包缩小飞
     */
    public void startGuideLoginAnim() {
        if (endView == null) return;

        int[] outLocation = new int[2];
        if (pos == null || pos.x == 0 || pos.y == 0) {
            pos = new Pos();
            getLocationInWindow(outLocation);
            pos.x = outLocation[0];
            pos.y = outLocation[1];
        }
        endView.getLocationInWindow(outLocation);
        final Pos endPos = new Pos();
        endPos.x = outLocation[0];
        endPos.y = outLocation[1];

        setVisibility(View.VISIBLE);
        guideSecondView.setVisibility(VISIBLE);
        guideSecondView.setAlpha(0f);
        guideSecondView.setScaleX(0.1f);
        guideSecondView.setScaleY(0.1f);
//        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);
        //----------------------------文案放大缩小--------------------------------
        //---------------------------文字的缩小和渐变-------------------------
        guideSecondView.setPivotX(guideSecondView.getWidth() / 2);  // X方向中点
        guideSecondView.setPivotY(0);   // Y方向底边
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(guideSecondView, "scaleX", 0f, 1.0f, 1.0f, 1.0f, 0);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(guideSecondView, "scaleY", 0f, 1.0f, 1.0f, 1.0f, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(guideSecondView, "alpha", 0f, 1.0f, 1.0f, 1.0f, 0);
        //------------------------------先走文字缩小--------------------------------
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
        valueAnimator1.setInterpolator(new LinearInterpolator());

        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                setTranslationY(value - pos.y);
            }
        });
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                if (fraction > 0.47 && !isStoped) {
                    pauseSet();

                    if (guideSecondView != null) {//动态改变位置
                        int locationArray[] = new int[2];
                        getLocationInWindow(locationArray);
                        int measuredHeight = getMeasuredHeight();
                        int measuredWidth = getMeasuredWidth();
//                        LogUtils.e(TAG, "locationArray:" + locationArray[0] + "    " + locationArray[1]);
                        guideSecondView.setX(locationArray[0] + measuredWidth / 2 + UIUtils.dip2px(2f) - guideSecondView.getMeasuredWidth() / 2);
                        guideSecondView.setY(locationArray[1] );
                    }

                    isStoped = !isStoped;
                    // 开始文案放大缩小的动画
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
                setVisibility(View.INVISIBLE);
                endView.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                endView.startAnimation(shake);
            }

        });

        ObjectAnimator scaleX_Red = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.3f,1f);
        ObjectAnimator scaleY_Red = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.3f,1f);
        animatorSetGuide.playTogether(scaleX_Red , scaleY_Red);
        animatorSetGuide.setDuration(1000);
        animatorSetGuide.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                resumeSet();
                LogUtils.i("resumeSet");
            }
        }, 4500);
    }


    /**
     * 登陆后红包动效
     * 圆形精度条 --> 圆形精度条渐变缩小 --> 红包弧形变大飞  --> 红包缩小飞
     * ---------------------------------------------------> 中间时下方价格弹出 --> 价格渐变放大 ->  价格渐变消失
     */
    public void startNormalAnim() {
        if (endView == null) return;

        int[] outLocation = new int[2];
        if (pos == null || pos.x == 0 || pos.y == 0) {
            pos = new Pos();
            getLocationInWindow(outLocation);
            pos.x = outLocation[0];
            pos.y = outLocation[1];
        }
        endView.getLocationInWindow(outLocation);
        final Pos endPos = new Pos();
        endPos.x = outLocation[0];
        endPos.y = outLocation[1];

        setVisibility(View.VISIBLE);
//        tvPriceBottomView.setVisibility(VISIBLE);
//        LogUtils.i("pos.x + pos.y: " + pos.x + ":" + pos.y + " -------endPos.x + endPos.y: " + endPos.x + ":" + +endPos.y);
        //----------------------------文案放大缩小--------------------------------
        //---------------------------文字的缩小和渐变-------------------------
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(tvPriceBottomView, "translationY", curTranslationY, curTranslationY - 300f, curTranslationY - 350f);
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(tvPriceBottomView, "alpha", 0.3f, 1.0f, 1.0f, 1.0f, 1.0f, 0f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvPriceBottomView, "scaleX", 0.3f, 1.0f, 1.0f, 1.0f, 1.0f, 0.3f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvPriceBottomView, "scaleY", 0.3f, 1.0f, 1.0f, 1.0f, 1.0f, 0.3f);
//
//        //------------------------------先走文字缩小--------------------------------
//        animatorPriceView = new AnimatorSet();
//        animatorPriceView.setDuration(1200);  //动画时间
//        animatorPriceView.setInterpolator(new AccelerateInterpolator());  //设置插值器
//        animatorPriceView.play(scaleX).with(scaleY).with(alpha).with(animator);  //同时执行

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
//                if (fraction > 0.3 && !isShownPrice) {
//                    isShownPrice = !isShownPrice;
//                    // TODO: 2018/9/19 开始文案放大缩小的动画
//                    animatorPriceView.start();  //启动动画
//                }
                int[] screenWidthAndHeight = UIUtils.getScreenWidthAndHeight();
                return (1 - fraction) * (1 - fraction) * pos.y + 2 * fraction * (1 - fraction) * screenWidthAndHeight[1] * 0.5f + fraction * fraction * endPos.y;
            }
        });

        //-------------------------弧形-------------------------------
        animatorNormal = new AnimatorSet();
        animatorNormal.playTogether(valueAnimator1, valueAnimator2);
        animatorNormal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setAlpha(1f);
                setScaleX(1);
                setScaleY(1);
                setTranslationX(0);//回到原点
                setTranslationY(0);//回到原点
//                tvPriceBottomView.setTranslationX(0);
//                tvPriceBottomView.setTranslationY(0);
//                tvPriceBottomView.setAlpha(0);
//                tvPriceBottomView.setScaleX(1);
//                tvPriceBottomView.setScaleY(1);
//                isShownPrice = false;
                setVisibility(View.INVISIBLE);
                endView.clearAnimation();
                Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                endView.startAnimation(shake);
            }

        });
        animatorNormal.setDuration(1000);
        animatorNormal.start();
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                resumeSet();
//                LogUtils.i("resumeSet");
//            }
//        }, 6000);

    }

    public void startMoneyTextAnim(String moneyText) {
        tvPriceBottomView.setVisibility(VISIBLE);
//        tvPriceBottomView.setAlpha(0.35f);
//        tvPriceBottomView.setScaleX(0.1f);
//        tvPriceBottomView.setScaleY(0.1f);

//        ((TextView) tvPriceBottomView).setText("+" + moneyText);
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvPriceBottomView, "translationY", curTranslationY, curTranslationY - 300f, curTranslationY - 350f);
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(tvPriceBottomView, "alpha", 0.35f, 1.0f, 1.0f, 1.0f, 1.0f, 0f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvPriceBottomView, "scaleX", 0.3f, 1.0f, 1.0f, 1.0f, 1.0f, 0f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvPriceBottomView, "scaleY", 0.3f, 1.0f, 1.0f, 1.0f, 1.0f, 0f);
//
//        //------------------------------先走文字缩小--------------------------------
//        animatorPriceView = new AnimatorSet();
//        animatorPriceView.setDuration(1200);  //动画时间
//        animatorPriceView.setInterpolator(new AccelerateInterpolator());  //设置插值器
//        animatorPriceView.play(scaleX).with(scaleY).with(alpha).with(animator);  //同时执行
//        animatorPriceView.start();


        final ValueAnimator animatorAlpha_1 = ValueAnimator.ofFloat(0.35f, 1f);
        animatorAlpha_1.setDuration(970);
        animatorAlpha_1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvPriceBottomView.setAlpha(value);
            }
        });

        final ValueAnimator animatorTranstationY_1 = ValueAnimator.ofFloat(curTranslationY, curTranslationY - 300);
        animatorTranstationY_1.setDuration(970);
        animatorTranstationY_1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvPriceBottomView.setTranslationY(value);
            }
        });

        final ValueAnimator animatorAlpha_2 = ValueAnimator.ofFloat(1f, 0f);
        animatorAlpha_2.setDuration(360);
        animatorAlpha_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvPriceBottomView.setAlpha(value);
            }
        });

        final ValueAnimator animatorTranstationY_2 = ValueAnimator.ofFloat(curTranslationY - 300f, curTranslationY - 320f);
        animatorTranstationY_2.setDuration(360);
        animatorTranstationY_2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                tvPriceBottomView.setTranslationY(value);
            }
        });

        animatorPriceView_2 = new AnimatorSet();
        animatorPriceView_2.setInterpolator(new DecelerateInterpolator());  //设置插值器
        animatorPriceView_2.play(animatorAlpha_2).with(animatorTranstationY_2);

        animatorPriceView = new AnimatorSet();
        animatorPriceView.setInterpolator(new DecelerateInterpolator());  //设置插值器
        animatorPriceView.play(animatorAlpha_1).with(animatorTranstationY_1);
        animatorPriceView.start();
        animatorPriceView.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animatorPriceView_2.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    public void pauseSet() {
        animatorSetGuide.pause();
    }

    public void resumeSet() {
        animatorSetGuide.resume();
    }


    public void setEndView(View endView) {
        this.endView = endView;
    }

    public void setGuideView(View guideFirstView, View guideSecondView, TextView tvPriceBottomView) {
        this.guideFirstView = guideFirstView;
        this.guideSecondView = guideSecondView;
        this.tvPriceBottomView = tvPriceBottomView;
        this.guideFirstView.setVisibility(INVISIBLE);
        this.guideSecondView.setVisibility(INVISIBLE);
        this.tvPriceBottomView.setVisibility(INVISIBLE);
//        this.guideFirstView.setAlpha(0);
//        this.guideSecondView.setAlpha(0);
//        this.tvPriceBottomView.setAlpha(0);
        if (this.tvPriceBottomView != null) {
            curTranslationY = this.tvPriceBottomView.getTranslationY();
        }
    }

//    public void setStartViewPosition() {
//        if (pos == null) {
//            int[] outLocation = new int[2];
//            pos = new Pos();
//            this.getLocationInWindow(outLocation);
//            LogUtils.i("首次 getLocationInWindow 坐标是: " + outLocation[0] + ":" + outLocation[1]);
//            pos.x = outLocation[0];
//            pos.y = outLocation[1];
//        }
//        if (endPos == null && endView != null) {
//            int[] outLocation = new int[2];
//            endView.getLocationInWindow(outLocation);
//            endPos = new Pos();
//            endPos.x = outLocation[0];
//            endPos.y = outLocation[1];
//        }
//    }

    /**
     * 取消动画
     */
    public void cancleAnim() {
        if (animatorSetGuideFirst != null) {
            animatorSetGuideFirst.cancel();
            guideFirstView.setScaleX(0);
            guideFirstView.setScaleY(0);
            guideFirstView.setAlpha(0);
        }
        if (animatorSetGuide != null) {
            animatorSetGuide.cancel();
            if (animatorSetSecondView != null) {
                animatorSetSecondView.end();
            }
        }
        if (animatorNormal != null) {
            animatorNormal.cancel();
            if (animatorPriceView != null) {
                animatorPriceView.end();
            }
        }

        if (endView != null) {
            endView.clearAnimation();
        }

        //红包出现
        setVisibility(VISIBLE);
        setAlpha(1f);
        setScaleX(1);
        setScaleY(1);
        setTranslationX(0);//回到原点
        setTranslationY(0);//回到原点
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
