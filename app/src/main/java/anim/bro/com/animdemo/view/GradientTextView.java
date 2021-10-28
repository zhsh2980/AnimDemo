package anim.bro.com.animdemo.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import anim.bro.com.animdemo.R;


/**
 * Created by Lin on 2020/11/6.
 */
public class GradientTextView extends AppCompatTextView implements IGradientState {

    private GradientDrawable mGradientDrawable;

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);

        int shape = ta.getInt(R.styleable.GradientTextView_compatShape, 0);

        int solidColor = ta.getColor(R.styleable.GradientTextView_compatSolidColor, 0);

        int radius = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatRadius, 0);
        int topLeftRadius = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatTopLeftRadius, radius);
        int topRightRadius = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatTopRightRadius, radius);
        int bottomRightRadius = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatBottomRightRadius, radius);
        int bottomLeftRadius = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatBottomLeftRadius, radius);

        boolean hasStartColor = ta.hasValue(R.styleable.GradientTextView_compatStartColor);
        boolean hasCenterColor = ta.hasValue(R.styleable.GradientTextView_compatCenterColor);
        boolean hasEndColor = ta.hasValue(R.styleable.GradientTextView_compatEndColor);
        int startColor = ta.getColor(R.styleable.GradientTextView_compatStartColor, 0);
        int centerColor = ta.getColor(R.styleable.GradientTextView_compatCenterColor, 0);
        int endColor = ta.getColor(R.styleable.GradientTextView_compatEndColor, 0);
        float centerX = ta.getFloat(R.styleable.GradientTextView_compatCenterX, 0.5f);
        float centerY = ta.getFloat(R.styleable.GradientTextView_compatCenterY, 0.5f);
        boolean hasAngle = ta.hasValue(R.styleable.GradientTextView_compatAngle);
        int angle = (int) ta.getFloat(R.styleable.GradientTextView_compatAngle, 0);

        int strokeColor = ta.getColor(R.styleable.GradientTextView_compatStrokeColor, 0);
        int strokeWidth = ta.getDimensionPixelSize(R.styleable.GradientTextView_compatStrokeWidth, 0);
        float dashWidth = ta.getDimension(R.styleable.GradientTextView_compatDashWidth, 0);
        float dashGap = ta.getDimension(R.styleable.GradientTextView_compatDashGap, 0);

        ta.recycle();

        GradientDrawable drawable = obtain();
        drawable.setShape(shape);
        drawable.setColor(solidColor);
        drawable.setCornerRadii(new float[]{
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomRightRadius, bottomRightRadius,
                bottomLeftRadius, bottomLeftRadius});
        drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
        if (hasAngle) {
            drawable.setOrientation(getGDOrientation(angle));
        }
        if (hasStartColor && hasEndColor) {
            if (hasCenterColor) {
                drawable.setColors(new int[]{startColor, centerColor, endColor});
            } else {
                drawable.setColors(new int[]{startColor, endColor});
            }
        }
        drawable.setGradientCenter(centerX, centerY);

        setBackground(drawable);
    }

    private GradientDrawable.Orientation getGDOrientation(int angle) {
        angle %= 360;
        GradientDrawable.Orientation mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
        switch (angle) {
            case 0:
                mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;

            case 45:
                mOrientation = GradientDrawable.Orientation.BL_TR;
                break;

            case 90:
                mOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;

            case 135:
                mOrientation = GradientDrawable.Orientation.BR_TL;
                break;

            case 180:
                mOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;

            case 225:
                mOrientation = GradientDrawable.Orientation.TR_BL;
                break;

            case 270:
                mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;

            case 315:
                mOrientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return mOrientation;
    }

    @Override
    public void setSolidColor(int argb) {
        obtain().setColor(argb);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setSolidColors(@Nullable ColorStateList colorStateList) {
        obtain().setColor(colorStateList);
    }

    @Override
    public void setGradientColors(int[] colors) {
        obtain().setColors(colors);
    }

    @Override
    public void setCornerRadius(float radius) {
        obtain().setCornerRadius(radius);
    }

    @Override
    public void setCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        this.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
    }

    @Override
    public void setCornerRadii(@Nullable float[] radii) {
        obtain().setCornerRadii(radii);
    }

    @Override
    public void setStroke(int width, int color) {
        obtain().setStroke(width, color);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStroke(int width, ColorStateList colorStateList) {
        obtain().setStroke(width, colorStateList);
    }

    @Override
    public void setStroke(int width, int color, float dashWidth, float dashGap) {
        obtain().setStroke(width, color, dashWidth, dashGap);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStroke(int width, ColorStateList colorStateList, float dashWidth, float dashGap) {
        obtain().setStroke(width, colorStateList, dashWidth, dashGap);
    }

    /**
     * @param shape The desired shape for this drawable:
     *              {@link GradientDrawable#LINE},
     *              {@link GradientDrawable#OVAL},
     *              {@link GradientDrawable#RECTANGLE},
     *              {@link GradientDrawable#RING}
     */
    @Override
    public void setShape(int shape) {
        obtain().setShape(shape);
    }

    @Override
    public void setGradientCenter(float x, float y) {
        obtain().setGradientCenter(x, y);
    }

    @Override
    public void setGradientOrientation(GradientDrawable.Orientation orientation) {
        obtain().setOrientation(orientation);
    }

    private GradientDrawable obtain() {
        if (mGradientDrawable == null) {
            mGradientDrawable = new GradientDrawable();
        }
        return mGradientDrawable;
    }

}
