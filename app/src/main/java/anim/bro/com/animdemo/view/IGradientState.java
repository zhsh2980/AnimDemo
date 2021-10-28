package anim.bro.com.animdemo.view;

import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Lin on 2020/11/6.
 */
public interface IGradientState {

    @IntDef({GradientDrawable.RECTANGLE, GradientDrawable.OVAL, GradientDrawable.LINE, GradientDrawable.RING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GradientShape {
    }

    void setSolidColor(@ColorInt int argb);

    void setSolidColors(@Nullable ColorStateList colorStateList);

    void setGradientColors(@ColorInt int[] colors);

    void setCornerRadius(float radius);

    void setCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft);

    void setCornerRadii(@Nullable float[] radii);

    void setStroke(int width, @ColorInt int color);

    void setStroke(int width, ColorStateList colorStateList);

    void setStroke(int width, @ColorInt int color, float dashWidth, float dashGap);

    void setStroke(int width, ColorStateList colorStateList, float dashWidth, float dashGap);

    void setShape(@GradientShape int shape);

    void setGradientCenter(float x, float y);

    void setGradientOrientation(GradientDrawable.Orientation orientation);

}
