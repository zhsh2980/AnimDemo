package anim.bro.com.practice.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import anim.bro.com.practice.R;


/**
 * @author zhaofusen
 * @describe 商品详情页工具类
 * @date 2019/5/22 17:38
 */
public class PriceUtils {

    public static int dp2px(Context context, float dp) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    public static float dip2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * density(context);
    }

    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 处理价格小数点后面的大小展示
     *
     * @param context
     * @param showPriceTextView
     * @param price
     * @param txSize
     */
    public static void setPriceSpannableText(Context context, TextView showPriceTextView, String price, int txSize) {
        setPriceSpannableText(context, showPriceTextView, price, txSize, 0);
    }

    /**
     * 处理区间价格小数点后面的大小展示
     *
     * @param context
     * @param showPriceTextView
     * @param minPrice
     * @param maxPrice
     * @param txSize
     * @param spanTxSize        添加设置其他文字的大小
     */
    public static void setPriceSpannableText(Context context, TextView showPriceTextView,
                                             String minPrice, String maxPrice,
                                             int txSize, int spanTxSize) {
        if (TextUtils.isEmpty(minPrice) || TextUtils.isEmpty(maxPrice)) {
            showPriceTextView.setVisibility(View.INVISIBLE);
            return;
        }
        showPriceTextView.setVisibility(View.VISIBLE);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float maxWidth = dm.widthPixels / 3;
        float scaled = context.getResources().getDisplayMetrics().scaledDensity;
        //设置截取Span文字的大小，默认为16sp，可手动设置
//        spanTxSize = spanTxSize > 0 ? (int) (spanTxSize * scaled) : (int) context.getResources().getDimension(R.dimen.dim_16sp);
        spanTxSize = spanTxSize > 0 ? (int) (spanTxSize * scaled) : SizeUtils.dp2px(16);
        //处理人民币符号¥
        String moneySymbol = context.getString(R.string.pd_money_symbol);
        setMoneySymbol(showPriceTextView, txSize, spanTxSize, maxWidth, scaled, moneySymbol, minPrice, maxPrice);
    }

    private static void setMoneySymbol(TextView showPriceTextView, int txSize, int spanTxSize, float maxWidth, float scaled, String moneySymbol, String price, String maxprice) {
        boolean isExistMoney = price.contains(moneySymbol) && price.indexOf(moneySymbol) != -1 && price.indexOf(moneySymbol) < price.length();
        if (!isExistMoney) {
            //这种是输入人民币符号，输入看到的是￥，展示在UI上是¥，所以过滤两种情况
            moneySymbol = "￥";
            isExistMoney = price.contains(moneySymbol) && price.indexOf(moneySymbol) != -1 && price.indexOf(moneySymbol) < price.length();
        }
        if (txSize > 22) {
            txSize = reSizeTextView(showPriceTextView, price, txSize, maxWidth, moneySymbol);
        }
        txSize = txSize <= 22 ? 22 : txSize;
        int moneyIndex = 0;
        Spannable wordToSpan = new SpannableString(price);
        if (!TextUtils.isEmpty(price) && price.contains(".") &&
                price.indexOf(".") != -1 && price.indexOf(".") < price.length()) {
            int index = price.indexOf(".");
            if (isExistMoney) {
                moneyIndex = price.indexOf(moneySymbol) + 1;
                wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyIndex, index,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), index, price.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            if (isExistMoney) {
                moneyIndex = price.indexOf(moneySymbol) + 1;
                wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyIndex, price.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showPriceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        }


        boolean isMaxExistMoney = maxprice.contains(moneySymbol) && maxprice.indexOf(moneySymbol) != -1 && maxprice.indexOf(moneySymbol) < maxprice.length();
        if (!isMaxExistMoney) {
            //这种是输入人民币符号，输入看到的是￥，展示在UI上是¥，所以过滤两种情况
            moneySymbol = "￥";
            isMaxExistMoney = maxprice.contains(moneySymbol) && maxprice.indexOf(moneySymbol) != -1 && maxprice.indexOf(moneySymbol) < maxprice.length();
        }
        if (txSize > 22) {
            txSize = reSizeTextView(showPriceTextView, maxprice, txSize, maxWidth, moneySymbol);
        }
        txSize = txSize <= 22 ? 22 : txSize;
        int moneyMaxIndex = 0;
        Spannable wordMaxToSpan = new SpannableString(maxprice);
        if (!TextUtils.isEmpty(maxprice) && maxprice.contains(".") &&
                maxprice.indexOf(".") != -1 && maxprice.indexOf(".") < maxprice.length()) {
            int index = maxprice.indexOf(".");
            if (isMaxExistMoney) {
                moneyMaxIndex = maxprice.indexOf(moneySymbol) + 1;
                wordMaxToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyMaxIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordMaxToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyMaxIndex, index,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordMaxToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), index, maxprice.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            if (isMaxExistMoney) {
                moneyMaxIndex = maxprice.indexOf(moneySymbol) + 1;
                wordMaxToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyMaxIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordMaxToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyMaxIndex, maxprice.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showPriceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        }


        if (wordToSpan != null) {
            showPriceTextView.setText(wordToSpan);
            showPriceTextView.append("~");
            showPriceTextView.append(wordMaxToSpan);
//            showPriceTextView.setText(wordToSpan + "" + wordMaxToSpan);
        } else {
            showPriceTextView.setText(price);
        }
    }

    /**
     * 处理价格小数点后面的大小展示
     *
     * @param context
     * @param showPriceTextView
     * @param price
     * @param txSize
     * @param spanTxSize        添加设置其他文字的大小
     */
    public static void setPriceSpannableText(Context context, TextView showPriceTextView, String price, int txSize, int spanTxSize) {
        if (TextUtils.isEmpty(price)) {
            showPriceTextView.setVisibility(View.INVISIBLE);
            return;
        }
        showPriceTextView.setVisibility(View.VISIBLE);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float maxWidth = dm.widthPixels / 3;
        float scaled = context.getResources().getDisplayMetrics().scaledDensity;
        //设置截取Span文字的大小，默认为16sp，可手动设置
//        spanTxSize = spanTxSize > 0 ? (int) (spanTxSize * scaled) : (int) context.getResources().getDimension(R.dimen.dim_16sp);
        spanTxSize = spanTxSize > 0 ? (int) (spanTxSize * scaled) : SizeUtils.dp2px(16);
        //处理人民币符号¥
        String moneySymbol = context.getString(R.string.pd_money_symbol);
        boolean isExistMoney = price.contains(moneySymbol) && price.indexOf(moneySymbol) != -1 && price.indexOf(moneySymbol) < price.length();
        if (!isExistMoney) {
            //这种是输入人民币符号，输入看到的是￥，展示在UI上是¥，所以过滤两种情况
            moneySymbol = "￥";
            isExistMoney = price.contains(moneySymbol) && price.indexOf(moneySymbol) != -1 && price.indexOf(moneySymbol) < price.length();
        }
        if (txSize > 22) {
            txSize = reSizeTextView(showPriceTextView, price, txSize, maxWidth, moneySymbol);
        }
        txSize = txSize <= 22 ? 22 : txSize;
        int moneyIndex = 0;
        Spannable wordToSpan = new SpannableString(price);
        if (!TextUtils.isEmpty(price) && price.contains(".") &&
                price.indexOf(".") != -1 && price.indexOf(".") < price.length()) {
            int index = price.indexOf(".");
            if (isExistMoney) {
                moneyIndex = price.indexOf(moneySymbol) + 1;
                wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyIndex, index,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), index, price.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } else {
            if (isExistMoney) {
                moneyIndex = price.indexOf(moneySymbol) + 1;
                wordToSpan.setSpan(new AbsoluteSizeSpan(spanTxSize), 0, moneyIndex,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            wordToSpan.setSpan(new AbsoluteSizeSpan((int) (txSize * scaled)), moneyIndex, price.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                showPriceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        }

        if (wordToSpan != null) {
            showPriceTextView.setText(wordToSpan);
        } else {
            showPriceTextView.setText(price);
        }
    }

    private static int reSizeTextView(TextView textView, String text, int textSize, float maxWidth, String moneySymbol) {
        if (textView == null || TextUtils.isEmpty(text) || maxWidth <= 0) {
            return 25;
        }
        //默认字体大小
        int textSizeInDp = textSize;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        int textTargeWidth = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
        Paint paint = textView.getPaint();
        float textWidth = paint.measureText(text);

        if (textWidth - textTargeWidth > 0 && textWidth > maxWidth) {
            for (; textSizeInDp > 0; textSizeInDp--) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeInDp);
                textWidth = paint.measureText(text);
                if (textWidth <= maxWidth) {
                    break;
                }
            }
        } else if ("敬请期待".equalsIgnoreCase(text) ||
                String.format("%%", moneySymbol, "敬请期待").equalsIgnoreCase(text)) {
            textSizeInDp = 27;
        }
        return textSizeInDp;
    }
}
