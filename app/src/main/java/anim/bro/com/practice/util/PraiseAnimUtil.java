package anim.bro.com.practice.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import anim.bro.com.practice.R;


public class PraiseAnimUtil {
    Activity mActivity;
    ImageView ivPraise, ivHeartLeftTop, ivHeartLeftBottom, ivHeartRightTop, ivHeartRightBottom;

    public void setGuideView(Activity activity, ImageView ivPraise, ImageView ivHeartLeftTop,
                             ImageView ivHeartLeftBottom, ImageView ivHeartRightTop,
                             ImageView ivHeartRightBottom) {
        mActivity = activity;
        this.ivPraise = ivPraise;
        this.ivHeartLeftTop = ivHeartLeftTop;
        this.ivHeartLeftBottom = ivHeartLeftBottom;
        this.ivHeartRightTop = ivHeartRightTop;
        this.ivHeartRightBottom = ivHeartRightBottom;
    }

    public void setPraise() {
        //白色星星先消失
        mActivity.getResources().getDrawable(R.drawable.video_not_praise);
        ObjectAnimator scaleX_UnPraise = ObjectAnimator.ofFloat(ivPraise, "scaleX", 1f, 0);
        ObjectAnimator scaleY_UnPraise = ObjectAnimator.ofFloat(ivPraise, "scaleY", 1f, 0);
        AnimatorSet animatorSetScale_1 = new AnimatorSet();
        animatorSetScale_1.setDuration(200);  //动画时间
        animatorSetScale_1.setInterpolator(new LinearInterpolator());  //设置插值器
        animatorSetScale_1.play(scaleX_UnPraise).with(scaleY_UnPraise);  //同时执行
        animatorSetScale_1.start();

        //红色星星由小变大  小星星渐变出来
        ObjectAnimator scaleX_Praise = ObjectAnimator.ofFloat(ivPraise, "scaleX", 0, 1f);
        ObjectAnimator scaleY_Praise = ObjectAnimator.ofFloat(ivPraise, "scaleY", 0, 1f);

        ObjectAnimator scaleX_Praise_l_t = ObjectAnimator.ofFloat(ivHeartLeftTop, "scaleX", 0, 1f);
        ObjectAnimator scaleY_Praise_l_t = ObjectAnimator.ofFloat(ivHeartLeftTop, "scaleY", 0, 1f);
        ObjectAnimator alpha_Praise_l_t = ObjectAnimator.ofFloat(ivHeartLeftTop, "alpha", 0, 1f);

        ObjectAnimator scaleX_Praise_l_b = ObjectAnimator.ofFloat(ivHeartLeftBottom, "scaleX", 0, 1f);
        ObjectAnimator scaleY_Praise_l_b = ObjectAnimator.ofFloat(ivHeartLeftBottom, "scaleY", 0, 1f);
        ObjectAnimator alpha_Praise_l_b = ObjectAnimator.ofFloat(ivHeartLeftBottom, "alpha", 0, 1f);

        ObjectAnimator scaleX_Praise_r_t = ObjectAnimator.ofFloat(ivHeartRightTop, "scaleX", 0, 1f);
        ObjectAnimator scaleY_Praise_r_t = ObjectAnimator.ofFloat(ivHeartRightTop, "scaleY", 0, 1f);
        ObjectAnimator alpha_Praise_r_t = ObjectAnimator.ofFloat(ivHeartRightTop, "alpha", 0, 1f);

        ObjectAnimator scaleX_Praise_r_b = ObjectAnimator.ofFloat(ivHeartRightBottom, "scaleX", 0, 1f);
        ObjectAnimator scaleY_Praise_r_b = ObjectAnimator.ofFloat(ivHeartRightBottom, "scaleY", 0, 1f);
        ObjectAnimator alpha_Praise_r_b = ObjectAnimator.ofFloat(ivHeartRightBottom, "alpha", 0, 1f);

        final AnimatorSet animatorSetScale_2 = new AnimatorSet();
        animatorSetScale_2.setDuration(400);  //动画时间
        animatorSetScale_2.setInterpolator(new LinearInterpolator());  //设置插值器
        animatorSetScale_2.play(scaleX_Praise).with(scaleY_Praise)
                .with(scaleX_Praise_l_t).with(scaleY_Praise_l_t).with(alpha_Praise_l_t)
                .with(scaleX_Praise_l_b).with(scaleY_Praise_l_b).with(alpha_Praise_l_b)  //同时执行
                .with(scaleX_Praise_r_t).with(scaleY_Praise_r_t).with(alpha_Praise_r_t)  //同时执行
                .with(scaleX_Praise_r_b).with(scaleY_Praise_r_b).with(alpha_Praise_r_b);  //同时执行

        //四个小星星消失
        ObjectAnimator scaleX_Praise_l_t_2 = ObjectAnimator.ofFloat(ivHeartLeftTop, "scaleX", 1f, 0);
        ObjectAnimator scaleY_Praise_l_t_2 = ObjectAnimator.ofFloat(ivHeartLeftTop, "scaleY", 1f, 0);
        ObjectAnimator alpha_Praise_l_t_2 = ObjectAnimator.ofFloat(ivHeartLeftTop, "alpha", 1f, 0);

        ObjectAnimator scaleX_Praise_l_b_2 = ObjectAnimator.ofFloat(ivHeartLeftBottom, "scaleX", 1f, 0);
        ObjectAnimator scaleY_Praise_l_b_2 = ObjectAnimator.ofFloat(ivHeartLeftBottom, "scaleY", 1f, 0);
        ObjectAnimator alpha_Praise_l_b_2 = ObjectAnimator.ofFloat(ivHeartLeftBottom, "alpha", 1f, 0);

        ObjectAnimator scaleX_Praise_r_t_2 = ObjectAnimator.ofFloat(ivHeartRightTop, "scaleX", 1f, 0);
        ObjectAnimator scaleY_Praise_r_t_2 = ObjectAnimator.ofFloat(ivHeartRightTop, "scaleY", 1f, 0);
        ObjectAnimator alpha_Praise_r_t_2 = ObjectAnimator.ofFloat(ivHeartRightTop, "alpha", 1f, 0);

        ObjectAnimator scaleX_Praise_r_b_2 = ObjectAnimator.ofFloat(ivHeartRightBottom, "scaleX", 1f, 0);
        ObjectAnimator scaleY_Praise_r_b_2 = ObjectAnimator.ofFloat(ivHeartRightBottom, "scaleY", 1f, 0);
        ObjectAnimator alpha_Praise_r_b_2 = ObjectAnimator.ofFloat(ivHeartRightBottom, "alpha", 1f, 0);

        final AnimatorSet animatorSetScale_3 = new AnimatorSet();
        animatorSetScale_3.setDuration(200);  //动画时间
        animatorSetScale_3.setInterpolator(new LinearInterpolator());  //设置插值器
        animatorSetScale_3.play(scaleX_Praise_l_t_2).with(scaleY_Praise_l_t_2).with(alpha_Praise_l_t_2)
                .with(scaleX_Praise_l_b_2).with(scaleY_Praise_l_b_2).with(alpha_Praise_l_b_2)  //同时执行
                .with(scaleX_Praise_r_t_2).with(scaleY_Praise_r_t_2).with(alpha_Praise_r_t_2)  //同时执行
                .with(scaleX_Praise_r_b_2).with(scaleY_Praise_r_b_2).with(alpha_Praise_r_b_2);  //同时执行

        animatorSetScale_1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ivPraise.setScaleX(0);
                ivPraise.setScaleY(0);
                ivPraise.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.video_praise));
                ivHeartLeftTop.setVisibility(View.VISIBLE);
                ivHeartLeftBottom.setVisibility(View.VISIBLE);
                ivHeartRightTop.setVisibility(View.VISIBLE);
                ivHeartRightBottom.setVisibility(View.VISIBLE);
                animatorSetScale_2.start();
                animatorSetScale_2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animatorSetScale_3.start();
                        animatorSetScale_3.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                ivHeartLeftTop.setVisibility(View.INVISIBLE);
                                ivHeartLeftBottom.setVisibility(View.INVISIBLE);
                                ivHeartRightTop.setVisibility(View.INVISIBLE);
                                ivHeartRightBottom.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });


    }

    public void setUnPraise() {
        ivPraise.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.video_not_praise));

    }


}
