package anim.bro.com.animdemo.ui;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.util.TextUtil;
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
        TextUtil.newBuilder()
                .setContext(this)
                .setTargetTv(tv_normal)
                .setTargetTvText((String) tv_normal.getText())
                .setTagText("当当好店")
                .setTagColor("#654321")
                .setTagBackground("#123456")
//                .setMaxLines(2)
                .setTagTextDrawableLocal(drawable)
                .build()
                .addTagToTextView();
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
