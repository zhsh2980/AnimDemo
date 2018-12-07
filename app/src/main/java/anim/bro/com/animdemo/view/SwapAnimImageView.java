package com.jm.leakmemerytest.release;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jm.leakmemerytest.R;

/**
 * Created by admin on 2018/12/7.
 */

public class SwapAnimImageView extends View {

    private Paint mPaint;
    private Bitmap redPacketBitmap;
    private int transparentColor;//半透明扫描的色值
    private Bitmap srcBitmap;

    public SwapAnimImageView(Context context) {
        super(context);
        init();
    }

    public SwapAnimImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwapAnimImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        transparentColor = Color.parseColor("#884D4D4D");

        redPacketBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.red_packag_big);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        //画红包图片，画到方块正中央
        canvas.drawBitmap(redPacketBitmap, (getWidth() - redPacketBitmap.getWidth()) / 2, (getHeight() - redPacketBitmap.getHeight()) / 2, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        //画半透明扫描的View，持续更新，实现动画
        canvas.drawBitmap(createSrcBitmap(getWidth(), getHeight()), 0, 0, mPaint);
        mPaint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    public Bitmap createSrcBitmap(int width, int height) {
        srcBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(srcBitmap);
        Paint dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setColor(transparentColor);
        canvas.drawArc(new RectF(0, 0, width, height), -90, swapValue, true, dstPaint);
        return srcBitmap;
    }

    int swapValue = -360;

    /**
     * 更新扫描角度，从-360到0
     *
     * @param value
     */
    public void updateValue(int value) {
        swapValue = -value;
        invalidate();
    }

}
