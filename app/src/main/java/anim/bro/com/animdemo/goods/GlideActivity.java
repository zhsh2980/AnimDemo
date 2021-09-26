package anim.bro.com.animdemo.goods;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import anim.bro.com.animdemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GlideActivity extends AppCompatActivity {


//    String url = "https://p0.jmstatic.com/mobile/package/exchange/android/apk/anim_box_normal.gif";
    String url = "https://p0.jmstatic.com/mobile/package/exchange/android/apk/anim_box_dialog.svga";
    String url2 = "https://p0.jmstatic.com/mobile/package/exchange/android/apk/anim_box_new_2nd.svga";
    String url3 = "https://p0.jmstatic.com/mobile/package/exchange/android/apk/anim_box_normal.svga";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
    }


}