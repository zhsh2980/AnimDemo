package anim.bro.com.practice.tuibida.utils;

import java.text.NumberFormat;

public class SVUtil {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastDoubleClick(long delayTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < delayTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 解析服务器返回的点赞等的数量格式
     *
     * @param old
     * @param offset 数字加减量
     * @return
     */
    public static String praseStringCount(String old, int offset) {
        try {
            int count = Integer.parseInt(old);
            count += offset;
            if (count <= 0) {
                return "0";
            } else if (count < 10000) {
                return String.valueOf(count);
            } else {
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(1);
                nf.setMinimumFractionDigits(1);
                return nf.format(count / 10000F) + "w";
            }
        } catch (NumberFormatException e) {
        }
        return old;
    }

    public static String praseStringCountOrigin(String old, int offset) {
        try {
            return Integer.toString(Integer.parseInt(old) + offset);
        } catch (Exception e) {
            return old;
        }
    }

    /***
     * 获取url 指定name的value;
     * @param url
     * @param name
     * @return
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }
}
