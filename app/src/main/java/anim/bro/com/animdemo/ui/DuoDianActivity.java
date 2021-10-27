package anim.bro.com.animdemo.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.util.TextUtil;
import anim.bro.com.animdemo.util.TextUtilNew;
import anim.bro.com.animdemo.view.AdvertThreeTextView;
import anim.bro.com.animdemo.view.AppendViewAfterTextView;

public class DuoDianActivity extends BaseActivity {

    private TextView tv_test;
    private TextView tv_normal;
    private AppendViewAfterTextView appendViewAfterTextView;
    private AdvertThreeTextView threeTextView;

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


        Drawable drawable = getResources().getDrawable(R.drawable.ic_cart_black_arrow);

        TextUtilNew.addTagToTextView(this , tv_normal,
                "当当自营当当自营",
                "当当好店"  ,
                drawable,
                TextUtilNew.mColorStr);


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
