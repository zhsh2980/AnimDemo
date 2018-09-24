package anim.bro.com.animdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import anim.bro.com.animdemo.util.UIUtils;

/**
 * Created by zhangshan on 2018/9/24 22:29.
 */
public class UpSeeMoreView extends RelativeLayout {

    private View view_white_line;
    private ImageView iv_hand_image;

    public UpSeeMoreView(Context context) {
        this(context, null);
    }

    public UpSeeMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpSeeMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_see_more, this);

        view_white_line = findViewById(R.id.view_white_line);
        iv_hand_image = findViewById(R.id.iv_hand_image);


        int minHeight = UIUtils.dip2px(30);
        final int maxHeight = UIUtils.dip2px(130);
        ValueAnimator animator = ValueAnimator.ofInt(minHeight, maxHeight);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                RelativeLayout.LayoutParams layoutParams_1 = (RelativeLayout.LayoutParams) view_white_line.getLayoutParams();
                layoutParams_1.height = value;
                view_white_line.setLayoutParams(layoutParams_1);

                RelativeLayout.LayoutParams layoutParams_2 = (RelativeLayout.LayoutParams) iv_hand_image.getLayoutParams();
                layoutParams_2.bottomMargin = value;
                iv_hand_image.setLayoutParams(layoutParams_2);
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
    }


}
