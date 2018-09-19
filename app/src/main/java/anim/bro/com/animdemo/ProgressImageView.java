package anim.bro.com.animdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LinearInterpolator;


import static android.graphics.Paint.Style.STROKE;

/**
 * create time 2018,09,19
 * 带转圈的view
 * 半径需要手动设置，后期可根据需求优化
 */
public class ProgressImageView extends android.support.v7.widget.AppCompatImageView {

    private int outsideColor = ContextCompat.getColor(getContext(), android.R.color.holo_red_light);    //进度的颜色
    private float outsideRadius = 30f;    //外圆半径大小
    private float progressWidth = 3f;    //圆环的宽度
    private int maxProgress = 100;    //最大进度
    private float progress = 0f;    //当前进度
    private int direction = 1;    //进度从哪里开始(设置了4个值,上左下右)
    private Paint paint;
    private ValueAnimator animator;

    private long animTime = 3000;//动画时间
    private long delayAnimTime = 300;//延迟执行

    enum DirectionEnum {
        LEFT(0, 180.0f),
        TOP(1, 270.0f),
        RIGHT(2, 0.0f),
        BOTTOM(3, 90.0f);

        private final int direction;
        private final float degree;

        DirectionEnum(int direction, float degree) {
            this.direction = direction;
            this.degree = degree;
        }

        public int getDirection() {
            return direction;
        }

        public float getDegree() {
            return degree;
        }

        public boolean equalsDescription(int direction) {
            return this.direction == direction;
        }

        public static DirectionEnum getDirection(int direction) {
            for (DirectionEnum enumObject : values()) {
                if (enumObject.equalsDescription(direction)) {
                    return enumObject;
                }
            }
            return RIGHT;
        }

        public static float getDegree(int direction) {
            DirectionEnum enumObject = getDirection(direction);
            if (enumObject == null) {
                return 0;
            }
            return enumObject.getDegree();
        }
    }

    public long getAnimTime() {
        return animTime;
    }

    public void setAnimTime(long animTime) {
        this.animTime = animTime;
    }

    public long getDelayAnimTime() {
        return delayAnimTime;
    }

    public void setDelayAnimTime(long delayAnimTime) {
        this.delayAnimTime = delayAnimTime;
    }

    public ProgressImageView(Context context) {
        this(context, null);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //attrs 有问题 不处理
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R2.styleable.sv_ProgressImageView, defStyleAttr, 0);
//        outsideColor = a.getColor(R2.styleable.waveProgressImageView_wave_outside_color, ContextCompat.getColor(getContext(), android.R.color.holo_red_light));
//        outsideRadius = a.getDimension(R2.styleable.waveProgressImageView_wave_outside_radius, dp2px(getContext(), 30.0f));
//        progressWidth = a.getDimension(R2.styleable.waveProgressImageView_wave_progress_width, dp2px(getContext(), 3.0f));
//        progress = a.getFloat(R2.styleable.waveProgressImageView_wave_progress, 0.0f);
//        maxProgress = a.getInt(R2.styleable.waveProgressImageView_wave_max_progress, 100);
//        direction = a.getInt(R2.styleable.waveProgressImageView_wave_direction, 3);
//        a.recycle();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int circlePoint = getWidth() / 2;
        outsideRadius = getHeight() / 2 - 3;
        paint.setStyle(STROKE); //设置空心
        paint.setStrokeWidth(progressWidth); //设置圆的宽度
        paint.setAntiAlias(true);  //消除锯齿
        //第二步:画进度(圆弧)
        paint.setColor(outsideColor);  //设置进度的颜色
        RectF oval = new RectF(circlePoint - outsideRadius, circlePoint - outsideRadius, circlePoint + outsideRadius, circlePoint + outsideRadius);  //用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, DirectionEnum.getDegree(direction), 360 * (progress / maxProgress), false, paint);  //根据进度画圆弧
    }

    //加锁保证线程安全,能在线程中使用 progress 表示最大到多少,支持不到最大
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress should not be less than 0");
        }
        if (progress > maxProgress) {
            progress = maxProgress;
        }
        startAnim(progress);
    }

    private void startAnim(float startProgress) {
        animator = ObjectAnimator.ofFloat(0, startProgress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ProgressImageView.this.progress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setStartDelay(delayAnimTime);
        animator.setDuration(animTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {//开始
                Log.e("aaaaa", "onAnimationStart 执行完毕");
            }

            @Override
            public void onAnimationEnd(Animator animation) {//执行完毕
                Log.e("aaaaa", "onAnimationEnd 执行完毕");
                if (endListener != null) {
                    endListener.endListener();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e("aaaaa", "onAnimationCancel 执行完毕");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e("aaaaa", "onAnimationRepeat 执行完毕");
            }
        });
        animator.start();
    }

    private EndListener endListener;

    public void setEndListener(EndListener endListener) {
        this.endListener = endListener;
    }

    public interface EndListener {//执行完毕回调

        void endListener();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = (int) ((2 * outsideRadius) + progressWidth);
        }
        size = MeasureSpec.getSize(heightMeasureSpec);
        mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = (int) ((2 * outsideRadius) + progressWidth);
        }
        setMeasuredDimension(width, height);
    }

    //中间的进度百分比
    private String getProgressText() {
        return (int) ((progress / maxProgress) * 100) + "%";
    }

    public int getOutsideColor() {
        return outsideColor;
    }

    public void setOutsideColor(int outsideColor) {
        this.outsideColor = outsideColor;
    }

    public float getOutsideRadius() {
        return outsideRadius;
    }

    public void setOutsideRadius(float outsideRadius) {
        this.outsideRadius = outsideRadius;
    }

    public float getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(float progressWidth) {
        this.progressWidth = progressWidth;
    }

    public synchronized int getMaxProgress() {
        return maxProgress;
    }

    public synchronized void setMaxProgress(int maxProgress) {
        if (maxProgress < 0) {
            //此为传递非法参数异常
            throw new IllegalArgumentException("maxProgress should not be less than 0");
        }
        this.maxProgress = maxProgress;
    }

    public synchronized float getProgress() {
        return progress;
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
