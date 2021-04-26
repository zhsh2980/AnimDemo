package anim.bro.com.animdemo.goods;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.view.Rotate3dAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GlideActivity extends AppCompatActivity {

//    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_title.setText("haha");
        }
    };


    @BindView(R.id.iv_glide)
    ImageView ivGlide;
    @BindView(R.id.tv_title)
    TextView tv_title;

//    String url = "http://p0.jmstatic.com/mobile/2020/08/20/top_background/44941597908197.jpeg?t=1597908197&imageView2/2/w/960/q/90";
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597925628482&di=f12d7bcdaba6df1b6322f76051e3f7d0&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D1484500186%2C1503043093%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D853";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
        Glide.with(this).load(url).into(ivGlide);
        //发送延迟消息
        mHandler.sendEmptyMessageDelayed(0, 20000);
    }


}