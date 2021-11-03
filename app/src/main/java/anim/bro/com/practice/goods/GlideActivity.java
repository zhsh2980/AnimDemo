package anim.bro.com.practice.goods;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import anim.bro.com.practice.R;
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