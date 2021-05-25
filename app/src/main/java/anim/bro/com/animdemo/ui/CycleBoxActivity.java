package anim.bro.com.animdemo.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import anim.bro.com.animdemo.Interface.Action0;
import anim.bro.com.animdemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CycleBoxActivity extends AppCompatActivity {

    @BindView(R.id.btn_anim_start)
    Button btnAnimStart;
    @BindView(R.id.tv_amount_cy)
    TextView tv_amount_cy;
    @BindView(R.id.frame_root_amount_cy)
    FrameLayout frame_root_amount_cy;
    @BindView(R.id.iv_box_open_new_2)
    ImageView iv_box_open_new_2;
    @BindView(R.id.frame_box_open_new_2)
    FrameLayout frame_box_open_new_2;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_box);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_anim_start)
    public void onViewClicked() {

        frame_box_open_new_2.setVisibility(View.VISIBLE);
        tv_amount_cy.setText("+" + random.nextInt(100) + 30);
        startTextAnim(frame_root_amount_cy);

        startImageAnim(iv_box_open_new_2, new Action0() {
            @Override
            public void call() {
//                yuanBaoProgress.setVisible(View.VISIBLE);
                frame_box_open_new_2.setVisibility(View.GONE);
            }
        });
    }

    Handler animEndHanler = new Handler();

    private void startImageAnim(ImageView imageView, final Action0 action) {
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.stop();
        animationDrawable.start();
        if (action != null) {
            int duration = 0;
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                duration += animationDrawable.getDuration(i);
            }
            animEndHanler.postDelayed(new Runnable() {
                public void run() {
                    //此处调用第二个动画播放方法
                    action.call();
                }
            }, duration);
        }
    }

    AnimatorSet animatorCome;  //组合动画
    AnimatorSet animatorGo;  //组合动画

    //特殊的红包数值 UI
    private void startTextAnim(final FrameLayout frame_root_amount_cy) {
        //动画一共 3300 毫秒
        // 延时 800 毫秒
        // 渐变 0 - 100 + 位移 -100 到 0(350 毫秒)
        // 停留 500 毫秒(1400 毫秒)
        // 渐变 + 消失(350 毫秒)
        frame_root_amount_cy.setVisibility(View.VISIBLE);
        frame_root_amount_cy.setAlpha(0f);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(frame_root_amount_cy, "translationY", -25f, 0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(frame_root_amount_cy, "alpha", 0f, 1.0f);

        animatorCome = new AnimatorSet();
        animatorCome.setDuration(350);  //动画时间
        animatorCome.setInterpolator(new AccelerateDecelerateInterpolator());  //设置插值器
        animatorCome.setStartDelay(700);
        animatorCome.play(alpha).with(scaleY);  //同时执行
        animatorCome.start();
        animatorCome.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator scaleY_Go = ObjectAnimator.ofFloat(frame_root_amount_cy, "translationY", 0f, -25f);
                ObjectAnimator alpha_Go = ObjectAnimator.ofFloat(frame_root_amount_cy, "alpha", 1.0f, 0f);

                animatorGo = new AnimatorSet();
                animatorGo.setDuration(350);  //动画时间
                animatorGo.setInterpolator(new AccelerateDecelerateInterpolator());  //设置插值器
                animatorGo.setStartDelay(1400);
                animatorGo.play(alpha_Go).with(scaleY_Go);  //同时执行
                animatorGo.start();
                animatorGo.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        frame_root_amount_cy.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }


}
