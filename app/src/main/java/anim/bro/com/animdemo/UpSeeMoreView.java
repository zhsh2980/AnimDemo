package anim.bro.com.animdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import anim.bro.com.animdemo.util.UIUtils;

/**
 * Created by zhangshan on 2018/9/24 22:29.
 */
public class UpSeeMoreView extends RelativeLayout {

    private View view_white_line;
    private ImageView iv_hand_image;

    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;
    private boolean isUpInvisible = true;

    public UpSeeMoreView(Context context) {
        this(context, null);
    }

    public UpSeeMoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpSeeMoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setOnUpInvisible();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_see_more, this);

        view_white_line = findViewById(R.id.view_white_line);
        iv_hand_image = findViewById(R.id.iv_hand_image);


        int minHeight = UIUtils.dip2px(30);
        final int maxHeight = UIUtils.dip2px(130);
        ValueAnimator animator = ValueAnimator.ofInt(minHeight, maxHeight);
        animator.setDuration(1100);
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
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void setOnUpInvisible() {
        setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向下滑動
                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            //向上滑动
                            if (isUpInvisible) {
                                Log.i("bro", "mCurPosY: " + mCurPosY + "-----mPosY: " + mPosY);
                                setVisibility(GONE);
                            }
                        }

                        break;
                }
                return true;
            }

        });

    }

    public void setUpInvisible(boolean isUpInvisible) {
        this.isUpInvisible = isUpInvisible;
    }


}
