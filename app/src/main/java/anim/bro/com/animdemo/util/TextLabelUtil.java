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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
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
public class TextLabelUtil {

    private String TAG = getClass().getSimpleName();

    private Context context;
    private TextView targetTv;
    private String targetTvText;
    private int targetTvMaxWidthPx;//父控件最大宽度,如果目标 TextView
    private boolean isTargetTvCutMiddle;//文字中间截断还是结尾截断
    private int labelAllWidthPx = -1;
    private int fitErrorWidth;
    private String labelText;
    private String labelBackground;//默认值白色
    private String labelTextColor;//默认值黑色
    private int labelCorner;
    private Drawable labelTextDrawableLocal;
    private int labelTextSize;//默认 12
    private boolean isLabelLeft;//标签是否居左
    private TextView labelOnlyTextView;//单独设置标签时才设置
    private int[] labelPading;//便签的内间距
    private boolean isHideLabel = false;
    private boolean arrowLeftOrRight;//暂时未实现
    private int maxLines = 2;//最大行数
    private int labelLeftMarginPx;//标签的左 margin
    private int labelRightMarginPx;//标签的右 margin
    private int labelWidthPx;//标签的左 margin
    private int arrorWidthPx = dp2px(5);//箭头的宽度
    private int arrorLeftMarginPx = dp2px(2);//箭头的左间距
    private SpannableStringBuilder arrowDrawable;
    private SpannableStringBuilder labelDrawable;
    private Drawable mDrawablePicNet;

    private TextLabelUtil(Builder builder) {
        context = builder.context;
        targetTv = builder.targetTv;
        targetTvText = builder.targetTvText;
        targetTvMaxWidthPx = builder.targetTvMaxWidthPx;
        isTargetTvCutMiddle = builder.isTargetTvCutMiddle;
        labelText = builder.labelText;
        labelBackground = builder.labelBackground;
        labelTextColor = builder.labelTextColor;
        labelCorner = builder.labelCorner;
        labelTextDrawableLocal = builder.labelTextDrawableLocal;
        labelTextSize = builder.labelTextSize;
        arrowLeftOrRight = builder.arrowLeftOrRight;
        maxLines = builder.maxLines;
        labelLeftMarginPx = builder.labelLeftMarginPx;
        labelRightMarginPx = builder.labelRightMarginPx;
        isLabelLeft = builder.isLabelLeft;
        labelOnlyTextView = builder.labelOnlyTv;
        labelPading = builder.labelPading;
        isHideLabel = builder.isHideLabel;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    //给图片设置的宽高,背景色用不着
    private int width = dp2px(50);
    private int height = dp2px(15);

    //测试 url
    public static String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";

    public void addLabelToTextView() {
        targetTv.setVisibility(View.INVISIBLE);
        targetTv.setText("测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度测试控件的宽度");
        targetTv.post(new Runnable() {
            @Override
            public void run() {
                targetTvMaxWidthPx = targetTv.getWidth();
                targetTv.setText("");
                targetTv.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(labelBackground) && labelBackground.startsWith("#")) {
                    //背景色
                    addLabelToTextView(null);
                } else {
                    //背景图
                    loadPic();
                }
            }
        });


    }


    private void loadPic() {
        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1024x768c5/g5/M00/00/02/ChMkJl3o5FqIY6f_AAGq9Ei0WJgAAvl8QFnVNAAAasM391.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1440x900c5/g6/M00/02/08/ChMkKmD_YbiIcvwfAASj8VSnLUMAASK7gDaIRcABKQJ325.jpg";
//        String picUrl = "https://desk-fd.zol-img.com.cn/t_s1600x900c5/g6/M00/00/0A/ChMkKmD6I2iIPb5AAIfgIkjcHwYAASDZgGr_akAh-A6322.jpg";
        Glide.with(context) // could be an issue!R
//                .asBitmap()//强制Glide返回一个Bitmap
//                .load(R.drawable.icon_minus)
                .load(labelBackground)
                .override(width, height)
//                .centerCrop()
                .transform(new RoundedCorners(labelCorner), new CenterCrop())
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
                        addLabelToTextView(drawablePicNet);
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

    //截取最后一位
    private String subLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }


