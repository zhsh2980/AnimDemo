package anim.bro.com.practice.tuibida.view;//package bro.tuibida.com.view;
//
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.widget.TextView;
//
//import com.blankj.utilcode.util.ScreenUtils;
//import com.jm.android.jumei.baselib.tools.DensityUtil;
//import com.jm.component.shortvideo.R;
//
///**
// * 主要是根据progress的变化来更新View
// * Created by admin on 2018/11/19.
// */
//
//public class CircleLoadingTextView extends TextView {
//
//    private ColorStateList inCircleColors = ColorStateList.valueOf(Color.TRANSPARENT);
//    private int progressLineColor = Color.parseColor("#EC3C2E");//进度条颜色
//    private int progressLineWidth = DensityUtil.dip2px(2);//进度条宽度 2dp
//    private int outLineWidth = DensityUtil.dip2px(4);//内部背景距离边界距离
//    private Paint mPaint = new Paint();
//    private RectF mArcRect = new RectF();
//    private int maxProgress = 100;//在时间超过5s的时候设为200动画更流畅
//    private int minProgress = 0;
//    private int progress = minProgress;//progress初始化，需要手动配置
//    Rect bounds = new Rect();
//    public final static int COUNT = 100;//正计时
//    public final static int COUNT_DOWN = 101;//倒计时
//    private int mProgressType = COUNT;
//    /**
//     * 进度条通知。
//     */
//    private OnCountdownProgressListener mCountdownProgressListener;
//    /**
//     * 进度倒计时时间。
//     */
//    private long timeMillis = 10000;//默认10s
//    private Bitmap mBitmap;
//
//    public CircleLoadingTextView(Context context) {
//        super(context);
//        initView();
//    }
//
//    public CircleLoadingTextView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        initView();
//    }
//
//    public CircleLoadingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        initView();
//    }
//
//    private void initView() {
//        if (mBitmap == null) {
//            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.task_progress_img);
//        }
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        //获取bounds边界
//        getDrawingRect(bounds);
//
//        int size = bounds.height() > bounds.width() ? bounds.width() : bounds.height();
//        float outerRadius = size / 2;
//
//        if (circleAllwaysGray) {//如果体力值为0的时候，只画灰色线圈和小黄点
//            //画内部背景
//            int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setColor(circleColor);
//            mPaint.setAntiAlias(true);
//            canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth - progressLineWidth, mPaint);
//
//            //进度条背景
//            mPaint.setColor(Color.parseColor("#f0efef"));
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(progressLineWidth);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            int deleteWidth2 = progressLineWidth + outLineWidth;
//            mArcRect.set(bounds.left + outLineWidth + progressLineWidth / 2, bounds.top + outLineWidth + progressLineWidth / 2,
//                    bounds.right - outLineWidth - progressLineWidth / 2, bounds.bottom - outLineWidth - progressLineWidth / 2);
//            canvas.drawArc(mArcRect, 0, 360, false, mPaint);
//
//            //画黄色的圆点
//            float diameter = bounds.right - bounds.left;
//            Matrix matrix = new Matrix();
//            int deleteWidth = progressLineWidth + outLineWidth;
//            matrix.postTranslate(diameter / 2 - mBitmap.getWidth() / 2,
//                    deleteWidth - mBitmap.getHeight() / 2 - progressLineWidth / 2);
//            float centerX = (bounds.left + bounds.right) / 2;
//            float centerY = (bounds.top + bounds.bottom) / 2;
//            matrix.postRotate(0, centerX, centerY);
//            canvas.drawBitmap(mBitmap, matrix, null);
//        } else if (duration != 0) {
//            //画内部背景
//            int circleColor = inCircleColors.getColorForState(getDrawableState(), 0);
//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setColor(circleColor);
//            mPaint.setAntiAlias(true);
//            canvas.drawCircle(bounds.centerX(), bounds.centerY(), outerRadius - outLineWidth - progressLineWidth, mPaint);
//
//            //进度条背景
//            mPaint.setColor(Color.parseColor("#f0efef"));
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(progressLineWidth);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            int deleteWidth2 = progressLineWidth + outLineWidth;
//            mArcRect.set(bounds.left + outLineWidth + progressLineWidth / 2, bounds.top + outLineWidth + progressLineWidth / 2,
//                    bounds.right - outLineWidth - progressLineWidth / 2, bounds.bottom - outLineWidth - progressLineWidth / 2);
//            canvas.drawArc(mArcRect, 0, 360, false, mPaint);
//
//            //画进度条
//            mPaint.setColor(progressLineColor);
//            mPaint.setStyle(Paint.Style.STROKE);
//            mPaint.setStrokeWidth(progressLineWidth);
//            mPaint.setStrokeCap(Paint.Cap.ROUND);
//            mPaint.setAntiAlias(true);
//            mArcRect.set(bounds.left + outLineWidth + progressLineWidth / 2, bounds.top + outLineWidth + progressLineWidth / 2,
//                    bounds.right - outLineWidth - progressLineWidth / 2, bounds.bottom - outLineWidth - progressLineWidth / 2);
//            canvas.drawArc(mArcRect, -90, 360 * curPosLong / duration, false, mPaint);
//
//            //画黄色的圆点
//            float diameter = bounds.right - bounds.left;
//            Matrix matrix = new Matrix();
//            int deleteWidth = progressLineWidth + outLineWidth;
//            matrix.postTranslate(diameter / 2 - mBitmap.getWidth() / 2,
//                    deleteWidth - mBitmap.getHeight() / 2 - progressLineWidth / 2);
//            float centerX = (bounds.left + bounds.right) / 2;
//            float centerY = (bounds.top + bounds.bottom) / 2;
//            matrix.postRotate(360 * curPosLong / duration, centerX, centerY);
//            canvas.drawBitmap(mBitmap, matrix, null);
//        }
//
//    }
//
//    /**
//     * 进度更新task。
//     */
//    private Runnable progressChangeTask = new Runnable() {
//        @Override
//        public void run() {
//            removeCallbacks(this);
//            switch (mProgressType) {
//                case COUNT:
//                    progress += 1;
//                    break;
//                case COUNT_DOWN:
//                    progress -= 1;
//                    break;
//            }
//            if (progress >= minProgress && progress <= maxProgress) {
//                isPlaying = true;
//
//                if (progress == maxProgress) {
//                    mCountdownProgressListener.onProgressComplete();
//                }
//
//                if (mCountdownProgressListener != null) {
////                    mCountdownProgressListener.onProgress(progress / (progress / 100));
//                }
//                invalidate();
//                if (doFlag) {
//                    postDelayed(progressChangeTask, timeMillis / maxProgress);//（timeMillis/maxProgress）ms更新一次，值越小更新越频繁，动画越流畅
//                }
//
//
//            } else {
//                isPlaying = false;
//                progress = validateProgress(progress);
//            }
//        }
//    };
//
//    /**
//     * 动画播放的控制标志位，true为继续播放，false为暂停播放
//     */
//    private boolean doFlag = true;
//
//    /**
//     * 动画是否在播放中，跟暂停无关，即使暂停也是播放中（true）
//     */
//    private boolean isPlaying = false;
//
//    /**
//     * 暂停动画，和continueTask配对使用
//     */
//    public void pauseTask() {
//        doFlag = false;
//    }
//
//    public boolean isPlaying() {
//        return isPlaying;
//    }
//
//    /**
//     * 继续动画，和pauseTask配对使用
//     */
//    public void continueTask() {
//        if (doFlag) {
//            return;
//        }
//        if (isPlaying) {
//            doFlag = true;
//            postDelayed(progressChangeTask, timeMillis / maxProgress);
//        }
//    }
//
//    /**
//     * 设置进度。
//     *
//     * @param progress 进度。
//     */
//    public void setProgress(int progress) {
//        this.progress = validateProgress(progress);
//        invalidate();
//    }
//
//    /**
//     * 拿到此时的进度。
//     *
//     * @return 进度值，最大100，最小0。
//     */
//    public int getProgress() {
//        return progress;
//    }
//
//    /**
//     * 开始。
//     */
//    public void start() {
//        doFlag = true;
//        isPlaying = true;
//        stop();
//        post(progressChangeTask);
//    }
//
//    /**
//     * 重新开始。
//     */
//    public void reStart() {
//        resetProgress();
//        start();
//    }
//
//    /**
//     * 停止。
//     */
//    public void stop() {
//        removeCallbacks(progressChangeTask);
//    }
//
//    public void setProgressType(int type) {
//        this.mProgressType = type;
//    }
//
//    /**
//     * 重置进度。
//     */
//    private void resetProgress() {
//        switch (mProgressType) {
//            case COUNT:
//                progress = minProgress;
//                break;
//            case COUNT_DOWN:
//                progress = maxProgress;
//                break;
//        }
//    }
//
//    /**
//     * 验证进度。
//     *
//     * @param progress 你要验证的进度值。
//     * @return 返回真正的进度值。
//     */
//    private int validateProgress(int progress) {
//        if (progress > maxProgress)
//            progress = maxProgress;
//        else if (progress < minProgress)
//            progress = minProgress;
//        return progress;
//    }
//
//    /**
//     * 设置进度监听。
//     *
//     * @param mCountdownProgressListener 监听器。
//     */
//    public void setCountdownProgressListener(OnCountdownProgressListener mCountdownProgressListener) {
//        this.mCountdownProgressListener = mCountdownProgressListener;
//    }
//
//    /**
//     * 进度监听。
//     */
//    public interface OnCountdownProgressListener {
//
//        /**
//         * 进度通知。
//         *
//         * @param progress 进度值。
//         */
//        void onProgress(int progress);
//
//        void onProgressComplete();
//
//    }
//
//    /**
//     * 设置倒计时总时间。
//     *
//     * @param timeMillis 毫秒。
//     */
//    public void setTimeMillis(long timeMillis) {
//        this.timeMillis = timeMillis;
//        invalidate();
//    }
//
//    public int getMaxProgress() {
//        return maxProgress;
//    }
//
//    public void setMaxProgress(int maxProgress) {
//        this.maxProgress = maxProgress;
//    }
//
//    /**
//     * 拿到进度条计时时间。
//     *
//     * @return 毫秒。
//     */
//    public long getTimeMillis() {
//        return this.timeMillis;
//    }
//
//    /**
//     * 计算剩余秒数
//     *
//     * @return
//     */
//    private int calcSecond() {
//        if (progress == 0) {
//            return 0;
//        }
//        if (progress == maxProgress) {
//            return progress / (maxProgress / 10);
//        }
//        return progress / (maxProgress / 10) + 1;
//    }
//
//    private long curPosLong;
//    private long duration;
//
//    private final String TAG = "CircleLoadingTextView";
//
//    public void updateProgress(long curPosLong, long duration) {
////        JuMeiLogMng.getInstance().w(TAG, "curPosLong = " + curPosLong + " , duration = " + duration);
//        this.curPosLong = curPosLong;
//        this.duration = duration;
//        if (curPosLong < 0) {
//            curPosLong = 0;
//        }
//        if (curPosLong <= duration) {
//            invalidate();
//        }
//    }
//
//    private boolean circleAllwaysGray = false;
//
//    /**
//     * 设置在体力值等于0的时候只画灰色的圈和黄点
//     *
//     * @param progressGray false，体力值大于0，设为动态转圈；true，静态灰色圆圈和黄点
//     */
//    public void setProgressGray(boolean progressGray) {
//        if (circleAllwaysGray && progressGray) {//如果他俩都为true，则不走下面的更新ui，避免过度绘制
//            return;
//        }
//        circleAllwaysGray = progressGray;
//        if (circleAllwaysGray) {
//            invalidate();
//        }
//    }
//
//}
