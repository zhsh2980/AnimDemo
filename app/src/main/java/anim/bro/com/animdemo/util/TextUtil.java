package anim.bro.com.animdemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * textView 末尾加标签
 *
 * @author zhuqiao
 */
public class TextUtil {
    /**
     * 带箭头
     *
     * @param context    上下文
     * @param targetTv   目标 TextView
     * @param title      文字
     * @param tagText     标签名
     * @param background 背景图url 或 背景色
     * @param textColor  文字颜色
     * @param drawableLocal  本地资源图(比如箭头)
     * @param arrowLeftOrRight  本地资源在左还是在右(暂时未实现)
     */
    private Context context;
    private TextView targetTv;
    private String targetTvText = "";
    private String tagText;
    private String tagBackground = "#FFFFFF";
    private String tagColor = "#000000";
    private Drawable tagTextDrawableLocal;
    private int tagTextSize;
    private boolean arrowLeftOrRight;//暂时未实现
    private int maxLines = 2;//最大行数
    private int labelLeftMarginPx = SizeUtils.dp2px(6);//标签的左 margin
    private int labelWidthPx;//标签的左 margin
    private int arrorWidthPx = SizeUtils.dp2px(5);//箭头的宽度
    private int arrorLeftMarginPx = SizeUtils.dp2px(2);//箭头的宽度
    private SpannableStringBuilder arrowDrawable;
    private SpannableStringBuilder labelDrawable;