    private void clipText(String targetTvText, boolean isCliped) {
//        targetTv.append(targetTvText);
        //2. 算出最后一行的文字宽度
        int textTotalWidthPx = (int) (targetTv.getPaint().measureText(targetTvText) + targetTv.getMaxLines() * (targetTv.getPaddingLeft() + targetTv.getPaddingRight()));
        log("targetTvText: " + targetTvText + "  textTotalWidthPx: " + textTotalWidthPx);
//        int textTotalWidthPx = textTotalWidthPx;//除去标签文本的宽度(包括 padingleft padingright)
        Log.i("bro", "textTotalWidthPx  : " + textTotalWidthPx + "  textViewMaxWidthPx  : " + targetTvMaxWidthPx);
        if (targetTvMaxWidthPx > 0) {
//            if (textTotalWidthPx > textViewMaxWidthPx) {
            //多行
            int i = textTotalWidthPx / targetTvMaxWidthPx;
            int j = textTotalWidthPx % targetTvMaxWidthPx;
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
//                    arrowLeftMarginPx = (int) (targetTv.getPaint().measureText(" "));
                }
                log("textViewMaxWidthPx: " + targetTvMaxWidthPx
                        + "  textTotalWidthPx: " + textTotalWidthPx
                        + "  textThreePointWidthPx: " + textThreePointWidthPx
                        + "  labelAllWidthPx: " + labelAllWidthPx);

                fitErrorWidth = (i + 1) * (int) (targetTv.getPaint().measureText("哈") * 0.5);
                log("容错距离: " + fitErrorWidth);

                if (targetTvMaxWidthPx - textTotalWidthPx - textThreePointWidthPx - arrowLeftMarginPx - fitErrorWidth > labelAllWidthPx) {
                    if (isCliped) {
                        targetTvText = getMiddleText(targetTvText);
                    }
                    if (isLabelLeft) {
                        if (labelDrawable != null) {
                            targetTv.setText(labelDrawable);
                        } else {
                            targetTv.setText("");
                        }
                        targetTv.append(targetTvText);
                        if (arrowDrawable != null) {
                            targetTv.append(" ");
                            targetTv.append(arrowDrawable);
                        }
                    } else {
                        targetTv.setText(targetTvText);
                        if (arrowDrawable != null) {
                            targetTv.append(" ");
                            targetTv.append(arrowDrawable);
                        }
                        if (labelDrawable != null) {
                            targetTv.append(labelDrawable);
                        }
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
//            }
        }
    }

    private String getMiddleText(String middleOriginTxt) {
        if (isTargetTvCutMiddle) {
            try {
                //3个点放中间
                CharSequence str1 = middleOriginTxt.subSequence(0, middleOriginTxt.length() / 2);
                CharSequence str2 = middleOriginTxt.subSequence(middleOriginTxt.length() / 2, middleOriginTxt.length());
                return str1 + "..." + str2;
            } catch (Exception e) {
                Log.i(TAG, "getMiddleText   -- Exception --");
                return middleOriginTxt + "...";
            }
        } else {
            return middleOriginTxt + "...";
        }
    }

    /**
     * 单独设置标签样式
     */
    public void setSingleLabelStyle() {
        if (context == null) {
            return;
        }
        if (labelOnlyTextView != null) {
            setSingleLabelStyle(labelOnlyTextView);
        }
    }

    /**
     * 设置文字+标签
     */
    private void addLabelToTextView(@Nullable Drawable drawablePicNet) {

        if (context == null) {
            targetTv.setText(targetTvText);
            return;
        }

        arrowDrawable = getArrowStringBuilder();
        labelDrawable = getLabelStringBuilder(drawablePicNet);

        //隐藏标签
        if (isHideLabel) {
            labelDrawable = null;
            labelWidthPx = 0;
            labelLeftMarginPx = 0;
            labelRightMarginPx = 0;
        }

//        arrorLeftMarginPx = 0;

        if (labelTextDrawableLocal != null) {
            labelAllWidthPx = labelAllWidthPx + arrorWidthPx + arrorLeftMarginPx;
        }
        labelAllWidthPx = labelAllWidthPx + labelWidthPx + labelLeftMarginPx + labelRightMarginPx;

        Log.i("bro", "textViewMaxWidth: " + SizeUtils.px2dp(targetTvMaxWidthPx));
        clipText(targetTvText, false);

        //整体加箭头
//        Drawable right = context.getResources().getDrawable(R.drawable.ic_cart_black_arrow);
//        target.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);

    }

    /**
     * 设置 label 样式
     *
     * @param textView
     */
    private void setSingleLabelStyle(TextView textView) {
        if (!TextUtils.isEmpty(labelText)) {
            textView.setText(labelText);
        }
        setTextColor(textView, labelTextColor);

        try {
            if (mDrawablePicNet == null) {
                //参考 https://www.tabnine.com/code/java/methods/android.graphics.drawable.ShapeDrawable/setShape
                ShapeDrawable background = new ShapeDrawable();
                float[] radii = new float[8];// maybe 每个脚两个的意思  4 * 2 = 8
                for (int i = 0; i <= 7; i++) {
                    radii[i] = labelCorner;
                }
                background.setShape(new RoundRectShape(radii, null, null)); // or RoundRectShape()
                background.getPaint().setColor((Color.parseColor(labelBackground)));
                textView.setBackground(background);
            }
        } catch (Exception e) {
            Log.i(TAG, "setSingleLabelStyle   -- Exception --");
        }

        textView.setTextSize(labelTextSize);
        textView.setIncludeFontPadding(false);
        textView.setPadding(labelPading[0], labelPading[1], labelPading[2], labelPading[3]);

        textView.setGravity(Gravity.CENTER);
    }

    private SpannableStringBuilder getArrowStringBuilder() {
        if (labelTextDrawableLocal != null) {
            //最后一行末尾加箭头
            SpannableStringBuilder arrowSp = new SpannableStringBuilder();
//            targetTv.append(" ");
            String logoTag2 = "#";
            arrowSp.append(logoTag2);
            Pattern patternLogo2 = Pattern.compile(logoTag2);
            Matcher matcherLogo2 = patternLogo2.matcher(arrowSp);
            if (matcherLogo2.find()) {
//            Drawable drawable1 = new BitmapDrawable(bitmap);
//                Drawable drawable1 = context.getResources().getDrawable(drawLocal);
                Drawable drawable1 = labelTextDrawableLocal;
                drawable1.setBounds(0, 0, arrorWidthPx, dp2px(10)); //自定义图片尺寸
                arrowSp.setSpan(new CustomImageSpan(drawable1), matcherLogo2.start(), matcherLogo2.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
                return arrowSp;
            }
        }
        return null;
    }

    private SpannableStringBuilder getLabelStringBuilder(Drawable drawablePicNet) {

        mDrawablePicNet = drawablePicNet;

        /**
         * 创建TextView对象，设置drawable背景，设置字体样式，设置间距，设置文本等
         * 这里我们为了给TextView设置margin，给其添加了一个父容器LinearLayout。不过他俩都只是new出来的，不会添加进任何布局
         */
        LinearLayout layout = new LinearLayout(context);
        TextView textViewLabel = new TextView(context);

        if (drawablePicNet != null) {
//            LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(width, height);
//            textViewLabel.setLayoutParams(Params1);
            textViewLabel.setBackground(drawablePicNet);
        }
        //设置标签的 UI 样式
        setSingleLabelStyle(textViewLabel);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //标签居左,自动默认右边距为 4dp,标签居右,自动默认左边距为 4dp
        if (isLabelLeft) {
            labelRightMarginPx = dp2px(4);
        } else {
            labelLeftMarginPx = dp2px(4);
        }
        // 设置左间距
        if (labelLeftMarginPx > 0) {
            layoutParams.leftMargin = labelLeftMarginPx;
        }
        if (labelRightMarginPx > 0) {
            layoutParams.rightMargin = labelRightMarginPx;
        }
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
//        layoutParams.bottomMargin = dp2px(3);
        layout.addView(textViewLabel, layoutParams);

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 给左边设置的margin留出空间
        layout.layout(0, 0, textViewLabel.getMeasuredWidth() + labelLeftMarginPx + labelRightMarginPx, textViewLabel.getMeasuredHeight());
//        layout.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        // 获取bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        //千万别忘最后一步
        layout.destroyDrawingCache();

        SpannableStringBuilder titleSp = new SpannableStringBuilder();
        String logoTag = "★";
        titleSp.append(logoTag);
        Pattern patternLogo = Pattern.compile(logoTag);
        Matcher matcherLogo = patternLogo.matcher(titleSp);
        if (matcherLogo.find()) {
            /**
             * 第三步，通过bitmap生成我们需要的ImageSpan对象
             */
            Drawable drawable1 = new BitmapDrawable(bitmap);
            drawable1.setBounds(0, 0, textViewLabel.getMeasuredWidth() + labelLeftMarginPx + labelRightMarginPx, textViewLabel.getMeasuredHeight()); //自定义图片尺寸
            CustomImageSpan customImageSpan = new CustomImageSpan(drawable1);
//            Drawable drawable1 = context.getResources().getDrawable(R.drawable.icon_minus);
//            drawable1.setBounds(0, 0, width, height); //自定义图片尺寸
            labelWidthPx = textViewLabel.getMeasuredWidth();
            /**
             * 第四步将ImageSpan对象设置到SpannableStringBuilder的对应位置
             */
            titleSp.setSpan(customImageSpan, matcherLogo.start(), matcherLogo.end(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE); //SPAN_EXCLUSIVE_EXCLUSIVE代表只对所选范围内文字生效
            return titleSp;
        }
        return null;
    }

    private void log(String str) {
        Log.i("bro", str);
    }

    private void setTextColor(TextView textView, String color) {
        try {
            textView.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            Log.i(TAG, "setTextColor   -- Exception --");
        }
    }

    private int dp2px(int dp) {
        return SizeUtils.dp2px(dp);

    }

    public static final class Builder {
        private Context context;
        private TextView targetTv;
        private String targetTvText;
        private int targetTvMaxWidthPx = -1;//父控件最大宽度,如果目标 TextView
        private boolean isTargetTvCutMiddle;
        private int labelLeftMarginPx;
        private int labelRightMarginPx;
        private String labelText;
        private String labelBackground = "#FFFFFF";
        private String labelTextColor = "#FF463C";
        private int labelCorner = SizeUtils.dp2px(2);
        private boolean isLabelLeft = false;//便签居左,默认是居右的
        private TextView labelOnlyTv;
        private Drawable labelTextDrawableLocal;
        private int[] labelPading = {SizeUtils.dp2px(2), SizeUtils.dp2px(0),
                SizeUtils.dp2px(2), SizeUtils.dp2px(0)};
        private int labelTextSize = 10;
        private boolean isHideLabel = false;
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

        public Builder setTargetTvMaxWidth(int targetTvMaxWidthPx) {
            this.targetTvMaxWidthPx = targetTvMaxWidthPx;
            return this;
        }

        public Builder setTargetTvCutMiddle(boolean isTargetTvCutMiddle) {
            this.isTargetTvCutMiddle = isTargetTvCutMiddle;
            return this;
        }

        public Builder setLabelLeftMarginPx(int labelLeftMarginPx) {
            this.labelLeftMarginPx = labelLeftMarginPx;
            return this;
        }

        public Builder setLabelRightMarginPx(int labelRightMarginPx) {
            this.labelRightMarginPx = labelRightMarginPx;
            return this;
        }

        public Builder setLabelText(String labelText) {
            this.labelText = labelText;
            return this;
        }

        public Builder setLabelBackground(String labelBackground) {
            this.labelBackground = labelBackground;
            return this;
        }

        public Builder setLabelTextColor(String labelTextColor) {
            this.labelTextColor = labelTextColor;
//            this.labelTextColor = "#FFFFFF";
            return this;
        }

        public Builder setLabelCorner(int labelCorner) {
            if (labelCorner < 0) labelCorner = 0;
            this.labelCorner = labelCorner;
            return this;
        }

        public Builder setLabelPadding(int[] labelPading) {
            this.labelPading = labelPading;
            return this;
        }

        public Builder setLabelTextDrawableLocal(Drawable labelTextDrawableLocal) {
            this.labelTextDrawableLocal = labelTextDrawableLocal;
            return this;
        }

        public Builder setLabelTextSize(int labelTextSize) {
            this.labelTextSize = labelTextSize;
            return this;
        }

        public Builder hideLabel(boolean isHideLabel) {
            this.isHideLabel = isHideLabel;
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

        public Builder setLabelLeft(boolean isLabelLeft) {
            this.isLabelLeft = isLabelLeft;
            return this;
        }

        public Builder setLabelOnlyTv(TextView labelOnlyTv) {
            this.labelOnlyTv = labelOnlyTv;
            return this;
        }

        public TextLabelUtil build() {
            return new TextLabelUtil(this);
        }
    }
}
