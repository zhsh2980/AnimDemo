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
    @BindView(R.id.iv_box_open_new_gold)
    SVGAImageView mSVGAImage;
    @BindView(R.id.iv_2)
    SVGAImageView mSVGAImage2;
    @BindView(R.id.iv_3)
    SVGAImageView mSVGAImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);

        try {
            new SVGAParser(this).decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity videoItem) {
                    mSVGAImage.setVideoItem(videoItem);
                    mSVGAImage.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            new SVGAParser(this).decodeFromURL(new URL(url2), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity videoItem) {
                    mSVGAImage2.setVideoItem(videoItem);
//                    mSVGAImage2.startAnimation();
                    mSVGAImage2.stepToFrame(0 , true);
                    mSVGAImage2.setCallback(new SVGACallback() {
                        @Override
                        public void onPause() {
                            //动画暂停
                        }

                        @Override
                        public void onFinished() {
                            //动画结束
                            LogUtils.i("bro","动画结束");
                            ToastUtils.showLong("动画结束");
                        }

                        @Override
                        public void onRepeat() {
                            //重复播放
                        }

                        @Override
                        public void onStep(int i, double v) {
                            //动画步骤
                        }
                    });
                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            new SVGAParser(this).decodeFromURL(new URL(url3), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity videoItem) {
                    mSVGAImage3.setVideoItem(videoItem);
                    mSVGAImage3.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


}