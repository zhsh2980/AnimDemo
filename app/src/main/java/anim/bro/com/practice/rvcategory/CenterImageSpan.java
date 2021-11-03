package anim.bro.com.practice.rvcategory;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by zhangshan on 2021/6/23 11:01.
 */
class CenterImageSpan extends ImageSpan {

    public static final int ALIGN_CENTER = 2;

    public CenterImageSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        //系统默认为ALIGN_BOTTOM
        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= fm.descent;
        } else {
            transY = ((y + fm.descent + y + fm.ascent) / 2
                    - b.getBounds().bottom / 2);
        }
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable b = getDrawable();
        Rect rect = b.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt painFm = paint.getFontMetricsInt();
            int fontHeight = (painFm.bottom - painFm.top);
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}