    private TextUtil(Builder builder) {
        context = builder.context;
        targetTv = builder.targetTv;
        targetTvText = builder.targetTvText;
        tagText = builder.tagText;
        tagBackground = builder.tagBackground;
        tagColor = builder.tagColor;
        tagTextDrawableLocal = builder.tagTextDrawableLocal;
        tagTextSize = builder.tagTextSize;
        arrowLeftOrRight = builder.arrowLeftOrRight;
        maxLines = builder.maxLines;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    //给图片设置的宽高,背景色用不着
    private static int width = SizeUtils.dp2px(70);
    private static int height = SizeUtils.dp2px(15);

    //测试 url
    public static String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";


    public static String mTagColorDefault = "#000000";
    public static String mTagBackgroundDefault = "#FFFFFF";

//    public void addTagToTextView(@Nullable Context context, @Nullable TextView targetTv, @Nullable String title,
//                                        @Nullable String tagStr, @Nullable String background, @Nullable String textColor) {
//        addTagToTextView(context, targetTv, title, tagStr, null, background, textColor);
//    }

    public void addTagToTextView() {

        if (!TextUtils.isEmpty(tagBackground) && tagBackground.startsWith("#")) {
            //背景色
            addTagToTextView(null);
        } else {
            //背景图
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1440x900c5/g6/M00/02/08/ChMkKmD_YbiIcvwfAASj8VSnLUMAASK7gDaIRcABKQJ325.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1600x900c5/g6/M00/00/0A/ChMkKmD6I2iIPb5AAIfgIkjcHwYAASDZgGr_akAh-A6322.jpg";
            Glide.with(context) // could be an issue!R
//                .asBitmap()//强制Glide返回一个Bitmap
//                .load(R.drawable.icon_minus)
                    .load(tagBackground)
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
                            targetTv.setText(targetTvText);
                        }

                        @Override
                        public void onResourceReady(@NonNull Drawable drawablePicNet, @Nullable Transition<? super Drawable> transition) {
//                        Drawable drawable = new BitmapDrawable(resource);
                            addTagToTextView(drawablePicNet);
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

    //截取最后一位
    private String subLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    int textViewMaxWidthPx = -1;//父控件最大宽度
    int labelAllWidthPx = -1;
    int fitErrorWidth;

    private void clipText(String targetTvText, boolean isCliped) {
//        targetTv.append(targetTvText);
        //2. 算出最后一行的文字宽度
        int textTotalWidthPx = (int) (targetTv.getPaint().measureText(targetTvText) + targetTv.getMaxLines() * (targetTv.getPaddingLeft() + targetTv.getPaddingRight()));
        log("targetTvText: " + targetTvText + "  textTotalWidthPx: " + textTotalWidthPx);
//        int textTotalWidthPx = textTotalWidthPx;//除去标签文本的宽度(包括 padingleft padingright)
        Log.i("bro", "textTotalWidthPx  : " + textTotalWidthPx + "  textViewMaxWidthPx  : " + textViewMaxWidthPx);
        if (textViewMaxWidthPx > 0) {
//            if (textTotalWidthPx > textViewMaxWidthPx) {
            //多行
            int i = textTotalWidthPx / textViewMaxWidthPx;
            int j = textTotalWidthPx % textViewMaxWidthPx;
            if (i + 1 < targetTv.getMaxLines()) {
                j = 0;
            }
            Log.i("bro", "i: " + i);
            Log.i("bro", "j : " + j);
            int textNeedLines = i + 1;
            log("maxLine: " + targetTv.getMaxLines() + "  textNeedLines: " + textNeedLines);
            //挨个删除,直到符合行数要求
            if (textNeedLines <= targetTv.getMaxLines()) {
                //省略号的宽度
                textTotalWidthPx = j + targetTv.getPaddingLeft();
                int textThreePointWidthPx = 0;
                if (isCliped) {
                    textThreePointWidthPx = (int) (targetTv.getPaint().measureText("..."));
                }
                //箭头左边的 marginLeft
                int arrowLeftMarginPx = 0;
                if (arrowDrawable != null) {
                    arrowLeftMarginPx = (int) (targetTv.getPaint().measureText(" "));
                }
                log("textViewMaxWidthPx: " + textViewMaxWidthPx
                        + "  textTotalWidthPx: " + textTotalWidthPx
                        + "  textThreePointWidthPx: " + textThreePointWidthPx
                        + "  labelAllWidthPx: " + labelAllWidthPx);

                fitErrorWidth = (i + 1) * (int) (targetTv.getPaint().measureText("哈"));
                log("容错距离: " + fitErrorWidth);

                if (textViewMaxWidthPx - textTotalWidthPx - textThreePointWidthPx - arrowLeftMarginPx - fitErrorWidth > labelAllWidthPx) {
                    if (isCliped) {
                        targetTvText = targetTvText + "...";
                    }
                    targetTv.setText(targetTvText);
                    if (arrowDrawable != null) {
                        targetTv.append(" ");
                        targetTv.append(arrowDrawable);
                    }
                    if (labelDrawable != null) {
                        targetTv.append(labelDrawable);
                    }
                } else {
                    clipText(subLastChar(targetTvText), true);
                }
                log("labelAllWidth: " + labelAllWidthPx);

            } else {
                clipText(subLastChar(targetTvText), true);
            }
//            } else {
//                //一行
//
//            }
        }
    }

    private void addTagToTextView(@Nullable Drawable drawablePicNet) {

        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }

        arrowDrawable = getArrowStringBuilder();
        labelDrawable = getLabelStringBuilder(drawablePicNet);
        if (tagTextDrawableLocal != null) {
            labelAllWidthPx = labelAllWidthPx + arrorWidthPx + arrorLeftMarginPx;
        }
        labelAllWidthPx = labelAllWidthPx + labelWidthPx + labelLeftMarginPx;

        targetTv.post(new Runnable() {
            @Override
            public void run() {
                //1. 测量出文本控件的最大宽度
                ViewGroup.LayoutParams layoutParams = targetTv.getLayoutParams();
                if (layoutParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    textViewMaxWidthPx = targetTv.getWidth();
                } else if (targetTv.getMaxWidth() != -1 && targetTv.getMaxWidth() != Integer.MAX_VALUE) {
                    textViewMaxWidthPx = targetTv.getMaxWidth();
                }

                Log.i("bro", "textViewMaxWidth: " + SizeUtils.px2dp(textViewMaxWidthPx));
                clipText(targetTvText, false);
            }
        });

        //整体加箭头
//        Drawable right = context.getResources().getDrawable(R.drawable.ic_cart_black_arrow);
//        target.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);

    }

    private SpannableStringBuilder getArrowStringBuilder() {
        if (tagTextDrawableLocal != null) {
            //最后一行末尾加箭头
            SpannableStringBuilder arrowSp = new SpannableStringBuilder();
            targetTv.append(" ");
//        arrowSp.append(target.getText());
            String logoTag2 = "#";
            arrowSp.append(logoTag2);
            Pattern patternLogo2 = Pattern.compile(logoTag2);
            Matcher matcherLogo2 = patternLogo2.matcher(arrowSp);
            if (matcherLogo2.find()) {
//            Drawable drawable1 = new BitmapDrawable(bitmap);
//                Drawable drawable1 = context.getResources().getDrawable(drawLocal);
                Drawable drawable1 = tagTextDrawableLocal;
                drawable1.setBounds(0, 0, arrorWidthPx, SizeUtils.dp2px(10)); //自定义图片尺寸
                arrowSp.setSpan(new CustomImageSpan(drawable1), matcherLogo2.start(), matcherLogo2.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
                return arrowSp;
//                targetTv.append(arrowSp);
            }
        }
        return null;
    }

    private SpannableStringBuilder getLabelStringBuilder(Drawable drawablePicNet) {


        /**
         * 创建TextView对象，设置drawable背景，设置字体样式，设置间距，设置文本等
         * 这里我们为了给TextView设置margin，给其添加了一个父容器LinearLayout。不过他俩都只是new出来的，不会添加进任何布局
         */
        LinearLayout layout = new LinearLayout(context);
        TextView textViewLabel = new TextView(context);
//        LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(width, height);
//        textView.setLayoutParams(Params1);
        if (!TextUtils.isEmpty(tagText)) {
            textViewLabel.setText(tagText);
        }
        if (drawablePicNet != null) {
//            textView.setBackground(context.getResources().getDrawable(drawable));
            textViewLabel.setBackground(drawablePicNet);
        }
        if (!TextUtils.isEmpty(tagBackground)) {
            try {
                textViewLabel.setBackgroundColor(Color.parseColor(tagBackground));
            } catch (Exception e) {
                textViewLabel.setTextColor(Color.parseColor(mTagBackgroundDefault));
            }
        }
        if (!TextUtils.isEmpty(tagColor)) {
            try {
                textViewLabel.setTextColor(Color.parseColor(tagColor));
            } catch (Exception e) {
                textViewLabel.setTextColor(Color.parseColor(mTagColorDefault));
            }
        }

        //参考 https://www.tabnine.com/code/java/methods/android.graphics.drawable.ShapeDrawable/setShape
        ShapeDrawable background = new ShapeDrawable();
        float[] radii = new float[8];// maybe 每个脚两个的意思  4 * 2 = 8
        for (int i = 0; i <= 7; i++) {
            radii[i] = SizeUtils.dp2px(8);
        }
        background.setShape(new RoundRectShape(radii, null, null)); // or RoundRectShape()
        background.getPaint().setColor((Color.parseColor(tagBackground)));
        textViewLabel.setBackground(background);


        if (tagTextSize > 0) {
            textViewLabel.setTextSize(tagTextSize);
        } else {
            textViewLabel.setTextSize(12);
        }
        textViewLabel.setIncludeFontPadding(false);
        textViewLabel.setPadding(SizeUtils.dp2px(6), 0,
                SizeUtils.dp2px(6), 0);
        textViewLabel.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置左间距
        layoutParams.leftMargin = SizeUtils.dp2px(6);
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
//        layoutParams.bottomMargin = SizeUtils.dp2px(3);
        layout.addView(textViewLabel, layoutParams);

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 给左边设置的margin留出空间
        layout.layout(0, 0, textViewLabel.getMeasuredWidth() + labelLeftMarginPx, textViewLabel.getMeasuredHeight());
//        layout.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        // 获取bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        //千万别忘最后一步
        layout.destroyDrawingCache();


//        ImageSpan imageSpan = new ImageSpan(context, bitmap);
//        ImageSpan imageSpan = new ImageSpan(bitmap, ImageSpan.ALIGN_BASELINE);


//        SpannableStringBuilder ssb = new SpannableStringBuilder(tagStr);
//        //将尾部tag字符用ImageSpan替换
//        ssb.setSpan(imageSpan, 0, tagStr.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        target.append(ssb);

        SpannableStringBuilder titleSp = new SpannableStringBuilder();
//        titleSp.append(title);
        String logoTag = "★";
        titleSp.append(logoTag);
        Pattern patternLogo = Pattern.compile(logoTag);
        Matcher matcherLogo = patternLogo.matcher(titleSp);
        if (matcherLogo.find()) {
            /**
             * 第三步，通过bitmap生成我们需要的ImageSpan对象
             */
            Drawable drawable1 = new BitmapDrawable(bitmap);
            drawable1.setBounds(0, 0, textViewLabel.getMeasuredWidth() + labelLeftMarginPx, textViewLabel.getMeasuredHeight()); //自定义图片尺寸
            CustomImageSpan customImageSpan = new CustomImageSpan(drawable1);
//            Drawable drawable1 = context.getResources().getDrawable(R.drawable.icon_minus);
//            drawable1.setBounds(0, 0, width, height); //自定义图片尺寸
            labelWidthPx = textViewLabel.getMeasuredWidth();
            /**
             * 第四步将ImageSpan对象设置到SpannableStringBuilder的对应位置
             */
            titleSp.setSpan(customImageSpan, matcherLogo.start(), matcherLogo.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
            return titleSp;
//            targetTv.append(titleSp);
        }
        return null;
    }

    private void log(String str) {
        Log.i("bro", str);
    }

    public static final class Builder {
        private Context context;
        private TextView targetTv;
        private String targetTvText;
        private String tagText;
        private String tagBackground;
        private String tagColor;
        private Drawable tagTextDrawableLocal;
        private int tagTextSize;
        private boolean arrowLeftOrRight;
        private int maxLines;

        private Builder() {
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTargetTv(TextView targetTv) {
            this.targetTv = targetTv;
            return this;
        }

        public Builder setTargetTvText(String targetTvText) {
            this.targetTvText = targetTvText;
            return this;
        }

        public Builder setTagText(String tagText) {
            this.tagText = tagText;
            return this;
        }

        public Builder setTagBackground(String tagBackground) {
            this.tagBackground = tagBackground;
            return this;
        }

        public Builder setTagColor(String tagColor) {
            this.tagColor = tagColor;
            return this;
        }

        public Builder setTagTextDrawableLocal(Drawable tagTextDrawableLocal) {
            this.tagTextDrawableLocal = tagTextDrawableLocal;
            return this;
        }

        public Builder setTagTextSize(int tagTextSize) {
            this.tagTextSize = tagTextSize;
            return this;
        }

        public Builder setArrowLeftOrRight(boolean arrowLeftOrRight) {
            this.arrowLeftOrRight = arrowLeftOrRight;
            return this;
        }

        public Builder setMaxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        public TextUtil build() {
            return new TextUtil(this);
        }
    }

}
