package anim.bro.com.practice.rvcategory;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anim.bro.com.practice.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RvCategoryActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView tvTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_category);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(tvTest).init();

        initData();
    }

    private void initData() {

//        CharSequence text = "效果测试★，效果测试，效果★测试";
//        SpannableStringBuilder builder = new SpannableStringBuilder(text);
//        String rexgString = "★";
//        Pattern pattern = Pattern.compile(rexgString);
//        Matcher matcher = pattern.matcher(text);
//        while (matcher.find()) {
//            builder.setSpan(
//                    new ImageSpan(this , R.drawable.detail_green_point), matcher.start(), matcher
//                            .end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        }
//        tvTest.setText(builder);

        CharSequence text = "效果测试★，效果★测试，效果★测★试";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        String rexgString = "★";
        Pattern pattern = Pattern.compile(rexgString);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            Drawable tvDrawable = ContextCompat.getDrawable(this, R.drawable.detail_green_point);
            tvDrawable.setBounds(0, 0, tvDrawable.getMinimumWidth(), tvDrawable.getMinimumHeight());
            CenterImageSpan span = new CenterImageSpan(tvDrawable, CenterImageSpan.ALIGN_CENTER);
            builder.setSpan(
                    span, matcher.start(), matcher
                            .end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        tvTest.setText(builder);
    }

}