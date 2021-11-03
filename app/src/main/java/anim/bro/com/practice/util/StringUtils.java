package anim.bro.com.practice.util;

import android.text.TextUtils;

public class StringUtils {

    public static int parseToInt(String si) {
        return parseToInt(si, 0);
    }

    public static int parseToInt(String si, int defaultvalue) {

        if (TextUtils.isEmpty(si)) {
            return defaultvalue;
        }

        int result = defaultvalue;
        try {
            result = Integer.parseInt(si);
        } catch (NumberFormatException ex) {
            result = defaultvalue;
        }

        return result;
    }

}
