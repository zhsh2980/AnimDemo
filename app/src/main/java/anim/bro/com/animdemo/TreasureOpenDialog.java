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
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import anim.bro.com.animdemo.util.GifLoadUtil;
import anim.bro.com.animdemo.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangshan on 2019/3/8 16:48.
 */
public class TreasureOpenDialog extends Dialog {

    @BindView(R.id.iv_box)
    ImageView ivBox;
    @BindView(R.id.iv_box_open)
    ImageView iv_box_open;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.ll_treasure_amount)
    LinearLayout ll_treasure_amount;
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

    public void setData() {
//        if (videoBonusResultEntity == null) {
//            return;
//        }
        ll_treasure_amount.setAlpha(0f);
        //今日元宝
        int sycee_amount = 234;
        tvAmount.setText(sycee_amount + "元宝");

//        Glide.with(mContext).load(R.drawable.treasure_open).into(new GlideDrawableImageViewTarget(iv_box_open, 100));
//        Glide.with(mContext).load(R.drawable.treasure_open).asGif().into(iv_box_open);
//        Glide.with(mContext).load(R.drawable.treasure_dialog_progress).asGif().into(ivProgress);
        startBoxBigAnim();
        startMoneyTextAnim();

        startBoxAnim();

//        if (t == null){
//            t = new Timer();
//        }
//        t.schedule(new TimerTask() {
//            public void run() {
//                dismiss();
//                t.cancel();
//            }
//        }, 1500 + 500 + 1500);

    }

    private void startBoxBigAnim() {
        AnimationDrawable animationDrawable = (AnimationDrawable) ivBox.getBackground();
        animationDrawable.start();
    }

    private AnimationDrawable animationDrawable;

    private void startBoxAnim() {
        animationDrawable = (AnimationDrawable) iv_box_open.getBackground();
        animationDrawable.stop();
        animationDrawable.start();
    }

    public void startMoneyTextAnim() {

        ObjectAnimator translationY = ObjectAnimator.ofFloat(ll_treasure_amount, "translationY", 0, UIUtils.dip2px(-200));
        translationY.setInterpolator(new LinearInterpolator());

        ObjectAnimator alpha = ObjectAnimator.ofFloat(ll_treasure_amount, "alpha", 0f, 1.0f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ll_treasure_amount, "scaleX", 1.0f, 2.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ll_treasure_amount, "scaleY", 1.0f, 2.0f);

        PropertyValuesHolder scaleX_After =PropertyValuesHolder.ofFloat("scaleX", 2.0f, 1.8f, 2.0f, 1.8f, 2.0f, 1.8f, 2.0f);
        PropertyValuesHolder scaleY_After = PropertyValuesHolder.ofFloat("scaleY", 2.0f, 1.8f, 2.0f, 1.8f, 2.0f, 1.8f, 2.0f);
        PropertyValuesHolder alpha_After =PropertyValuesHolder.ofFloat("alpha", 1.0f, 1.0f, 1.0f, 1.0f, 0f);
        ObjectAnimator animator_After = ObjectAnimator.ofPropertyValuesHolder(ll_treasure_amount, scaleX_After, scaleY_After, alpha_After);


        //------------------------------先走文字缩小--------------------------------
        treasureAnim = new AnimatorSet();
        treasureAnim.setDuration(800);  //动画时间
        treasureAnim.setInterpolator(new AccelerateDecelerateInterpolator());  //设置插值器
        treasureAnim.play(scaleX).with(alpha).with(scaleY).with(translationY).before(animator_After);  //同时执行
        treasureAnim.setStartDelay(2500);
        treasureAnim.start();

    }

}
