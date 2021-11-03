package anim.bro.com.practice.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by zhangshan on 2021/10/26 16:28.
 * Description：请添加类注释
 */
public class CustomImageSpan extends ImageSpan {

    public CustomImageSpan(Drawable drawable) {
        super(drawable);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drawableHeight = rect.bottom - rect.top;

            int top = drawableHeight / 2 - fontHeight / 4;
            int bottom = drawableHeight / 2 + fontHeight / 4;

            // ascent: 字体最上端到基线的距离，为负值。
            // descent：字体最下端到基线的距离，为正值。
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.save();
        Drawable drawable = getDrawable();
        int transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }
}