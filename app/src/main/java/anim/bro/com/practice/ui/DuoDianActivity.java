package anim.bro.com.practice.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import anim.bro.com.practice.R;
import anim.bro.com.practice.bean.AgileBodyPromotionInfoModel;
import anim.bro.com.practice.util.EnterDebugUtils;
import anim.bro.com.practice.util.GetJsonDataUtil;
import anim.bro.com.practice.util.PriceUtils;
import anim.bro.com.practice.util.TextLabelUtil;
import anim.bro.com.practice.view.AdvertThreeTextView;
import anim.bro.com.practice.view.AppendViewAfterTextView;
import anim.bro.com.practice.view.Rotate3dAnimation;

public class DuoDianActivity extends BaseActivity {

    private TextView tv_test;
    private TextView tv_normal;
    private TextView tv_price;
    private ImageView iv_test;
    private ViewFlipper fliper1;
    private ViewFlipper fliper2;
    private ViewFlipper fliper3;
    private ViewFlipper fliper4;
    private AppendViewAfterTextView appendViewAfterTextView;
    private AdvertThreeTextView threeTextView;
    private final static int SHOW_NEXT = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_NEXT:
                    showNextFlipper(fliper1);
                    showNextFlipper(fliper2);
                    showNextFlipper(fliper3);
                    showNextFlipper(fliper4);
                    startFlipper();
                    break;
            }
        }
    };


    @Override
    public int getResourceID() {
        return R.layout.activity_duo_dian;
    }

    @Override
    public String getTitleName() {
        return "多点";
    }

    @Override
    public void initView() {
        tv_test = findViewById(R.id.tv_test);
        tv_normal = findViewById(R.id.tv_normal);
        tv_price = findViewById(R.id.tv_price);
        View flipItemView = findViewById(R.id.view_flipper);
        fliper1 = flipItemView.findViewById(R.id.fliper_1);
        fliper2 = flipItemView.findViewById(R.id.fliper_2);
        fliper3 = flipItemView.findViewById(R.id.fliper_3);
        fliper4 = flipItemView.findViewById(R.id.fliper_4);
        setLabelText();

        setFlipper();

        PriceUtils.setPriceSpannableText(this, tv_price,
                "¥129.00", "¥149.89",
                16, 12);

        String qrCodeEnterDebug = EnterDebugUtils.getInstance().getQrCodeEnterDebug();
        Log.i("bro", "qrCodeEnterDebug: " + qrCodeEnterDebug);

    }

    private void setFlipper() {
        String beanJson = new GetJsonDataUtil().getJson(this, "flipper.json");//获取assets目录下的json文件数据
        Gson gson = new Gson();
        AgileBodyPromotionInfoModel bannerBean = gson.fromJson(beanJson, AgileBodyPromotionInfoModel.class);
        setFlipperData(fliper1, bannerBean.items.get(0));
        setFlipperData(fliper2, bannerBean.items.get(1));
        setFlipperData(fliper3, bannerBean.items.get(2));
        setFlipperData(fliper4, bannerBean.items.get(3));
        startFlipper();

        int childCount = fliper1.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View flipItemView = fliper1.getChildAt(i);
            TextView textView = flipItemView.findViewById(R.id.tv_flipper_item);
            textView.setTextColor(Color.BLUE);
        }
    }

    private void startFlipper() {
        handler.sendEmptyMessageDelayed(SHOW_NEXT, 3000);
    }

    private void startFlipper(ViewFlipper viewFlipper) {
        if (viewFlipper != null && viewFlipper.getChildCount() > 1 && !viewFlipper.isFlipping()) {
//            viewFlipper.startFlipping();
        }
    }

    private void showNextFlipper(ViewFlipper viewFlipper) {
        if (viewFlipper != null && viewFlipper.getChildCount() > 1 && !viewFlipper.isFlipping()) {
            viewFlipper.showNext();
        }
    }

    private void setFlipperData(ViewFlipper fliper, AgileBodyPromotionInfoModel.Items item) {
        for (AgileBodyPromotionInfoModel.Items.ItemsInside itemsInside : item.itemsInside) {
            View flipItemView = LayoutInflater.from(this).inflate(R.layout.layout_flip_item, null);
            ImageView ivFlipperItem = flipItemView.findViewById(R.id.iv_flipper_item);
            TextView tvFlipperItem = flipItemView.findViewById(R.id.tv_flipper_item);
            Glide.with(this).load(itemsInside.pic).into(ivFlipperItem);
            tvFlipperItem.setText(itemsInside.title + "");
            flipItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showLong("点我干啥: " + itemsInside.title);
//                    String other = item.getOther();
//                    PagerTransferFactory.getInstance().onCMSTransfer(context, item.picLink).extra(other).other(other).onAction();
//                    PagerTransferFactory.getInstance().onCMSTransfer(context, itemsInside.picLink).onAction();
                }
            });
            fliper.addView(flipItemView);
        }
    }


    private void setLabelText() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_cart_black_arrow);
        TextLabelUtil.newBuilder()
                .setContext(this)
                .setTargetTv(tv_normal)
//                .setTargetTvText("文轩网旗舰店")
                .setTargetTvText("围围围围围围")
                .setLabelRightMarginPx(SizeUtils.dp2px(3))
                .setLabelCorner(SizeUtils.dp2px(2))
                .setLabelPadding(new int[]{SizeUtils.dp2px(3), SizeUtils.dp2px(0), SizeUtils.dp2px(3), SizeUtils.dp2px(0)})
                .setLabelText("明星店铺")
                .hideLabel(false)
                .setTargetTvCutMiddle(false)
                .setLabelTextColor("#654321")
                .setLabelBackground("#123456")
                .setLabelLeft(true)
//                .setMaxLines(2)
                .setLabelTextDrawableLocal(drawable)
                .build()
                .addLabelToTextView();
//        appendViewAfterTextView = findViewById(R.id.append_text);
//        appendViewAfterTextView.setText("我是正常的文字我是正常的文字我是正常的文字我是正常的文字");
//        appendViewAfterTextView.setSpecialViewText("我是按钮");

//        threeTextView = findViewById(R.id.textAd);
//        threeTextView.setTextContent("我是正常的文字我是正常的文字我是正常的文字我是正常的文字我是正常的文字" , "广告");
    }

    @Override
    public void initData() {
    }

}
