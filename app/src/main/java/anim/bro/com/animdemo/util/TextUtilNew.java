package anim.bro.com.animdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anim.bro.com.animdemo.R;

/**
 * textView 末尾加标签
 *
 * @author zhuqiao
 */
public class TextUtilNew {

    private static int width = SizeUtils.dp2px(70);
    private static int height = SizeUtils.dp2px(15);

    //测试 url
    public static String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";

    public static String mColorStr = "#123456";
//

    /**
     * 不带箭头的
     *
     * @param context  上下文
     * @param targetTv 目标 TextView
     * @param title    文字
     * @param tagStr   标签名
     * @param picUrl   背景图url
     */
    public static void addTagToTextView(@Nullable Context context, @Nullable TextView targetTv, @Nullable String title,
                                        @Nullable String tagStr, @Nullable String picUrl) {
        addTagToTextView(context, targetTv, title, tagStr, null, picUrl);
    }

    /**
     * 带箭头
     *
     * @param context  上下文
     * @param targetTv 目标 TextView
     * @param title    文字
     * @param tagStr   标签名
     * @param background   背景图url
     */
    public static void addTagToTextView(@Nullable Context context, @Nullable TextView targetTv, @Nullable String title,
                                        @Nullable String tagStr, Drawable drawableLocal, @Nullable String background) {

        if (!TextUtils.isEmpty(background) && background.startsWith("#")){
            //背景色
            addTagToTextView(context, targetTv, title, tagStr, drawableLocal, null , background);
        }else {
            //背景图
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1440x900c5/g6/M00/02/08/ChMkKmD_YbiIcvwfAASj8VSnLUMAASK7gDaIRcABKQJ325.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1600x900c5/g6/M00/00/0A/ChMkKmD6I2iIPb5AAIfgIkjcHwYAASDZgGr_akAh-A6322.jpg";
            Glide.with(context) // could be an issue!R
//                .asBitmap()//强制Glide返回一个Bitmap
//                .load(R.drawable.icon_minus)
                    .load(background)
                    .override(width, height)
                    .centerCrop()
//                .transform(new RoundedCorners(SizeUtils.dp2px(13)))
                    .into(new Target<Drawable>() {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            Log.e("bro", "onLoadFailed");
                            targetTv.setText(title);
                        }

                        @Override
                        public void onResourceReady(@NonNull Drawable drawablePicNet, @Nullable Transition<? super Drawable> transition) {
//                        Drawable drawable = new BitmapDrawable(resource);
                            addTagToTextView(context, targetTv, title, tagStr, drawableLocal, drawablePicNet , "");
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void getSize(@NonNull SizeReadyCallback cb) {

                        }

                        @Override
                        public void removeCallback(@NonNull SizeReadyCallback cb) {

                        }

                        @Override
                        public void setRequest(@Nullable Request request) {

                        }

                        @Nullable
                        @Override
                        public Request getRequest() {
                            return null;
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onStop() {

                        }

                        @Override
                        public void onDestroy() {

                        }
                    });
        }

    }

    /**
     * @param context        上下文
     * @param target         目标 TextView
     * @param title          文字
     * @param tagStr         标签名
     * @param drawLocal      本地资源图(例如箭头)
     * @param drawablePicNet 背景图
     */
    private static void addTagToTextView(@Nullable Context context, @Nullable TextView target, @Nullable String title,
                                         @Nullable String tagStr, Drawable drawLocal, @Nullable Drawable drawablePicNet,
                                        String colorStr) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }

        target.setText(title);
//        String content = title + tagStr;

        /**
         * 创建TextView对象，设置drawable背景，设置字体样式，设置间距，设置文本等
         * 这里我们为了给TextView设置margin，给其添加了一个父容器LinearLayout。不过他俩都只是new出来的，不会添加进任何布局
         */
        LinearLayout layout = new LinearLayout(context);

        TextView textView = new TextView(context);
//        LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(width, height);
//        textView.setLayoutParams(Params1);
        textView.setText(tagStr);
        if (drawablePicNet != null) {
//            textView.setBackground(context.getResources().getDrawable(drawable));
            textView.setBackground(drawablePicNet);
        }
        if (!TextUtils.isEmpty(colorStr)){
            textView.setBackgroundColor(Color.parseColor(colorStr));
        }
        textView.setTextSize(12);
//        textView.setTextColor(Color.parseColor("#FDA413"));
        textView.setTextColor(Color.WHITE);
        textView.setIncludeFontPadding(false);
        textView.setPadding(SizeUtils.dp2px(6), 0,
                SizeUtils.dp2px(6), 0);
//        textView.setHeight(SizeUtils.dp2px(9));
//        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置左间距
        layoutParams.leftMargin = SizeUtils.dp2px(6);
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
//        layoutParams.bottomMargin = SizeUtils.dp2px(3);
        layout.addView(textView, layoutParams);

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 给上方设置的margin留出空间
        layout.layout(0, 0, textView.getMeasuredWidth() + SizeUtils.dp2px(6), textView.getMeasuredHeight());
//        layout.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        // 获取bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        //千万别忘最后一步
        layout.destroyDrawingCache();

        /**
         * 第三步，通过bitmap生成我们需要的ImageSpan对象
         */
//        ImageSpan imageSpan = new ImageSpan(context, bitmap);
//        ImageSpan imageSpan = new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);

        /**
         * 第四步将ImageSpan对象设置到SpannableStringBuilder的对应位置
         */
//        SpannableStringBuilder ssb = new SpannableStringBuilder(tagStr);
//        //将尾部tag字符用ImageSpan替换
//        ssb.setSpan(imageSpan, 0, tagStr.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        target.append(ssb);


        if (drawLocal != null) {
            //最后一行末尾加箭头
            SpannableStringBuilder arrowSp = new SpannableStringBuilder();
            target.append(" ");
//        arrowSp.append(target.getText());
            String logoTag2 = "#";
            arrowSp.append(logoTag2);
            Pattern patternLogo2 = Pattern.compile(logoTag2);
            Matcher matcherLogo2 = patternLogo2.matcher(arrowSp);
            if (matcherLogo2.find()) {
//            Drawable drawable1 = new BitmapDrawable(bitmap);
//                Drawable drawable1 = context.getResources().getDrawable(drawLocal);
                Drawable drawable1 = drawLocal;
                drawable1.setBounds(0, 0, SizeUtils.dp2px(5), SizeUtils.dp2px(10)); //自定义图片尺寸
                arrowSp.setSpan(new CustomImageSpan(drawable1), matcherLogo2.start(), matcherLogo2.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
                target.append(arrowSp);
            }
        }

        SpannableStringBuilder titleSp = new SpannableStringBuilder();
//        titleSp.append(title);
        String logoTag = "★";
        titleSp.append(logoTag);
        Pattern patternLogo = Pattern.compile(logoTag);
        Matcher matcherLogo = patternLogo.matcher(titleSp);
        if (matcherLogo.find()) {
            Drawable drawable1 = new BitmapDrawable(bitmap);
//            Drawable drawable1 = context.getResources().getDrawable(R.drawable.icon_minus);
            drawable1.setBounds(0, 0, width, height); //自定义图片尺寸
            titleSp.setSpan(new CustomImageSpan(drawable1), matcherLogo.start(), matcherLogo.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
            target.append(titleSp);
        }


        //整体加箭头
//        Drawable right = context.getResources().getDrawable(R.drawable.ic_cart_black_arrow);
//        target.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);


    }


}
