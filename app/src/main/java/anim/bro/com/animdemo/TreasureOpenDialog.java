package anim.bro.com.animdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import anim.bro.com.animdemo.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2019/3/8 16:48.
 */
public class TreasureOpenDialog extends Dialog {

    @BindView(R.id.iv_box)
    ImageView ivBox;
    @BindView(R.id.iv_box_png)
    ImageView ivBoxPng;
    @BindView(R.id.iv_box_open_new)
    ImageView iv_box_open_new;
    @BindView(R.id.iv_box_open_new_text)
    ImageView iv_box_open_new_text;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.ll_treasure_amount)
    LinearLayout ll_treasure_amount;
    @BindView(R.id.scale_x)
    Button scale_x;
    private AnimatorSet treasureAnim;

    private Context mContext;
    private View layout;
    private Timer t;

    public TreasureOpenDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.treasure_anim.dialog_treasure_open);
//        ButterKnife.bind(this);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.dialog_treasure_open, null);
        setContentView(layout);
        ButterKnife.bind(this, layout);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 0;
        lp.verticalMargin = 0;
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.CENTER);
//        setCanceledOnTouchOutside(false);
//        setCancelable(false);

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


//    @Override
//    protected void init(View view) {
//        getDialog().setCancelable(false);
//        getDialog().setCanceledOnTouchOutside(false);
//    }

    //    @Override
//    protected void handleArguments(Bundle bundle) {
//
//
//    }
    long scaleTime = 300l;

    public void setData() {

        startBoxSmallAnim();

        ll_treasure_amount.setAlpha(0f);
        tvAmount.setText(108 + "元宝");

        //显示放大动画
        ivBox.setVisibility(View.INVISIBLE);
        ivBoxPng.setVisibility(View.VISIBLE);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.2f, 1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.2f, 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivBoxPng, scaleX, scaleY);
        animator.setDuration(scaleTime);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivBoxPng.setVisibility(View.INVISIBLE);
                ivBox.setVisibility(View.VISIBLE);
                startBoxAnim();
                startMoneyTextAnim();

//                if (t == null) {
//                    t = new Timer();
//                }
//                t.schedule(new TimerTask() {
//                    public void run() {
//                        dismiss();
//                        t.cancel();
//                    }
//                }, 1200 + 800 + 2500 + scaleTime);
            }
        });


    }

    private void startBoxBigAnim() {
        AnimationDrawable animationDrawable = (AnimationDrawable) ivBox.getBackground();
        animationDrawable.start();
    }

    private AnimationDrawable animationDrawable;

    private void startBoxAnim() {
        AnimationDrawable animationDrawable = (AnimationDrawable) ivBox.getBackground();
        animationDrawable.stop();
        animationDrawable.start();
    }

    private void startBoxSmallAnim() {
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_box_open_new.getBackground();
        animationDrawable.stop();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //此处进行下一步
                iv_box_open_new.setBackgroundResource(R.drawable.treasure_anim_new_half);
                AnimationDrawable animationDrawable1 = (AnimationDrawable) iv_box_open_new.getBackground();
                animationDrawable1.stop();
                animationDrawable1.start();
            }
        }, duration);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //此处进行下一步
                AnimationDrawable animationDrawable = (AnimationDrawable) iv_box_open_new_text.getBackground();
                animationDrawable.stop();
                animationDrawable.start();
            }
        }, duration / 2);

    }

//    private void startBoxSmallAnim() {
//        AnimationDrawable animationDrawable = (AnimationDrawable) iv_box_open.getBackground();
//        animationDrawable.stop();
//        animationDrawable.start();
//        int duration = 0;
//        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
//            duration += animationDrawable.getDuration(i);
//        }
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                //此处进行下一步
//                iv_box_open.setBackgroundResource(R.drawable.treasure_anim_half);
//                AnimationDrawable animationDrawable = (AnimationDrawable) iv_box_open.getBackground();
//                animationDrawable.stop();
//                animationDrawable.start();
//            }
//        }, duration);
//
//    }

    public void startMoneyTextAnim() {

        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0, UIUtils.dip2px(-200));
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1.0f, 1.0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 2.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ll_treasure_amount, translationY, alpha, scaleX, scaleY);

        PropertyValuesHolder scaleX_After = PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1.8f, 2.0f, 1.8f, 2.0f, 1.8f, 2.0f);
        PropertyValuesHolder scaleY_After = PropertyValuesHolder.ofFloat("scaleY", 2.0f, 1.8f, 2.0f, 1.8f, 2.0f, 1.8f, 2.0f);
//        PropertyValuesHolder alpha_After = PropertyValuesHolder.ofFloat("alpha", 1.0f, 1.0f, 1.0f, 1.0f, 0f);
        ObjectAnimator animator_After = ObjectAnimator.ofPropertyValuesHolder(ll_treasure_amount, scaleX_After, scaleY_After);
        //------------------------------先走文字缩小--------------------------------
        if (treasureAnim != null) {
            treasureAnim.cancel();
        }
        if (treasureAnim == null) {
            treasureAnim = new AnimatorSet();
        }
        treasureAnim.setDuration(800);  //动画时间
        treasureAnim.setStartDelay(2500);
        treasureAnim.play(animator).before(animator_After);  //同时执行
        treasureAnim.start();
    }


    boolean isX = false;

    @OnClick(R.id.scale_x)
    public void onViewClicked() {
        if (!isX) {
            iv_box_open_new.setScaleX(-1);
        } else {
            iv_box_open_new.setScaleX(1);
        }
        isX = !isX;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startBoxAnim();
            }
        }, 500);

    }
}
