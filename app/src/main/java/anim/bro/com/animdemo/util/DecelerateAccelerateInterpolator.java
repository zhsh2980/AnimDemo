package anim.bro.com.animdemo.util;

import android.animation.TimeInterpolator;

/**
 * Created by zhangshan on 2018/9/19 11:00.
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator {

    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result = (float) (Math.sin(Math.PI * input)) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return result;
    }

}